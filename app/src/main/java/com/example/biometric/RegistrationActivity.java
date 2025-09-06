package com.example.biometric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etName;
    private Spinner spinnerCountry;
    private Spinner spinnerCity;
    private Button btnContinue;
    private TextView tvSkipFingerprintInfo;
    
    private String selectedCountry;
    private String selectedCity;
    private ArrayAdapter<String> countryAdapter;
    private ArrayAdapter<String> cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        setupSpinners();
        setupListeners();
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnContinue = findViewById(R.id.btnContinue);
        tvSkipFingerprintInfo = findViewById(R.id.tvSkipFingerprintInfo);
    }

    private void setupSpinners() {
        // Setup country spinner
        List<String> countries = CountryCityUtils.getCountries();
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(countryAdapter);

        // Setup city spinner (initially empty)
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }

    private void setupListeners() {
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = (String) parent.getItemAtPosition(position);
                updateCitySpinner(selectedCountry);
                updateSkipFingerprintInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCountry = null;
                updateSkipFingerprintInfo();
            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = null;
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    proceedToFingerprintRegistration();
                }
            }
        });
    }

    private void updateCitySpinner(String country) {
        List<String> cities = CountryCityUtils.getCitiesForCountry(country);
        cityAdapter.clear();
        cityAdapter.addAll(cities);
        cityAdapter.notifyDataSetChanged();
        selectedCity = null;
    }

    private void updateSkipFingerprintInfo() {
        if ("Estados Unidos".equals(selectedCountry) || "Guatemala".equals(selectedCountry)) {
            tvSkipFingerprintInfo.setVisibility(View.VISIBLE);
        } else {
            tvSkipFingerprintInfo.setVisibility(View.GONE);
        }
    }

    private boolean validateInput() {
        String name = etName.getText().toString().trim();
        
        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (selectedCountry == null) {
            Toast.makeText(this, "Por favor seleccione un país", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (selectedCity == null) {
            Toast.makeText(this, "Por favor seleccione una ciudad", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }

    private void proceedToFingerprintRegistration() {
        String name = etName.getText().toString().trim();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        
        // Check if country is USA or Guatemala - skip fingerprint registration
        if ("Estados Unidos".equals(selectedCountry) || "Guatemala".equals(selectedCountry)) {
            // Skip fingerprint registration and go directly to iris
            Intent intent = new Intent(this, IrisActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("country", selectedCountry);
            intent.putExtra("city", selectedCity);
            intent.putExtra("registrationDate", currentDate);
            // Create empty fingerprint hashes for countries that skip fingerprint registration
            ArrayList<String> emptyFingerprintHashes = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                emptyFingerprintHashes.add("faltante");
            }
            intent.putStringArrayListExtra("fingerprintHashes", emptyFingerprintHashes);
            startActivity(intent);
        } else {
            // Normal flow - go to fingerprint registration
            Intent intent = new Intent(this, FingerprintActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("country", selectedCountry);
            intent.putExtra("city", selectedCity);
            intent.putExtra("registrationDate", currentDate);
            startActivity(intent);
        }
    }
}

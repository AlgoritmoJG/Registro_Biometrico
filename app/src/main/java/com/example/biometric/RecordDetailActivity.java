package com.example.biometric;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {

    private TextView tvName, tvCountry, tvCity, tvRegistrationDate;
    private TextView tvThumbHash, tvIndexHash, tvMiddleHash, tvRingHash, tvPinkyHash;
    private TextView tvIrisHash;
    
    private DatabaseHelper dbHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        long userId = getIntent().getLongExtra("userId", -1);
        if (userId == -1) {
            finish();
            return;
        }

        initViews();
        loadUserData(userId);
        displayUserData();
    }

    private void initViews() {
        tvName = findViewById(R.id.tvName);
        tvCountry = findViewById(R.id.tvCountry);
        tvCity = findViewById(R.id.tvCity);
        tvRegistrationDate = findViewById(R.id.tvRegistrationDate);
        
        tvThumbHash = findViewById(R.id.tvThumbHash);
        tvIndexHash = findViewById(R.id.tvIndexHash);
        tvMiddleHash = findViewById(R.id.tvMiddleHash);
        tvRingHash = findViewById(R.id.tvRingHash);
        tvPinkyHash = findViewById(R.id.tvPinkyHash);
        
        tvIrisHash = findViewById(R.id.tvIrisHash);
    }

    private void loadUserData(long userId) {
        dbHelper = new DatabaseHelper(this);
        user = dbHelper.getUserById(userId);
    }

    private void displayUserData() {
        if (user == null) {
            finish();
            return;
        }

        tvName.setText("Nombre: " + user.getName());
        tvCountry.setText("País: " + user.getCountry());
        tvCity.setText("Ciudad: " + user.getCity());
        tvRegistrationDate.setText("Fecha de Registro: " + user.getRegistrationDate());

        // Parse fingerprint hashes
        String[] fingerprintHashes = user.getFingerprintHashes().split(",");
        String[] fingerNames = {"Pulgar", "Índice", "Medio", "Anular", "Meñique"};
        
        TextView[] hashTextViews = {tvThumbHash, tvIndexHash, tvMiddleHash, tvRingHash, tvPinkyHash};
        
        for (int i = 0; i < Math.min(fingerprintHashes.length, 5); i++) {
            String hash = fingerprintHashes[i].trim();
            String displayText = fingerNames[i] + ": ";
            if ("faltante".equals(hash)) {
                displayText += "FALTANTE";
            } else {
                displayText += hash.substring(0, Math.min(16, hash.length())) + "...";
            }
            hashTextViews[i].setText(displayText);
        }

        // Display iris hash
        String irisHash = user.getIrisHash();
        if (irisHash != null && !irisHash.isEmpty()) {
            tvIrisHash.setText("Hash del Iris: " + irisHash.substring(0, Math.min(16, irisHash.length())) + "...");
        } else {
            tvIrisHash.setText("Hash del Iris: No disponible");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

package com.example.biometric;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class FingerprintActivity extends AppCompatActivity {

    private Button btnThumb, btnIndex, btnMiddle, btnRing, btnPinky;
    private CheckBox cbThumbMissing, cbIndexMissing, cbMiddleMissing, cbRingMissing, cbPinkyMissing;
    private Button btnContinue;
    
    private String name, country, city, registrationDate;
    private List<String> fingerprintHashes;
    private int currentFingerIndex = -1;
    
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        // Get data from previous activity
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        country = intent.getStringExtra("country");
        city = intent.getStringExtra("city");
        registrationDate = intent.getStringExtra("registrationDate");

        initViews();
        setupBiometricPrompt();
        setupListeners();
        
        fingerprintHashes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fingerprintHashes.add("faltante");
        }
    }

    private void initViews() {
        btnThumb = findViewById(R.id.btnThumb);
        btnIndex = findViewById(R.id.btnIndex);
        btnMiddle = findViewById(R.id.btnMiddle);
        btnRing = findViewById(R.id.btnRing);
        btnPinky = findViewById(R.id.btnPinky);
        
        cbThumbMissing = findViewById(R.id.cbThumbMissing);
        cbIndexMissing = findViewById(R.id.cbIndexMissing);
        cbMiddleMissing = findViewById(R.id.cbMiddleMissing);
        cbRingMissing = findViewById(R.id.cbRingMissing);
        cbPinkyMissing = findViewById(R.id.cbPinkyMissing);
        
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setupBiometricPrompt() {
        biometricPrompt = BiometricUtils.createBiometricPrompt(this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                runOnUiThread(() -> {
                    if (currentFingerIndex >= 0 && currentFingerIndex < 5) {
                        // Generate a mock hash for the fingerprint (in real app, this would be actual biometric data)
                        String mockFingerprintData = "fingerprint_" + currentFingerIndex + "_" + System.currentTimeMillis();
                        String hash = HashUtils.generateFingerprintHash(mockFingerprintData);
                        fingerprintHashes.set(currentFingerIndex, hash);
                        
                        updateButtonState(currentFingerIndex, true);
                        Toast.makeText(FingerprintActivity.this, "Huella registrada exitosamente", Toast.LENGTH_SHORT).show();
                    }
                    currentFingerIndex = -1;
                });
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                runOnUiThread(() -> {
                    Toast.makeText(FingerprintActivity.this, "Error en autenticación: " + errString, Toast.LENGTH_SHORT).show();
                    currentFingerIndex = -1;
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                runOnUiThread(() -> {
                    Toast.makeText(FingerprintActivity.this, "Autenticación fallida", Toast.LENGTH_SHORT).show();
                    currentFingerIndex = -1;
                });
            }
        });
    }

    private void setupListeners() {
        btnThumb.setOnClickListener(v -> startFingerprintScan(0, "Pulgar"));
        btnIndex.setOnClickListener(v -> startFingerprintScan(1, "Índice"));
        btnMiddle.setOnClickListener(v -> startFingerprintScan(2, "Medio"));
        btnRing.setOnClickListener(v -> startFingerprintScan(3, "Anular"));
        btnPinky.setOnClickListener(v -> startFingerprintScan(4, "Meñique"));

        // Missing checkboxes
        cbThumbMissing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fingerprintHashes.set(0, "faltante");
                updateButtonState(0, false);
            }
        });
        
        cbIndexMissing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fingerprintHashes.set(1, "faltante");
                updateButtonState(1, false);
            }
        });
        
        cbMiddleMissing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fingerprintHashes.set(2, "faltante");
                updateButtonState(2, false);
            }
        });
        
        cbRingMissing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fingerprintHashes.set(3, "faltante");
                updateButtonState(3, false);
            }
        });
        
        cbPinkyMissing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fingerprintHashes.set(4, "faltante");
                updateButtonState(4, false);
            }
        });

        btnContinue.setOnClickListener(v -> {
            if (validateFingerprints()) {
                proceedToIrisRegistration();
            }
        });
    }

    private void startFingerprintScan(int fingerIndex, String fingerName) {
        if (!BiometricUtils.isBiometricAvailable(this)) {
            Toast.makeText(this, "Biometría no disponible en este dispositivo", Toast.LENGTH_SHORT).show();
            return;
        }

        currentFingerIndex = fingerIndex;
        promptInfo = BiometricUtils.createPromptInfo(
                "Registro de Huella",
                "Coloque su " + fingerName + " en el sensor"
        );
        biometricPrompt.authenticate(promptInfo);
    }

    private void updateButtonState(int fingerIndex, boolean isRegistered) {
        Button button = getButtonByIndex(fingerIndex);
        if (isRegistered) {
            button.setText("✓ Registrado");
            button.setEnabled(false);
        } else {
            button.setText(getFingerName(fingerIndex));
            button.setEnabled(true);
        }
    }

    private Button getButtonByIndex(int index) {
        switch (index) {
            case 0: return btnThumb;
            case 1: return btnIndex;
            case 2: return btnMiddle;
            case 3: return btnRing;
            case 4: return btnPinky;
            default: return null;
        }
    }

    private String getFingerName(int index) {
        switch (index) {
            case 0: return "Pulgar";
            case 1: return "Índice";
            case 2: return "Medio";
            case 3: return "Anular";
            case 4: return "Meñique";
            default: return "";
        }
    }

    private boolean validateFingerprints() {
        // Check if at least one fingerprint is registered (not all are "faltante")
        boolean hasAtLeastOne = false;
        for (String hash : fingerprintHashes) {
            if (!"faltante".equals(hash)) {
                hasAtLeastOne = true;
                break;
            }
        }

        if (!hasAtLeastOne) {
            Toast.makeText(this, "Debe registrar al menos una huella dactilar", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void proceedToIrisRegistration() {
        Intent intent = new Intent(this, IrisActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("country", country);
        intent.putExtra("city", city);
        intent.putExtra("registrationDate", registrationDate);
        intent.putStringArrayListExtra("fingerprintHashes", (ArrayList<String>) fingerprintHashes);
        startActivity(intent);
    }
}

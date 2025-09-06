package com.example.biometric;

import android.content.Context;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class BiometricUtils {
    public static boolean isBiometricAvailable(Context context) {
        BiometricManager biometricManager = BiometricManager.from(context);
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS;
    }

    public static BiometricPrompt createBiometricPrompt(FragmentActivity activity, BiometricPrompt.AuthenticationCallback callback) {
        return new BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), callback);
    }

    public static BiometricPrompt.PromptInfo createPromptInfo(String title, String subtitle) {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .setNegativeButtonText("Cancelar")
                .build();
    }
}

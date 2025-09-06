package com.example.biometric;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class IrisActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    
    private PreviewView previewView;
    private ImageView capturedImageView;
    private Button btnCapture;
    private Button btnRetake;
    private Button btnContinue;
    
    private String name, country, city, registrationDate;
    private ArrayList<String> fingerprintHashes;
    private String irisHash;
    private Bitmap capturedImage;
    
    private ImageCapture imageCapture;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iris);

        // Get data from previous activity
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        country = intent.getStringExtra("country");
        city = intent.getStringExtra("city");
        registrationDate = intent.getStringExtra("registrationDate");
        fingerprintHashes = intent.getStringArrayListExtra("fingerprintHashes");

        initViews();
        setupListeners();
        
        if (checkCameraPermission()) {
            startCamera();
        } else {
            requestCameraPermission();
        }
    }

    private void initViews() {
        previewView = findViewById(R.id.previewView);
        capturedImageView = findViewById(R.id.capturedImageView);
        btnCapture = findViewById(R.id.btnCapture);
        btnRetake = findViewById(R.id.btnRetake);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setupListeners() {
        btnCapture.setOnClickListener(v -> captureImage());
        btnRetake.setOnClickListener(v -> retakeImage());
        btnContinue.setOnClickListener(v -> {
            if (capturedImage != null) {
                saveUserData();
            } else {
                Toast.makeText(this, "Debe capturar una imagen del iris", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Permiso de cámara requerido", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                
                imageCapture = new ImageCapture.Builder().build();
                
                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
                
                cameraProvider.unbindAll();
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
                
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void captureImage() {
        if (imageCapture == null) {
            Toast.makeText(this, "Cámara no inicializada", Toast.LENGTH_SHORT).show();
            return;
        }

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                super.onCaptureSuccess(image);
                
                // Convert ImageProxy to Bitmap
                Bitmap bitmap = imageProxyToBitmap(image);
                image.close();
                
                runOnUiThread(() -> {
                    if (bitmap != null) {
                        capturedImage = bitmap;
                        capturedImageView.setImageBitmap(bitmap);
                        capturedImageView.setVisibility(View.VISIBLE);
                        previewView.setVisibility(View.GONE);
                        btnCapture.setVisibility(View.GONE);
                        btnRetake.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.VISIBLE);
                        
                        // Generate hash for the captured image
                        irisHash = HashUtils.generateSHA256Hash(bitmap);
                        Toast.makeText(IrisActivity.this, "Imagen capturada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(IrisActivity.this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                runOnUiThread(() -> {
                    Toast.makeText(IrisActivity.this, "Error al capturar imagen: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void retakeImage() {
        capturedImage = null;
        irisHash = null;
        capturedImageView.setVisibility(View.GONE);
        previewView.setVisibility(View.VISIBLE);
        btnCapture.setVisibility(View.VISIBLE);
        btnRetake.setVisibility(View.GONE);
        btnContinue.setVisibility(View.GONE);
    }

    private void saveUserData() {
        // Convert fingerprint hashes list to comma-separated string
        StringBuilder fingerprintHashesString = new StringBuilder();
        for (int i = 0; i < fingerprintHashes.size(); i++) {
            if (i > 0) {
                fingerprintHashesString.append(",");
            }
            fingerprintHashesString.append(fingerprintHashes.get(i));
        }

        // Create user object
        User user = new User(name, country, city, registrationDate, 
                           fingerprintHashesString.toString(), irisHash);

        // Save to database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long userId = dbHelper.insertUser(user);
        dbHelper.close();

        if (userId > 0) {
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            
            // Navigate to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}

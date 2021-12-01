package com.mahmoudbashir.biometricfingerprintauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

     Button auth_btn;
     TextView authStateTv;
     Executor executor;
     BiometricPrompt prompt;
     BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth_btn = findViewById(R.id.auth_btn);
        authStateTv = findViewById(R.id.authStateTv);

        executor = ContextCompat.getMainExecutor(this);
        prompt  = new BiometricPrompt(MainActivity.this,
                executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        authStateTv.setText("Authentication error: "+ errString);
                        Toast.makeText(MainActivity.this,
                                "Some Error Occured!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        authStateTv.setText("Authentication succeed ! ");
                        Toast.makeText(MainActivity.this,
                                "Authentication succeed !", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        authStateTv.setText("Authentication Failed !");
                        Toast.makeText(MainActivity.this,
                                "Authentication Failed !", Toast.LENGTH_SHORT).show();
                    }
                });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerPrint authentication")
                .setNegativeButtonText("User App Password")
                .build();

        auth_btn.setOnClickListener(view -> {
            prompt.authenticate(promptInfo);
        });
    }
}
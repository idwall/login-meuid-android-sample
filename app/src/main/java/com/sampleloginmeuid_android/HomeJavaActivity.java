package com.sampleloginmeuid_android;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Uri deeplink = getIntent().getData();
        String code = deeplink.getQueryParameter("code");
        String codeVerifier = deeplink.getQueryParameter("code_verifier");

        // Handle parameters here...
        TextView codeTextView = findViewById(R.id.code);
        TextView codeVerifierTextView = findViewById(R.id.codeVerifier);

        codeTextView.setText("code = " + code);
        codeVerifierTextView.setText("code_verifier = " + codeVerifier);
    }
}

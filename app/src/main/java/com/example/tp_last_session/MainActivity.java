package com.example.tp_last_session;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    private static final int CALL_PERMISSION_REQUEST_CODE = 101;
    private static final String PHONE_NUMBER = "0661658250"; // Replace with the phone number you want to call

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOpenWebsite = findViewById(R.id.buttonOpenWebsite);
        buttonOpenWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });
        Button buttonOpenCamera = findViewById(R.id.buttonOpenCamera);
        buttonOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCameraPermission()) {
                    openCamera();
                } else {
                    requestCameraPermission();
                }
            }
        });
        Button buttonDial = findViewById(R.id.buttonDial);
        buttonDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneDial();
            }
        });
        Button buttonCall = findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCallPermission()) {
                    makePhoneCall();
                } else {
                    requestCallPermission();
                }
            }
        });
    }
    private boolean checkCallPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCallPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
    }

    private void makePhoneCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));

        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
        }
    }
    private void makePhoneDial() {
        String phoneNumber = "0661658250"; // Replace with the phone number you want to call
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
    private void openWebsite() {
        // Replace "https://www.example.com" with the URL of the website you want to open
        String url = "https://www.youtube.com/watch?v=w7cSaIPUO3k";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

}

package com.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.carpoolingapp.R;
import com.google.android.material.button.MaterialButton;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText reviewEditText;
    private MaterialButton submitButton, skipButton;
    private TextView titleText, subtitleText;

    private boolean isDemo = false;
    private String rideId;
    private String driverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        // Get intent extras
        isDemo = getIntent().getBooleanExtra("isDemo", false);
        rideId = getIntent().getStringExtra("rideId");
        driverId = getIntent().getStringExtra("driverId");

        initViews();
        setupListeners();
    }

    private void initViews() {
        ratingBar = findViewById(R.id.ratingBar);
        reviewEditText = findViewById(R.id.reviewEditText);
        submitButton = findViewById(R.id.submitButton);
        skipButton = findViewById(R.id.skipButton);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);

        if (isDemo) {
            titleText.setText("🎬 Demo: Rate Your Ride");
            subtitleText.setText("Try rating your demo driver!");
        }
    }

    private void setupListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipRating();
            }
        });
    }

    private void submitRating() {
        float rating = ratingBar.getRating();
        String review = reviewEditText.getText().toString().trim();

        if (rating == 0) {
            Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isDemo) {
            // Demo mode - just show success and go home
            Toast.makeText(this, "⭐ Demo rating submitted: " + rating + " stars!", Toast.LENGTH_SHORT).show();
            goToHome();
        } else {
            // TODO: Save rating to Firebase
            Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            goToHome();
        }
    }

    private void skipRating() {
        goToHome();
    }

    private void goToHome() {
        Intent intent = new Intent(RatingActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
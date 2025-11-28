package com.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.carpoolingapp.R;
import com.google.android.material.button.MaterialButton;

public class RideDetailActivity extends AppCompatActivity {

    private TextView fromText, toText, dateText, timeText, priceText, seatsText, driverNameText;
    private MaterialButton bookNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        initViews();
        setupToolbar();
        loadDemoData();
        setupListeners();
    }

    private void initViews() {
        fromText = findViewById(R.id.fromText);
        toText = findViewById(R.id.toText);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        priceText = findViewById(R.id.priceText);
        seatsText = findViewById(R.id.seatsText);
        driverNameText = findViewById(R.id.driverNameText);
        bookNowButton = findViewById(R.id.bookNowButton);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Ride Details");
        }
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void loadDemoData() {
        fromText.setText("Downtown Vancouver");
        toText.setText("Surrey Central");
        dateText.setText("Dec 15, 2024");
        timeText.setText("10:00 AM");
        priceText.setText("$15.00");
        seatsText.setText("3 seats available");
        driverNameText.setText("Demo Driver");
    }

    private void setupListeners() {
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to payment
                Intent intent = new Intent(RideDetailActivity.this, PaymentActivity.class);
                intent.putExtra("totalPrice", 15.00);
                intent.putExtra("isDemo", true);
                startActivity(intent);
            }
        });
    }
}
package com.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.carpoolingapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PaymentActivity extends AppCompatActivity {

    private TextInputEditText cardNumberEditText, expiryEditText, cvvEditText;
    private TextView totalPriceText;
    private MaterialButton makePaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();
        setupToolbar();
        setupListeners();
        loadPaymentInfo();
    }

    private void initViews() {
        cardNumberEditText = findViewById(R.id.cardNumberEditText);
        expiryEditText = findViewById(R.id.expiryEditText);
        cvvEditText = findViewById(R.id.cvvEditText);
        totalPriceText = findViewById(R.id.totalPriceText);
        makePaymentButton = findViewById(R.id.makePaymentButton);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupListeners() {
        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void loadPaymentInfo() {
        // Get data from intent
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        totalPriceText.setText("$" + String.format("%.2f", totalPrice));
    }

    private void processPayment() {
        String cardNumber = cardNumberEditText.getText().toString().trim();
        String expiry = expiryEditText.getText().toString().trim();
        String cvv = cvvEditText.getText().toString().trim();

        boolean isDemo = getIntent().getBooleanExtra("isDemo", false);

        // For demo, we're more lenient
        if (!isDemo && (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty())) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Basic validation (for non-demo)
        if (!isDemo && cardNumber.length() < 13) {
            Toast.makeText(this, "Invalid card number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulate payment processing
        makePaymentButton.setEnabled(false);
        makePaymentButton.setText("Processing...");

        // TODO: Integrate real payment gateway (Stripe, PayPal, etc.)

        // Simulate success after delay
        makePaymentButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PaymentActivity.this, "Payment successful!", Toast.LENGTH_SHORT).show();

                // Always go to chat after payment
                Intent intent = new Intent(PaymentActivity.this, ChatActivity.class);
                intent.putExtra("isDemo", isDemo);

                // Pass ride details
                intent.putExtra("rideId", getIntent().getStringExtra("rideId"));
                intent.putExtra("from", getIntent().getStringExtra("from"));
                intent.putExtra("to", getIntent().getStringExtra("to"));
                intent.putExtra("date", getIntent().getStringExtra("date"));
                intent.putExtra("time", getIntent().getStringExtra("time"));

                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
package com.carpoolingapp.activities;

// File: CarpoolingApp/app/src/main/java/com/carpooling/app/activities/ChatActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carpoolingapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private MaterialButton completeBookingButton;
    private MaterialCardView demoInfoCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupListeners();

        // Check if demo mode
        boolean isDemo = getIntent().getBooleanExtra("isDemo", false);
        if (isDemo) {
            showDemoMode();
        }
    }

    private void initViews() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        completeBookingButton = findViewById(R.id.completeBookingButton);
        demoInfoCard = findViewById(R.id.demoInfoCard);
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
                boolean isDemo = getIntent().getBooleanExtra("isDemo", false);
                if (isDemo) {
                    // Go back to home instead
                    goToHome();
                } else {
                    finish();
                }
            }
        });
    }

    private void setupRecyclerView() {
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set up message adapter
    }

    private void setupListeners() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        completeBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeBooking();
            }
        });
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            // TODO: Send message to Firebase
            messageEditText.setText("");
        }
    }

    private void showDemoMode() {
        // Show demo info card
        demoInfoCard.setVisibility(View.VISIBLE);

        // Show complete booking button
        completeBookingButton.setVisibility(View.VISIBLE);
    }

    private void completeBooking() {
        // Demo: Go to confirmation
        Intent intent = new Intent(ChatActivity.this, RideConfirmationActivity.class);
        intent.putExtra("confirmationType", "booking");
        intent.putExtra("isDemo", true);
        startActivity(intent);
        finish();
    }

    private void goToHome() {
        Intent intent = new Intent(ChatActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        boolean isDemo = getIntent().getBooleanExtra("isDemo", false);
        if (isDemo) {
            // Go back to home
            goToHome();
        } else {
            super.onBackPressed();
        }
    }
}
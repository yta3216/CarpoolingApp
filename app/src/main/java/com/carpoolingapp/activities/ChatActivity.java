package com.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carpoolingapp.R;
import com.carpoolingapp.adapters.MessageAdapter;
import com.carpoolingapp.models.Message;
import com.carpoolingapp.utils.SharedPrefsHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private MaterialButton completeBookingButton;
    private MaterialCardView demoInfoCard;

    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private SharedPrefsHelper prefsHelper;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        prefsHelper = new SharedPrefsHelper(this);
        handler = new Handler();

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupListeners();

        // Check if demo mode
        boolean isDemo = getIntent().getBooleanExtra("isDemo", false);
        if (isDemo) {
            showDemoMode();
        }

        // Add welcome message
        addSystemMessage("Chat started. Say hello!");
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
                    goToHome();
                } else {
                    finish();
                }
            }
        });
    }

    private void setupRecyclerView() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList, prefsHelper.getUserId());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messagesRecyclerView.setLayoutManager(layoutManager);
        messagesRecyclerView.setAdapter(messageAdapter);
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
        String messageText = messageEditText.getText().toString().trim();
        if (messageText.isEmpty()) {
            return;
        }

        // Add user's message
        String currentUserId = prefsHelper.getUserId();
        String currentUserName = prefsHelper.getUserName();

        Message userMessage = new Message(
                currentUserId,
                currentUserName,
                "other_user_id",
                messageText
        );
        messageList.add(userMessage);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.scrollToPosition(messageList.size() - 1);

        // Clear input
        messageEditText.setText("");

        // Trigger auto-reply after delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendAutoReply(messageText);
            }
        }, 1000); // 1 second delay
    }

    private void sendAutoReply(String userMessage) {
        String reply = generateAutoReply(userMessage);

        Message botMessage = new Message(
                "bot_id",
                "Driver",
                prefsHelper.getUserId(),
                reply
        );
        messageList.add(botMessage);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    private String generateAutoReply(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();

        // Simple pattern matching for auto-replies
        if (lowerMessage.contains("hi") || lowerMessage.contains("hello") || lowerMessage.contains("hey")) {
            return "Hi! How can I help you with the ride?";
        } else if (lowerMessage.contains("thanks") || lowerMessage.contains("thank you")) {
            return "You're welcome! 😊";
        } else if (lowerMessage.contains("where")) {
            return "I'll pick you up at the agreed location.";
        } else if (lowerMessage.contains("when") || lowerMessage.contains("time")) {
            return "We'll meet at the scheduled time. I'll be there!";
        } else if (lowerMessage.contains("price") || lowerMessage.contains("cost")) {
            return "The price is as listed in the booking details.";
        } else if (lowerMessage.contains("cancel")) {
            return "If you need to cancel, please do so through the app.";
        } else if (lowerMessage.contains("yes")) {
            return "Great! See you then!";
        } else if (lowerMessage.contains("no")) {
            return "No problem. Let me know if you change your mind!";
        } else if (lowerMessage.contains("ok") || lowerMessage.contains("okay")) {
            return "Perfect! 👍";
        } else if (lowerMessage.contains("bye")) {
            return "Goodbye! Have a great day!";
        } else {
            // Default reply for unrecognized messages
            return "Got it! If you have any questions, just ask.";
        }
    }

    private void addSystemMessage(String text) {
        Message systemMessage = new Message(
                "system",
                "System",
                prefsHelper.getUserId(),
                text
        );
        messageList.add(systemMessage);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
    }

    private void showDemoMode() {
        if (demoInfoCard != null) {
            demoInfoCard.setVisibility(View.VISIBLE);
        }

        if (completeBookingButton != null) {
            completeBookingButton.setVisibility(View.VISIBLE);
        }
    }

    private void completeBooking() {
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
            goToHome();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
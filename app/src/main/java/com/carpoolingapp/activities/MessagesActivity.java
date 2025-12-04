package com.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.carpoolingapp.R;
import com.carpoolingapp.adapters.ChatPreviewAdapter;
import com.carpoolingapp.models.ChatPreview;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView chatsRecyclerView;
    private View emptyState;
    private MaterialButton loadDemoButton;
    private BottomNavigationView bottomNav;

    private ChatPreviewAdapter adapter;
    private List<ChatPreview> chatList;
    private boolean demosLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        initViews();
        setupRecyclerView();
        setupBottomNav();
        setupListeners();
        loadChats();
    }

    private void initViews() {
        chatsRecyclerView = findViewById(R.id.chatsRecyclerView);
        emptyState = findViewById(R.id.emptyState);
        loadDemoButton = findViewById(R.id.loadDemoButton);
        bottomNav = findViewById(R.id.bottomNav);
    }

    private void setupRecyclerView() {
        chatList = new ArrayList<>();
        adapter = new ChatPreviewAdapter(this, chatList, new ChatPreviewAdapter.OnChatClickListener() {
            @Override
            public void onChatClick(ChatPreview chat) {
                if (chat.isDemo()) {
                    // Open demo chat
                    Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
                    intent.putExtra("isDemo", true);
                    intent.putExtra("otherUserName", chat.getOtherUserName());
                    startActivity(intent);
                } else {
                    // Open real chat
                    Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
                    intent.putExtra("chatId", chat.getChatId());
                    intent.putExtra("otherUserId", chat.getOtherUserId());
                    intent.putExtra("otherUserName", chat.getOtherUserName());
                    startActivity(intent);
                }
            }
        });

        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatsRecyclerView.setAdapter(adapter);
    }

    private void setupBottomNav() {
        bottomNav.setSelectedItemId(R.id.nav_messages);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                try {
                    Intent intent = new Intent(MessagesActivity.this, HomeActivity.class);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(MessagesActivity.this, "Error opening Home", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (itemId == R.id.nav_create) {
                try {
                    Intent intent = new Intent(MessagesActivity.this, CreateRideActivity.class);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(MessagesActivity.this, "Error opening Create Ride", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (itemId == R.id.nav_messages) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                try {
                    Intent intent = new Intent(MessagesActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(MessagesActivity.this, "Error opening Profile", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return false;
        });
    }

    private void setupListeners() {
        loadDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDemoChats();
            }
        });
    }

    private void loadChats() {
        // TODO: Load real chats from Firebase
        // For now, show empty state with demo button
        updateUI();
    }

    private void loadDemoChats() {
        if (demosLoaded) {
            Toast.makeText(this, "Demo chats already loaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create demo chat previews
        long currentTime = System.currentTimeMillis();

        ChatPreview demo1 = new ChatPreview(
                "demo_chat_1",
                "demo_driver_1",
                "Sarah Johnson",
                "Thanks for booking! See you tomorrow at 10 AM 🚗",
                currentTime - 300000, // 5 minutes ago
                2,
                true
        );

        ChatPreview demo2 = new ChatPreview(
                "demo_chat_2",
                "demo_driver_2",
                "Mike Chen",
                "I'm running 10 minutes late, sorry!",
                currentTime - 3600000, // 1 hour ago
                0,
                true
        );

        ChatPreview demo3 = new ChatPreview(
                "demo_chat_3",
                "demo_rider_1",
                "Emma Wilson",
                "Can I bring an extra bag?",
                currentTime - 7200000, // 2 hours ago
                1,
                true
        );

        ChatPreview demo4 = new ChatPreview(
                "demo_chat_4",
                "demo_driver_3",
                "James Anderson",
                "Great ride today! Thanks for the conversation 😊",
                currentTime - 86400000, // 1 day ago
                0,
                true
        );

        chatList.add(demo1);
        chatList.add(demo2);
        chatList.add(demo3);
        chatList.add(demo4);

        demosLoaded = true;
        adapter.notifyDataSetChanged();
        updateUI();

        Toast.makeText(this, "✅ Loaded 4 demo conversations!", Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        if (chatList.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            chatsRecyclerView.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            chatsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_messages);
        }
    }
}
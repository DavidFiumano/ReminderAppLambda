package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Friend_Activity extends AppCompatActivity {
    EditText friendEmail;
    ListView pendingFriends;
    String userEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_);
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        user = UserWrapper.getUser(userEmail);
        initializeUI();
        initializePendingFriendsList();
    }

    private void initializeUI(){
        friendEmail = findViewById(R.id.editFriendEmail);
        pendingFriends = findViewById(R.id.listViewPendingFriend);
    }

    private void initializePendingFriendsList(){

        user.pendingFriends = new ArrayList<User>();
        user.pendingFriends.add(new User("Tom", "fakeemail"));
        user.pendingFriends.add(new User("Tim", "fakeemail"));
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, user.pendingFriends);
        pendingFriends.setAdapter(adapter);
    }
}

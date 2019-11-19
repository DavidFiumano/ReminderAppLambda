package com.example.taskmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Friend_Activity extends AppCompatActivity {
    EditText friendEmail;
    ListView pendingFriends;
    String userEmail;
    User user;
    int currentPos;
    AlertDialog actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_);
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        UserWrapper.masterEmail = userEmail;
        initializeUI();
        UserWrapper.updateFriendActivity(pendingFriends, friendEmail, this, "FRIENDACTIVITY");
        try {
            user = UserWrapper.getUser(userEmail);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //initializePendingFriendsList();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to accept?");
        String[] options = {"Accept"};

        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
        pendingFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPos = position;
                actions.show();
            }
        });
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
    public void requestFriend(View v){

//        if (friendEmail.getText().toString() == null){
//            Toast.makeText(this, "Please enter an email", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (UserWrapper.checkUser(friendEmail.getText().toString()," ")){
//            UserWrapper.requestFriend(friendEmail.getText().toString(),"");//TODO Change
//            Toast.makeText(this, "Request sent", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "Can not find user", Toast.LENGTH_LONG).show();
//        }
        UserWrapper.requestFriend(userEmail, ((User)(pendingFriends.getAdapter().getItem(currentPos))).email);
        //UserWrapper.checkUser(friendEmail.getText().toString(), "");
    }

    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // complete
                            UserWrapper.requestFriend(userEmail, ((User)(pendingFriends.getAdapter().getItem(currentPos))).email);
                            //UserWrapper.checkUser(userEmail, ((User)(pendingFriends.getAdapter().getItem(currentPos))).email);
                            break;
                        default:
                            break;
                    }
                }
            };

}

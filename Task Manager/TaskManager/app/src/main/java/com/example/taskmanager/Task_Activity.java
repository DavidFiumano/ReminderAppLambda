package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Task_Activity extends AppCompatActivity {
    String userEmail;
    User user;
    Spinner days;
    TimePicker picker;
    int hour, min;
    Spinner friendSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_);
        initializeUI();
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("EMAIL");
        user = UserWrapper.getUser(userEmail);
        user.friends = new ArrayList<User>();
        user.friends.add(new User("Jake", "email"));
        user.friends.add(new User("Brad", "email"));
        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_spinner_item, user.friends);
        friendSpinner.setAdapter(adapter);


    }

    public void createTask(View v){
        hour = picker.getHour();
        min = picker.getMinute();
        System.out.println("Hour is " + hour + " Minute is " + min);
    }

    private void initializeUI(){
        days = findViewById(R.id.task_days_spinner);
        ArrayAdapter<CharSequence> qAdapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(qAdapter);
        //Code for timepicker
        picker=findViewById(R.id.timePicker);
        picker.setIs24HourView(false);
        friendSpinner = findViewById(R.id.friendSpinner);

    }
}

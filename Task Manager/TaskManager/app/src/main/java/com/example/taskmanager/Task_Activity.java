package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class Task_Activity extends AppCompatActivity {
    String userEmail;
    User user;
    Spinner days, taskDay;
    TimePicker picker;
    int hour, min;
    ListView friendListView;
    ArrayList<User> friendList = new ArrayList<User>();
    TextView friendsTextView;
    View button;
    EditText daysEditText, nameEditText;
    RadioButton period;

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
        friendListView.setAdapter(adapter);

        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String temp = friendsTextView.getText().toString();
                temp = temp + " " + friendListView.getAdapter().getItem(position);
                friendsTextView.setText(temp);
                friendList.add((User)(friendListView.getAdapter().getItem(position)));
            }
        });



    }

    public void createTask(View v){
        if (friendList.isEmpty()){
            Toast.makeText(this, "Please add friend to share request", Toast.LENGTH_LONG).show();
        }
        if (period.isChecked() && daysEditText.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a correct period integer", Toast.LENGTH_LONG).show();
        }
        if (nameEditText.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a name for task", Toast.LENGTH_LONG).show();
        }

        Task newTask = new Task();
        newTask.name = nameEditText.getText().toString();
        newTask.users = friendList;
        newTask.users.add(user);
        newTask.isCompleted = false;
        if (period.isChecked()){
            newTask.isInterval = true;
            newTask.interval = Integer.parseInt(daysEditText.getText().toString());
            newTask.day = "N/A";
        } else {
            newTask.isInterval = false;
            newTask.interval = 0;
            newTask.day = taskDay.getSelectedItem().toString();
        }
        newTask.nextAlarmDay = "N/A";

        hour = picker.getHour();
        min = picker.getMinute();

        UserWrapper.putTask(newTask, friendList);
        ArrayList<User> tempUserList = new ArrayList<User>();
        tempUserList.add(user);
        UserWrapper.putTask(newTask, tempUserList);
        //System.out.println("Hour is " + hour + " Minute is " + min);
    }

    private void initializeUI(){
        days = findViewById(R.id.task_days_spinner);
        ArrayAdapter<CharSequence> qAdapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(qAdapter);
        //Code for timepicker
        picker=findViewById(R.id.timePicker);
        picker.setIs24HourView(false);
        friendListView = findViewById(R.id.friendList);
        friendsTextView = findViewById(R.id.friendTextView);
        daysEditText = findViewById(R.id.daysEditText);
        daysEditText.setEnabled(false);
        taskDay = findViewById(R.id.task_days_spinner);
        period = findViewById(R.id.radio_period);
        nameEditText = findViewById(R.id.nameEditText);

    }

    public void resetNames(View view){
        friendsTextView.setText("");
        friendList = new ArrayList<User>();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        button = view;

        switch (view.getId()) {
            case R.id.radio_interval:
                if (checked) {
                    taskDay.setEnabled(true);
                    daysEditText.setEnabled(false);
                }
                break;
            case R.id.radio_period:
                if (checked) {
                    taskDay.setEnabled(false);
                    daysEditText.setEnabled(true);
                }
                break;

        }
    }

}

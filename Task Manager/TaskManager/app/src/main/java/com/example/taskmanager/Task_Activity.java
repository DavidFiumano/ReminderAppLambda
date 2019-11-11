package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

public class Task_Activity extends AppCompatActivity {
    Spinner days;
    TimePicker picker;
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_);
        days = findViewById(R.id.task_days_spinner);
        ArrayAdapter<CharSequence> qAdapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        qAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(qAdapter);
        //Code for timepicker
        picker=findViewById(R.id.timePicker);
        picker.setIs24HourView(false);
    }

    public void createTask(View v){
        hour = picker.getHour();
        min = picker.getMinute();
        System.out.println("Hour is " + hour + " Minute is " + min);
    }
}

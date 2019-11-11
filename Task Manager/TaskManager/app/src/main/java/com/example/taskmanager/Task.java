package com.example.taskmanager;

import java.util.ArrayList;

public class Task {
    public String name;
    public int id; // ID or Key in the database
    public ArrayList<User> users = new ArrayList<User>(); // ArrayList of users that has this task
    public boolean isInterval; //is this task an interval task. i.e every 2 days
    public int interval; //the interval of the task i.e every 2 days
    public String day;
    public int hour;
    public int minute;

    public Task(){

    }

    @Override
    public String toString(){
        return name;
    }

}

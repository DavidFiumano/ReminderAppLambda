package com.example.taskmanager;

import java.util.ArrayList;

public class Task {
    public String name;
    public String id; // ID or Key in the database
    public ArrayList<User> users = new ArrayList<User>(); // ArrayList of users that has this task
    public boolean isInterval, isCompleted; //is this task an interval task. i.e every 2 days, isCompleted is if the task has been completed
    public int interval; //the interval of the task i.e every 2 days
    public String day; //to see which day the task needs to be completed i.e. every Monday, Tuesday
    public int hour;
    public int minute;
    public String nextAlarmDay;


    public Task(){

    }

    public Task(String id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

    public int getUniqueID(){
        return this.id.hashCode();
    }

}

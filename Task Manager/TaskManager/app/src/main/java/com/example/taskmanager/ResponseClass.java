package com.example.taskmanager;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ResponseClass {
    public String body;
    public String body2;
    public int statusCode;
    public String friend;
    public String friendRequest;
    public String task;
    //public ArrayList<String> friendsList = new ArrayList<>();        //TODO implement
    //public ArrayList<String> incomingRequests = new ArrayList<>();
    //public ArrayList<String> taskList = new ArrayList<>();

    public String getBody() {

        return body;
    }public String getBody2() {

        return body2;
    }
    public int getStatusCode(){
        return statusCode;
    }
    public String getFriend(){
        return friend;
    }
    public String getFriendRequest(){
        return friendRequest;
    }
    public String getTask(){
        return task;
    }


    public void setBody(String body) {
        this.body = body;
    }

    public ResponseClass(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public ResponseClass(int statusCode, String body2, String friends, String friendRequests, String taskIDs) {
        this.statusCode = statusCode;
        this.body2 = body2;
        this.friend = friends;
        this.friendRequest = friendRequests;
        this.task = taskIDs;


    }

    public ResponseClass() {
    }
}

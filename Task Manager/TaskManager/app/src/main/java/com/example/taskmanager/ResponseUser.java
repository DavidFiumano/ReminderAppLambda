package com.example.taskmanager;

public class ResponseUser {
    public static String body;
    public static String body2;
    public static int statusCode;
    public static String friend;
    public static String friendRequest;
    public static String task;
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

    public ResponseUser(int statusCode, String body2, String friends, String friendRequests, String taskIDs) {
        this.statusCode = statusCode;
        this.body2 = body2;
        this.friend = friends;
        this.friendRequest = friendRequests;
        this.task = taskIDs;


    }

    public ResponseUser() {
    }
}

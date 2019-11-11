package com.example.taskmanager;

import java.util.ArrayList;

public class User {
    public String email, name;
    public ArrayList<User> friends;
    public  ArrayList<User> pendingFriends;
    public ArrayList<Task> tasks;


    public User( String email, String name, ArrayList<User> friends, ArrayList<User> pendingFriends, ArrayList<Task> tasks){
        this.email = email;
        this.name = name;
        this.friends = friends;
        this.pendingFriends = pendingFriends;
        this.tasks = tasks;
    }

    public User(){

    }

    @Override
    public String toString(){
        return name;
    }
}

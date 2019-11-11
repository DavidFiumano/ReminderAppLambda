package com.example.taskmanager;

import java.util.ArrayList;

public class UserWrapper {


    //Checks to see if user is in the database
    public boolean checkUser(String email){
        return false;
    }

    //Adds user to database
    public void addUser(User user){}

    //Gets a user from an email address
    public User getUser(String email){
        User user = new User();
        return user;
    }

    //gets a task with the id used
    public Task getTask(int id){
        Task task = new Task();
        return task;
    }

    //puts a task in the database
    public void putTask(Task task){

    }

    //sends a friend request to another user
    public void requestFriend(String email){

    }

    //once someone confirms the friend request, we move them from the pendingFriend list to friend list
   public void confirmFriendRequest(String userEmail, String friendEmail){

   }



    //May not need code below
//
//    //get the tasks the user current has
//    public ArrayList<Task> getUserTasks(String email){
//        ArrayList<Task> retVal = new ArrayList<Task>();
//
//        return retVal;
//    }
//
//    //get your current friend list, return an array of their email
//    public ArrayList<User> getFriendList(String email){
//        ArrayList<User> retVal = new ArrayList<User>();
//
//        return retVal;
//    }

}

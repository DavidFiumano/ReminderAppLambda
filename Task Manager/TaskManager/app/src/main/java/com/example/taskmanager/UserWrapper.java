package com.example.taskmanager;

import java.util.ArrayList;

public class UserWrapper {


    //Checks to see if user is in the database
    public static boolean checkUser(String email){
        return false;
    }

    //Adds user to database, add a user with just email
    public static void addUser(User user){}

    //Gets a user from an email address
    public static User getUser(String email){
        User user = new User();
        return user;
    }

    //gets a task with the id used
    public static Task getTask(String id){
        Task task = new Task();
        return task;
    }

    //puts a task in the database
    public static void putTask(Task task, ArrayList<User> users){

    }



    //remove the task from the user
    public static void deleteTask(Task task, User user){

    }

    //task is completed and lets all the users that have the task know that its been completed.
    public static void completeTask(Task task, User user){

    }

    //remove the task from the database
    public static void deleteTaskFromDatabase(Task task){}

    //sends a friend request to another user
    public static void requestFriend(String email){

    }

    //once someone confirms the friend request, we move them from the pendingFriend list to friend list
   public static void confirmFriendRequest(String userEmail, String friendEmail){

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

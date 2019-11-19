package com.example.taskmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import android.os.Handler;

public class UserWrapper {
    public static String buffer;
    public static boolean bufferBool;
    private static Context currContext;
    public static boolean hasExecuted;
    public static boolean hasGetUserExecuted = false;
    //private Context currContext;

    public static void setContext(Context context){

        currContext = context;
    }
    public static  void setBufferBool(boolean test){
        bufferBool = test;
    }
    public static void setHasExecuted(Boolean test0){
        hasExecuted = test0;
    }


    public static Context getContext(){
        return currContext;
    }

    private static LambdaInvokerFactory setCognito(){
        CognitoCachingCredentialsProvider cognitoProvider =
                new CognitoCachingCredentialsProvider(
                        currContext,
                        "us-east-2:d5f254a0-c6a5-42d9-b22d-5cc4feb65abb",
                        Regions.US_EAST_2);
        LambdaInvokerFactory factory = new LambdaInvokerFactory(currContext,
                Regions.US_EAST_2, cognitoProvider);
        //final MyInterface myInterface = factory.build(MyInterface.class);
        return factory;
    }


    public static class GetUserAsync extends AsyncTask<RequestClass, Void, ResponseClass> {
        //private Context currContext;
        String email2;


        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);

        public GetUserAsync(String email) {
            email2 = email;
        }

        @Override
        protected  ResponseClass doInBackground(RequestClass... params) {
            try {
                ResponseClass x = myInterface.doesUserExist(params[0]);
                 int i = x.getStatusCode();
                if (x == null) {
                    return null;
                }else if(x.getStatusCode() == 1){
                    //Toast.makeText(currContext,  "TRUE", Toast.LENGTH_LONG).show();
                    UserWrapper.setBufferBool(true);
                    UserWrapper.setHasExecuted(true);
                }else{
                    //Toast.makeText(currContext,  "FALSE", Toast.LENGTH_LONG).show();
                    UserWrapper.setHasExecuted(true);
                }
                return myInterface.doesUserExist(params[0]);
            } catch (LambdaFunctionException lfe) {
                Log.e("Tag", "Failed to invoke echo", lfe);
                return null;
            }
        }
        @Override
        protected void onPostExecute(ResponseClass result) {
            Log.d("test", "Result is: " +result);

        }

    }


    //Checks to see if user is in the database
    public static boolean checkUser( String email){

        RequestClass request = new RequestClass(email);
        GetUserAsync c = new GetUserAsync(email);
        c.execute(request);

        while (hasExecuted==false){

        }
        hasExecuted=false;
        return bufferBool;
    }

    //Adds user to database, add a user with email and name
    public static void addUser(User user){
        String names[] = user.name.split(" ");

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass(user.email, names[0], names[1]);
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.createUser(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    return;
                }
            }
        }.execute(request);

    }

    //Gets a user from an email address
    public static User getUser(String email) throws ExecutionException, InterruptedException {
        User user = new User();
        String[] details;
        String[] friends;
        String[] friendRequests;
        String[] tasksIDs;
        final String[] body = {" "};
        ArrayList<String> test = new ArrayList<>();

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass(email);
        Object result = new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    ResponseClass result = myInterface.getUser(params[0]);

                    if(result.getStatusCode() == 0){
                        body[0] = result.getBody();
                        System.out.println(body[0]);
                    }
                    hasGetUserExecuted = true;
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
                return null;
            }
        }.execute(request);

        while (hasGetUserExecuted==false){

        }
        hasGetUserExecuted = false;
        details = body[0].split(" ");
        user.email = details[0];
        user.name = details[1] + " " + details[2];
        friends = details[3].split(",");
        if(friends == null || friends[0].equals("None")){
            user.friends = null;
        }else{
            user.friends = new ArrayList<>();
            for(String emails : friends){
                user.friends.add(getFriend(emails));
            }
        }
        friendRequests = details[4].split(",");
        if(friendRequests == null || friendRequests[0].equals("None")){
            user.pendingFriends = null;
        }else{
            user.pendingFriends = new ArrayList<>();
            for(String emails : friendRequests){
                user.pendingFriends.add(getFriend(emails));
            }
        }
        tasksIDs = details[5].split(",");
        if(tasksIDs == null || tasksIDs[0].equals("None")){
            user.tasks = null;
        }else{
            user.tasks = new ArrayList<>();
            for(String ids : tasksIDs){
                user.tasks.add(getTask(ids));
            }
        }
        return user;
    }


    public static User getFriend(String email) throws ExecutionException, InterruptedException {
        User user = new User();
        String[] details;
        final String[] body = {" "};
        ArrayList<String> test = new ArrayList<>();


        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass(email);
        Object result = new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    ResponseClass result = myInterface.getUser(params[0]);

                    if(result.getStatusCode() == 0){
                        body[0] = result.getBody();
                    }
                    hasExecuted = true;
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
                return null;
            }
        }.execute(request);

        while (hasExecuted==false){

        }
        hasExecuted = false;
        details = body[0].split(" ");
        user.email = details[0];
        user.name = details[1] + " " + details[2];

        return user;
    }

        //gets a task with the id used
    public static Task getTask(String id){
        final boolean[] exist = {false};
        final String[] temp = new String[0];

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);


        RequestClass request = new RequestClass();
        request.setTaskId(id);

        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.doesTaskExist(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    return;
                }else if(result.getStatusCode() == 1){
                    exist[0] = true;
                }
            }
        }.execute(request);

        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.getReminder(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    return;
                }else if(result.getStatusCode() == 200){
                    temp[0] = result.getBody();
                }
            }
        }.execute(request);

        Task task = new Task();
        return task;
    }

    //puts a task in the database
    public static void putTask(Task task, ArrayList<User> users){

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass("temp");
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.doesUserExist(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }
            @Override
            protected void onPostExecute(ResponseClass result) {
                if (result == null) {
                    return;
                }
            }
        }.execute(request);



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

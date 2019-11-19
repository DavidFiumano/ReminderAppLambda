package com.example.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.google.android.gms.tasks.Tasks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import android.os.Handler;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserWrapper {
    public static String buffer;
    public static boolean bufferBool;
    private static Context currContext;
    public static boolean hasExecuted = false;
    public static boolean hasGetUserExecuted = false;
    public static boolean hasGetFriendExecuted = false;
    public static User globalUser;
    public static ArrayList<User> globalFriends;
    public static ArrayList<User> globalPendingFriends;
    public static ArrayList<Tasks> globalTasks;
    public static ListView secondActivityTaskList;
    public static ListView friendActivityPendingFriends;
    public static ListView taskActivityFriendList;
    public static EditText friendActivityFriendEmail;
    public static String where = "";
    public static String masterEmail;

    public static boolean getTasksMain = false;
    public static boolean getTasksUser = false;
    public static boolean hasTaskExecuted = false;
    public static String getTasksBuffer;
    public static ArrayList<Task> globalTaskList;

    public static String[] dayOfWeek = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    //private Context currContext;

    public static Task testTask;

    public static User returnGetUser(User user){
        globalUser=user;
        return user;
    }

    public static void setBuffer(String bfufer){
        buffer = bfufer;
    }
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

    private static LambdaInvokerFactory setCognito(Context currContext){
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
        String name;


        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);

        public GetUserAsync(String email, String name) {
            email2 = email;
            this.name = name;
        }

        @Override
        protected  ResponseClass doInBackground(RequestClass... params) {
            try {
                ResponseClass x = myInterface.doesUserExist(params[0]);
//                 int i = x.getStatusCode();
//                if (x == null) {
//                    return null;
//                }else if(x.getStatusCode() == 1){
//                    //Toast.makeText(currContext,  "TRUE", Toast.LENGTH_LONG).show();
//                    UserWrapper.setBufferBool(true);
//                    UserWrapper.setHasExecuted(true);
//                    if(where.equals("SECONDACTIVITY")) {
//                        try {
//                            getUser(email2);
//                        } catch (ExecutionException e) {
//                            e.printStackTrace();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (where.equals("FRIENDACTIVITY")){
////                        UserWrapper.requestFriend("",email2);
////                        UserWrapper.getUser(masterEmail);
//                        //Toast.makeText(currContext, "Request Sent", Toast.LENGTH_LONG).show();
//                    }
//                }else{
//                    //Toast.makeText(currContext,  "FALSE", Toast.LENGTH_LONG).show();
//                    if(where.equals("SECONDACTIVITY")) {
//                        addUser(new User(email2, name, null, null, null));
//                        UserWrapper.setHasExecuted(true);
//                    }
//                    if(where.equals("FRIENDACTIVITY")){
//                        //Toast.makeText(currContext, "Person does not exist", Toast.LENGTH_LONG).show();
//                    }
//                }
                return myInterface.doesUserExist(params[0]);
            }  catch (LambdaFunctionException lfe) {
                Log.e("Tag", "Failed to invoke echo", lfe);
                return null;
            }
        }
        @Override
        protected void onPostExecute(ResponseClass result) {

            Integer x = result.getStatusCode();
            if (x == null) {
                return ;
            }else if(x == 1){
                //Toast.makeText(currContext,  "TRUE", Toast.LENGTH_LONG).show();
                UserWrapper.setBufferBool(true);
                UserWrapper.setHasExecuted(true);
                if(where.equals("SECONDACTIVITY")) {
                    try {
                        getUser(email2);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (where.equals("FRIENDACTIVITY")){
//                        UserWrapper.requestFriend("",email2);
//                        UserWrapper.getUser(masterEmail);
                    //Toast.makeText(currContext, "Request Sent", Toast.LENGTH_LONG).show();
                }
            }else{
                //Toast.makeText(currContext,  "FALSE", Toast.LENGTH_LONG).show();
                if(where.equals("SECONDACTIVITY")) {
                    addUser(new User(email2, name, null, null, null));
                    UserWrapper.setHasExecuted(true);
                }
                if(where.equals("FRIENDACTIVITY")){
                    //Toast.makeText(currContext, "Person does not exist", Toast.LENGTH_LONG).show();
                }
            }


        }

    }


    //Checks to see if user is in the database
    public static boolean checkUser(String email, String name){

        RequestClass request = new RequestClass(email);
        GetUserAsync c = new GetUserAsync(email, name);
        c.execute(request);

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


        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass(email);
        Object result = new AsyncTask<RequestClass, Void, User>() {
            @Override
            protected User doInBackground(RequestClass... params) {
                try {
                    ResponseClass result = myInterface.getUser(params[0]);
                    if (result.getStatusCode() == 0) {
                        User user = new User();
                        String[] details;
                        String[] friends;
                        String[] friendRequests;
                        String[] tasksIDs;
                        final String[] body = {" "};
                        ArrayList<String> test = new ArrayList<>();
                        body[0] = result.getBody();
                        System.out.println(body[0]);
                        details = body[0].split(" ");
                        user.email = details[0];
                        user.name = details[1] + " " + details[2];
                        friends = details[3].split(",");
                        if (friends == null || friends[0].equals("None")) {
                            user.friends = null;
                        } else {
                            user.friends = new ArrayList<>();
                            for (String emails : friends) {

                                user.friends.add(new User(emails, emails, null, null, null));

                            }
                        }
                        friendRequests = details[4].split(",");
                        if (friendRequests == null || friendRequests[0].equals("None")) {
                            user.pendingFriends = null;
                        } else {
                            user.pendingFriends = new ArrayList<>();
                            for (String emails : friendRequests) {
                                user.pendingFriends.add(new User(emails, emails, null, null, null));
                            }
                        }
                        tasksIDs = details[5].split(",");
                        if (tasksIDs == null || tasksIDs[0].equals("None")) {
                            user.tasks = null;
                        } else {
                            user.tasks = new ArrayList<>();
                            for (String ids : tasksIDs) {

//

                                user.tasks.add( new Task(ids));
                                hasTaskExecuted=false;
                            }
                        }
                        return user;
                    }

                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    System.out.println("Error in getUser");

                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(User result) {


                globalUser = result;

                if (where.equals("SECONDACTIVITY")) {
                    if (globalUser.tasks == null){
                        globalUser.tasks = new ArrayList<Task>();
                    }
                    globalTaskList = new ArrayList<Task>();
                    for (Task t : globalUser.tasks){
                        getTask(t.id);
                    }
                    //Toast.makeText(currContext, "SHOW TEXT", Toast.LENGTH_SHORT).show();
                    ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(currContext, android.R.layout.simple_list_item_1, globalUser.tasks);
                    secondActivityTaskList.setAdapter(adapter);
                }

                if(where.equals("FRIENDACTIVITY")){

                    if (globalUser.pendingFriends == null){
                        globalUser.pendingFriends = new ArrayList<User>();
                    }
                    globalTaskList = new ArrayList<Task>();
                    for (Task t : globalUser.tasks){
                        getTask(t.id);
                    }
                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(currContext, android.R.layout.simple_list_item_1, globalUser.pendingFriends);
                    friendActivityPendingFriends.setAdapter(adapter);
                }

                if(where.equals("TASKACTIVITY")){
                    globalTaskList = new ArrayList<Task>();
                    for (Task t : globalUser.tasks){
                        getTask(t.id);
                    }
                    if (globalUser.friends == null){
                        globalUser.friends = new ArrayList<User>();
                    }


                    ArrayAdapter<User> adapter = new ArrayAdapter<User>(currContext, android.R.layout.simple_list_item_1, globalUser.friends);
                    taskActivityFriendList.setAdapter(adapter);
                }


            }
        }.execute(request);


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
                    hasGetFriendExecuted = true;
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
                return null;
            }
        }.execute(request);

        while (hasGetFriendExecuted==false){

        }
        hasGetFriendExecuted = false;
        details = body[0].split(" ");
        user.email = details[0];
        user.name = details[1] + " " + details[2];
        return user;
    }

        //gets a task with the id used
    public static Task getTask(String id){

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);

        RequestClass request = new RequestClass();
        request.setTaskId(id);
        Task tempTask = new Task();

             new AsyncTask<RequestClass, Void, Task>() {
                @Override
                protected Task doInBackground(RequestClass... params) {
                    try {
                        ResponseClass x = myInterface.getReminders(params[0]);
                        if(x.getStatusCode() == 0){
                            getTasksBuffer = x.getBody();
                            Task task = new Task();
                            String[] tempBuffer = getTasksBuffer.split("=");
                            task.id = tempBuffer[0];
                            task.name = tempBuffer[1];
                            switch(tempBuffer[2]){
                                case "Interval":
                                    task.isInterval = true;
                                    break;
                                case "Weekly":
                                    task.isInterval = false;
                                    break;
                                default:
                                    break;
                            }String[] argsBuffer = tempBuffer[3].split(" ");
                            if(task.isInterval){
                                task.interval = Integer.parseInt(argsBuffer[0]);
                                String[] nextSchedule = tempBuffer[5].split(" ");
                                switch(nextSchedule[0]){
                                    case "Monday":
                                        task.nextAlarmDay = "MONDAY";
                                        break;
                                    case "Tuesday":
                                        task.nextAlarmDay = "TUESDAY";
                                        break;
                                    case "Wednesday":
                                        task.nextAlarmDay = "WEDNESDAY";
                                        break;
                                    case "Thursday":
                                        task.nextAlarmDay = "THURSDAY";
                                        break;
                                    case "Friday":
                                        task.nextAlarmDay = "FRIDAY";
                                        break;
                                    case "Saturday":
                                        task.nextAlarmDay = "SATURDAY";
                                        break;
                                    case "Sunday":
                                        task.nextAlarmDay = "SUNDAY";
                                        break;
                                    default:
                                        break;
                                }

                            }else{
                                switch(argsBuffer[0]){
                                    case "Monday":
                                        task.day = "MONDAY";
                                        break;
                                    case "Tuesday":
                                        task.day = "TUESDAY";
                                        break;
                                    case "Wednesday":
                                        task.day = "WEDNESDAY";
                                        break;
                                    case "Thursday":
                                        task.day = "THURSDAY";
                                        break;
                                    case "Friday":
                                        task.day = "FRIDAY";
                                        break;
                                    case "Saturday":
                                        task.day = "SATURDAY";
                                        break;
                                    case "Sunday":
                                        task.day = "SUNDAY";
                                        break;
                                    default:
                                        break;
                                }
                            }
                            switch(tempBuffer[6]){
                                case "True":
                                    task.isCompleted = true;
                                    break;
                                case "False":
                                    task.isCompleted = false;
                                    break;
                            }
                            String[] argsBuffer2 = argsBuffer[1].split(":");
                            task.hour = Integer.parseInt(argsBuffer2[0]);
                            task.minute = Integer.parseInt(argsBuffer2[1]);
                            String[]users = tempBuffer[4].split(",");
                            task.users = new ArrayList<>();
                            for(String emails : users){
                                task.users.add(new User(emails));
                            }
                            testTask = task;
                            return task;

                        }
                    } catch (LambdaFunctionException lfe) {
                        Log.e("Tag", "Failed to invoke echo", lfe);
                        return null;
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Task task) {
                    globalTaskList.add(task);
                   // ArrayList<Task> todayTasks = returnTodayTask(globalTaskList);

                    //AdapterTask adapter = new AdapterTask((Activity)currContext, android.R.layout.simple_list_item_1, todayTasks);
                    if(globalTaskList!=null) {
                        setAlarm(globalTaskList);
                    }
                    if (where.equals("SECONDACTIVITY")) {
                        AdapterTask adapter = new AdapterTask((Activity) currContext, android.R.layout.simple_list_item_1, globalTaskList);
                        secondActivityTaskList.setAdapter(adapter);
                    }
                }
            }.execute(request);



        return tempTask;
    }

    //puts a task in the database
    public static void putTask(Task task, ArrayList<User> users){

        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass();
        request.taskId = task.id;
        for(User x : users){
            request.Users += x.email + ",";
        }
        request.Users = request.Users.substring(0, request.Users.length() - 1);
        request.email = users.get(0).email;
        request.Name = task.name;
        request.description = task.name;
        request.ScheduleType = task.isInterval ? "Interval" : "Weekly";
        request.ScheduleArgs = task.isInterval ? task.interval + " " + task.hour + ":" + task.minute + ":00": task.day + " " + task.hour + ":" + task.minute + ":00";

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                request.CurrentDay = "Sunday";
                break;
            case Calendar.MONDAY:
                request.CurrentDay = "Monday";
                break;
            case Calendar.TUESDAY:
                request.CurrentDay = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                request.CurrentDay = "Wednesday";
                break;
            case Calendar.THURSDAY:
                request.CurrentDay = "Thursday";
                break;
            case Calendar.FRIDAY:
                request.CurrentDay = "Friday";
                break;
            case Calendar.SATURDAY:
                request.CurrentDay = "Saturday";
                break;
            default:
                break;
        }
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        request.CurrentDay += " " + currentTime;


        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.createReminder(params[0]);
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



    //remove the task from the database
    public static void deleteTaskFromDatabase(Task task){}

    //sends a friend request to another user
    public static void requestFriend(String userEmail, String friendEmail){
        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass();
        request.userEmail = masterEmail;
        request.friendEmail = friendEmail;
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.addFriend(params[0]);
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

    public static void updateSecondActivity(ListView tasklist, Context context, String whereFrom){
        secondActivityTaskList = tasklist;
        currContext = context;
        where = whereFrom;
    }

    public static void updateFriendActivity(ListView pendingFriends, EditText friendEmail, Context context, String whereFrom){
        friendActivityPendingFriends = pendingFriends;
        currContext = context;
        friendActivityFriendEmail = friendEmail;
        where = whereFrom;
    }

    public static void updateTaskActivity(ListView friendListView, Context context, String whereFrom){
        taskActivityFriendList = friendListView;
        currContext = context;
        where = whereFrom;
    }

    public static ArrayList<Task> returnTodayTask(ArrayList<Task> t){
        Calendar calendar = Calendar.getInstance();
        ArrayList<Task> todayTask = new ArrayList<Task>();
        if(t != null) {
            for (Task temp : t) {
                String dayTask = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)];
                if (dayTask.equalsIgnoreCase(temp.nextAlarmDay)) {
                    todayTask.add(temp);
                }
            }
        }
        return todayTask;
    }

    public static void setAlarm(ArrayList<Task> tasks){

        for (Task temp : tasks) {
            Calendar calendar = Calendar.getInstance();


            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    temp.hour,
                    temp.minute,
                    0
            );
            Intent intent = new Intent(currContext, AlertReceiver.class);
            intent.putExtra("NAME", temp.name);
            final PendingIntent pIntent = PendingIntent.getBroadcast(currContext, temp.getUniqueID(),
                    intent, 0);
            AlarmManager alarm = (AlarmManager) currContext.getSystemService(Context.ALARM_SERVICE);
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }

    }

    public static void completeTask(Task task){
        LambdaInvokerFactory factory = setCognito();
        final MyInterface myInterface = factory.build(MyInterface.class);
        RequestClass request = new RequestClass();
        request.taskId = task.id;
        task.isCompleted=true;
        new AsyncTask<RequestClass, Void, ResponseClass>() {
            @Override
            protected ResponseClass doInBackground(RequestClass... params) {
                try {
                    return myInterface.completeReminder(params[0]);
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
}

package com.example.taskmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class SecondActivity extends AppCompatActivity {

    Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    String personEmail, personName;
    User user;
    ListView taskList;
    int currentPos = 0;
    AlertDialog actions;


    public String[] dayOfWeek = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserWrapper.setContext(this.getApplicationContext());
        setContentView(R.layout.activity_second);
        UserWrapper.setContext(this);
        Calendar calendar = Calendar.getInstance();
        int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        initializeUI();
        googleSignIn();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to complete or delete this task?");
        String[] options = {"COMPLETE", "DELETE"};

        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPos = position;
                actions.show();
            }
        });




//        getFromDatabase();
//        Task[] items = {new Task("1", "feed the cat"), new Task("2", "feed the dog")};
//        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, items);
//        taskList.setAdapter(adapter);


    }
    public void friendMenu(View view){
        Intent intent = new Intent(SecondActivity.this, Friend_Activity.class);
        intent.putExtra("EMAIL", personEmail);
        startActivity(intent);
    }

    public void taskMenu(View view){
        Intent intent = new Intent(SecondActivity.this, Task_Activity.class);
        intent.putExtra("EMAIL", personEmail);
        startActivity(intent);
    }

//    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });
////        Toast.makeText(SecondActivity.this, "Signed out!", Toast.LENGTH_LONG).show();
////        AlertReceiver av = new AlertReceiver();
////        Intent intent = new Intent(this, AlertReceiver.class);
////        intent.putExtra("NAME", "happy");
////        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 1,
////                intent, 0);
////        // Setup periodic alarm every every half hour from this point onwards
////        long firstMillis = System.currentTimeMillis(); // alarm is set right away
////        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
////        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
////        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
////        alarm.setExact(AlarmManager.RTC_WAKEUP, 100000, pIntent);
////        //alarm.setExact(AlarmManager.RTC_WAKEUP, 2000, pIntent);
//    }

    private void signOut() {
        mGoogleSignInClient.signOut();
        finish();

    }

    private void googleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();

        }
    }

    private void initializeUI(){
        taskList = findViewById(R.id.mainTastList);
        signOut = findViewById(R.id.sign_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    // ...
                    case R.id.sign_out_button:
                        signOut();
                        break;
                    // ...
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (UserWrapper.checkUser(this.getApplicationContext(),personEmail)){
//            try {
//                user = UserWrapper.getUser(personEmail);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ArrayList<Task> todayTask = returnTodayTask(user);
//            ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, todayTask);
//            taskList.setAdapter(adapter);
//            setAlarm(todayTask);
            getFromDatabase();
        } else {
            user = new User(personEmail, personName, new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Task>());
            UserWrapper.addUser(this.getApplicationContext(),user);
        }



//        Task[] items = {new Task("1", "feed the cat"), new Task("2", "feed the dog")};
//        ArrayList<Task> newTask = new ArrayList<Task>();
//        newTask.add(new Task("1", "feed the cat"));
//        newTask.add(new Task("2", "feed the dog"));

        //ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, items);
//        if (user.tasks != null) {
//            AdapterTask adapter = new AdapterTask(this, android.R.layout.simple_list_item_1, user.tasks);
//            taskList.setAdapter(adapter);
//        }
    }

    public void getFromDatabase(){
        try {
            user = UserWrapper.getUser(this.getApplicationContext(),personEmail);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(user != null) {
            ArrayList<Task> todayTask = returnTodayTask(user);
            if (todayTask != null) {
                ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, todayTask);
                taskList.setAdapter(adapter);
                setAlarm(todayTask);
            }
        }
    }


    public ArrayList<Task> returnTodayTask(User user){
        Calendar calendar = Calendar.getInstance();
        ArrayList<Task> todayTask = new ArrayList<Task>();
        if(user.tasks != null) {
            for (Task temp : user.tasks) {
                String dayTask = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)];
                if (dayTask.equalsIgnoreCase(temp.nextAlarmDay)) {
                    todayTask.add(temp);
                }
            }
        }
        return todayTask;
    }

    public void setAlarm(ArrayList<Task> tasks){

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
            Intent intent = new Intent(this, AlertReceiver.class);
            intent.putExtra("NAME", temp.name);
            final PendingIntent pIntent = PendingIntent.getBroadcast(this, temp.getUniqueID(),
                    intent, 0);
            AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }

    }

    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // complete
                            UserWrapper.completeTask((Task)taskList.getAdapter().getItem(currentPos), user);
                            getFromDatabase();
                            break;

                        case 1: //delete
                            UserWrapper.deleteTask((Task)taskList.getAdapter().getItem(currentPos), user);
                            getFromDatabase();
                        break;
                        default:
                            break;
                    }
                }
            };

}

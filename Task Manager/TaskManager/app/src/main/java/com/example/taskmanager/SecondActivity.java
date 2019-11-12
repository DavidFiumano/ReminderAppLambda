package com.example.taskmanager;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    TextView name, email;
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;
    String personEmail, personName;
    User user;
    ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initializeUI();
        googleSignIn();


        if (UserWrapper.checkUser(personEmail)){
            user = UserWrapper.getUser(personEmail);
        } else {
            user = new User(personEmail, personName, new ArrayList<User>(), new ArrayList<User>(), new ArrayList<Task>());
        }

        Task[] items = {new Task("1", "feed the cat"), new Task("2", "feed the dog")};
        ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, items);
        taskList.setAdapter(adapter);


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

    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(SecondActivity.this, "Signed out successfully!", Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//                });
        Toast.makeText(SecondActivity.this, "Signed out!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AlertReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 1,
                intent, 0);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setExact(AlarmManager.RTC_WAKEUP, 100000, pIntent);
        //alarm.setExact(AlarmManager.RTC_WAKEUP, 2000, pIntent);
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
            name.setText(personName);
            email.setText(personEmail);
        }
    }

    private void initializeUI(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
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

}

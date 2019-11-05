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
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SecondActivity extends AppCompatActivity {
    TextView name, email;
    Button signOut;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            name.setText(personName);
            email.setText(personEmail);
        }


    }
    public void friendMenu(View view){
        Intent intent = new Intent(SecondActivity.this, Friend_Activity.class);
        startActivity(intent);
    }

    public void taskMenu(View view){
        Intent intent = new Intent(SecondActivity.this, Task_Activity.class);
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

        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setExact(AlarmManager.RTC_WAKEUP, 1000, pIntent);
    }

    public class MyTestService extends IntentService {
        public MyTestService() {
            super("MyTestService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            // Do the task here
            Log.i("MyTestService", "Service running");
        }
    }

    public class MyAlarmReceiver extends BroadcastReceiver {
        public static final int REQUEST_CODE = 12345;
        public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

        // Triggered by the Alarm periodically (starts the service to run task)
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, MyTestService.class);
            i.putExtra("foo", "bar");
            context.startService(i);
        }
    }
}

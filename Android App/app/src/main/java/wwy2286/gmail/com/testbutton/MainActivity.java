package wwy2286.gmail.com.testbutton;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testButton(View v){
        Toast toast = Toast.makeText(getApplicationContext(), "Button works toast", Toast.LENGTH_SHORT);
        System.out.println("button works");
        toast.show();
    }
}

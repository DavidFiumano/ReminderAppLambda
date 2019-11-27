package com.example.taskmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public String[] dayOfWeek = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    //tests to see if custom adapter works
    @Test
    public void checkAdapterTask(){
        ArrayList<Task> task = new ArrayList<Task>();
        task.add(new Task("1", "Feed the cat"));
        task.add(new Task("2", "Feed the dog"));
        AdapterTask adapterTask = new AdapterTask(null,0,task);
        //Task task1 = adapterTask.getItem(0);
        //Task task2 = adapterTask.getItem(1);

    }

    @Test
    public void checkDay(){
        Calendar calendar = Calendar.getInstance();
        String dayTask = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)];
        assertEquals(dayTask, "Monday");

    }

    @Test
    public void returnTodayTask() {

        User user = new User();
        ArrayList<Task> task = new ArrayList<Task>();
        task.add(new Task("1", "Feed the cat", "monday"));
        task.add(new Task("2", "Feed the dog", "tuesday"));
        user.tasks = task;
        Calendar calendar = Calendar.getInstance();
        ArrayList<Task> todayTask = new ArrayList<Task>();
        if(user.tasks != null) {
            for (Task temp : user.tasks) {

                String dayTask = dayOfWeek[calendar.get(Calendar.DAY_OF_WEEK)];
                System.out.println(dayTask);
                if (dayTask.equalsIgnoreCase(temp.nextAlarmDay)) {
                    todayTask.add(temp);
                }
            }
        }

        for (Task temp : todayTask) {
            assertEquals(temp.day, "monday");
        }
    }


}
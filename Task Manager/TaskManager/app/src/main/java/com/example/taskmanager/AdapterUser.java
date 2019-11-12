package com.example.taskmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class AdapterUser extends ArrayAdapter<User> {
    private Activity activity;
    private ArrayList<User> lUser;
    private static LayoutInflater inflater = null;

    public AdapterUser (Activity activity, int textViewResourceId,ArrayList<User> lUser) {
        super(activity, textViewResourceId, lUser);
        try {
            this.activity = activity;
            this.lUser = lUser;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }
}

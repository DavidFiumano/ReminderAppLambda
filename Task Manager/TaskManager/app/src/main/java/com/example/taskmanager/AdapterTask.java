package com.example.taskmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTask extends ArrayAdapter<Task> {
    private Activity activity;
    private ArrayList<Task> lTask;
    private static LayoutInflater inflater = null;

    public AdapterTask(Activity activity, int textViewResourceId,ArrayList<Task> lTask) {
        super(activity, textViewResourceId, lTask);
        try {
            this.activity = activity;
            this.lTask = lTask;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lTask.size();
    }


    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_time;
        public TextView display_complete;


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.task_adapter, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.taskName);
                holder.display_time = (TextView) vi.findViewById((R.id.taskTime));
                holder.display_complete = (TextView) vi.findViewById(R.id.taskComplete);



                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText(lTask.get(position).name);
            holder.display_time.setText(lTask.get(position).hour+":"+lTask.get(position).minute);

            String complete;
            if (lTask.get(position).isCompleted){
                complete = "Completed";
            } else {
                complete = "To Do";
            }
            holder.display_complete.setText(complete);


        } catch (Exception e) {


        }
        return vi;
    }
}

package com.example.bonnana.tusky.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Task;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> tasks;
    private Context context;
    private View.OnClickListener mOnItemClickListener;

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view, parent, false);

        TaskAdapter.TaskViewHolder vh = new TaskAdapter.TaskViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = tasks.get(i);

        TextView title = taskViewHolder.title;
        title.setText(task.getName());
        TextView description = taskViewHolder.description;
        description.setText(task.getDescription());
//        taskViewHolder.title.setText(task.getName());
//        taskViewHolder.description.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;

        public TaskViewHolder(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.task_name);
            description = (TextView) v.findViewById(R.id.task_description);
        }
    }
}

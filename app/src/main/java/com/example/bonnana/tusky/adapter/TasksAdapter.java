package com.example.bonnana.tusky.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Task;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    private ArrayList<Task> tasks;

    public static class TasksViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TasksViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public TasksAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_view, parent, false);

        TasksViewHolder vh = new TasksViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(tasks.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}

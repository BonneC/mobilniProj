package com.example.bonnana.tusky.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Task;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    private ArrayList<Task> tasks;
    private View.OnClickListener mOnItemClickListener;

    public TasksAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_view, parent, false);

        TasksViewHolder vh = new TasksViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(tasks.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    public class TasksViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public TasksViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.task_title);

            v.setTag(this);
            v.setOnClickListener(mOnItemClickListener);
        }
    }
}

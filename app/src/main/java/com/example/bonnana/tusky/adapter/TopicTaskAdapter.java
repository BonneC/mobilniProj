package com.example.bonnana.tusky.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Task;

import java.util.ArrayList;

public class TopicTaskAdapter extends RecyclerView.Adapter<TopicTaskAdapter.TopicTaskViewHolder> {
    private ArrayList<Task> tasks;
    private Context context;
    private View.OnClickListener mOnItemClickListener;

    public TopicTaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TopicTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopicTaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_task_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopicTaskViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getName());
        holder.description.setText(tasks.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TopicTaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public TopicTaskViewHolder(View v) {
            super(v);

            title = (TextView) v.findViewById(R.id.task_title);
            description = (TextView) v.findViewById(R.id.task_description);
        }
    }
}

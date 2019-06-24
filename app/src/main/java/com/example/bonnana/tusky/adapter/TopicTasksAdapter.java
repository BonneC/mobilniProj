package com.example.bonnana.tusky.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.Task;

import java.util.ArrayList;

public class TopicTasksAdapter extends RecyclerView.Adapter<TopicTasksAdapter.TasksViewHolder> {
    private ArrayList<Task> tasks;
    private View.OnClickListener mOnItemClickListener;
    private View.OnClickListener buttonListener;

    public TopicTasksAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TasksViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_tasks_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getName());
        holder.description.setText(tasks.get(position).getDescription());
        holder.btn_add.setTag(position);
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void setOnButtonClickListener(View.OnClickListener buttonClickListener) {
        buttonListener = buttonClickListener;
    }


    public class TasksViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private Button btn_add;

        public TasksViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.task_title);
            description = v.findViewById(R.id.task_description);
            btn_add = v.findViewById(R.id.button_add_task);


            btn_add.setOnClickListener(buttonListener);

            v.setTag(this);
            v.setOnClickListener(mOnItemClickListener);

        }

    }


}

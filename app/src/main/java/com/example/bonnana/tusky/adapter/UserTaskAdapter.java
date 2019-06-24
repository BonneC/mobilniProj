package com.example.bonnana.tusky.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.UserTask;

import java.util.ArrayList;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.UserTaskViewHolder> {
    private ArrayList<UserTask> tasks;
    private View.OnClickListener mOnItemClickListener;
    private View.OnClickListener checkBoxListener;

    public UserTaskAdapter(ArrayList<UserTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public UserTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserTaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_task_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserTaskViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getName());
        Integer tmp = ((UserTask)tasks.get(position)).isCompleted();
        holder.completed.setText(tmp.toString());
        if(tmp==1)
            holder.chbox_update.setChecked(true);
        else
            holder.chbox_update.setChecked(false);
        holder.chbox_update.setTag(position);
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void setOnButtonClickListener(View.OnClickListener checkBoxListener) {
        this.checkBoxListener = checkBoxListener;
    }


    public class UserTaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView completed;
        private CheckBox chbox_update;

        public UserTaskViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.task_title);
            completed = v.findViewById(R.id.task_completed);
            chbox_update = v.findViewById(R.id.chbox_check_task);


            //btn_add.setOnClickListener(buttonListener);

            v.setTag(this);
            v.setOnClickListener(mOnItemClickListener);
            chbox_update.setOnClickListener(checkBoxListener);

        }

        public boolean isChecked(){
            return chbox_update.isChecked();
        }

    }


}

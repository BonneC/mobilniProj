package com.example.bonnana.tusky.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bonnana.tusky.R;
import com.example.bonnana.tusky.model.UserTask;

import java.util.ArrayList;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.UserTaskViewHolder> {
    private ArrayList<UserTask> tasks;
    private View.OnClickListener mOnItemClickListener;
    private View.OnClickListener btnListener;

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
        holder.description.setText(tasks.get(position).getDescription());
        Integer tmp = ((UserTask)tasks.get(position)).isCompleted();

        if(tmp==1)
        {
            holder.btn_check_task.setBackgroundResource(R.drawable.icon_checked);
            holder.btn_check_task.setContentDescription("1");
        }
            //holder.chbox_update.setChecked(true);

        else{
            holder.btn_check_task.setBackgroundResource(R.drawable.icon_unchecked);
            holder.btn_check_task.setContentDescription("0");
        }

            //holder.chbox_update.setChecked(false);
        holder.btn_check_task.setTag(position);
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void setOnButtonClickListener(View.OnClickListener btnListener) {
        this.btnListener = btnListener;
    }


    public class UserTaskViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageButton btn_check_task;
        private boolean completed;

        public UserTaskViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.task_title);
            //chbox_update = v.findViewById(R.id.chbox_check_task);
            btn_check_task = v.findViewById(R.id.btn_check_task);
            description = v.findViewById(R.id.task_description);


            //btn_add.setOnClickListener(buttonListener);

            v.setTag(this);
            v.setOnClickListener(mOnItemClickListener);
            btn_check_task.setOnClickListener(btnListener);


        }

        public boolean isCompleted(){
            return completed;
        }

    }


}

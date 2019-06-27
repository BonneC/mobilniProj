package com.example.bonnana.tusky;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TopicTasksAdapter;
import com.example.bonnana.tusky.adapter.UserTasksAdapter;
import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.model.UserTask;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TaskServices;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTasksActivity extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TaskList<Task> taskList = new TaskList<>();

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Task task = (Task) taskList.getTaskArrayList().get(position);
            String id = task.getId();

            Toast.makeText(UserTasksActivity.this.getContext(), "SUKSES", Toast.LENGTH_LONG).show();

            callExplicitIntent(id);
        }
    };

    private View.OnLongClickListener onItemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Task task = (Task) taskList.getTaskArrayList().get(position);
            String id = task.getId();

            AlertDialog alert = new AlertDialog.Builder(UserTasksActivity.this.getContext())
                    .setTitle("Delete task?")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTask(Integer.parseInt(id));
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


            Toast.makeText(UserTasksActivity.this.getContext(), id, Toast.LENGTH_LONG).show();

            return true;

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        getTasks();

        return v;
    }

    public void getTasks() {
        TaskServices service = RetrofitInstance.getRetrofitInstance(UserTasksActivity.this.getContext()).create(TaskServices.class);

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdHVza3kuYXRhbmFzay5ta1wvYXV0aFwvbG9naW4iLCJpYXQiOjE1NjA1NTMwMzUsIm5iZiI6MTU2MDU1MzAzNiwianRpIjoiN3EzMXQ1b3JlRzdtYkJLViIsInN1YiI6MSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.AtK9Hq9OOdnxIlxe9tUvCCJ1wAWNfwUwB4AvcUwJZ8A";
        Call<TaskList<Task>> call = service.getTasksData(1);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<TaskList<Task>>() {
            @Override
            public void onResponse(Call<TaskList<Task>> call, Response<TaskList<Task>> response) {
//                String resp = response.body().toString();

                ArrayList<Task> apiTaskList = response.body().getTaskArrayList();
                taskList.setTaskArrayList(apiTaskList);

                mAdapter = new UserTasksAdapter(taskList.getTaskArrayList());
                recyclerView.setAdapter(mAdapter);
                ((UserTasksAdapter) mAdapter).setOnItemClickListener(onItemClickListener);
                ((UserTasksAdapter) mAdapter).setOnItemLongClickListener(onItemLongClickListener);
            }

            @Override
            public void onFailure(Call<TaskList<Task>> call, Throwable t) {
                Toast.makeText(UserTasksActivity.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteTask(int taskId) {
        TaskServices service = RetrofitInstance.getRetrofitInstance(UserTasksActivity.this.getContext()).create(TaskServices.class);

        Call<ResponseBody> call = service.deleteTask(1, taskId);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String resp = response.body().toString();

                //ArrayList<Task> apiTaskList = response.body().getTaskArrayList();
                //taskList.setTaskArrayList(apiTaskList);
                Toast.makeText(UserTasksActivity.this.getContext(), "SUKSES", Toast.LENGTH_LONG).show();
                getTasks();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UserTasksActivity.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callExplicitIntent(String id) {
        Intent intent = new Intent(this.getContext(), TaskActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }


    public void checkTask(View view) {
        int pos = (Integer) view.getTag();

        Task task = (Task) taskList.getTaskArrayList().get(pos);
        String id = task.getId();

        Toast.makeText(UserTasksActivity.this.getContext(), id, Toast.LENGTH_LONG).show();
    }
}

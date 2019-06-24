package com.example.bonnana.tusky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TopicTaskAdapter;
import com.example.bonnana.tusky.adapter.UserTaskAdapter;
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

public class TaskActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TaskServices service;
    private TaskList<UserTask> taskList = new TaskList<>();
    private int messageText;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (Integer) v.getTag();

            UserTask task = taskList.getTaskArrayList().get(pos);
            int id = Integer.parseInt(task.getId());


            CheckBox checkBox = (CheckBox) v.findViewById(R.id.chbox_check_task);
            boolean completed;

            if (checkBox.isChecked()) {
                //Toast.makeText(TaskActivity.this, "CHECKED", Toast.LENGTH_LONG).show();
                completed = true;
            } else
                completed = false;

            String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdHVza3kuYXRhbmFzay5ta1wvYXV0aFwvbG9naW4iLCJpYXQiOjE1NjA1NTMwMzUsIm5iZiI6MTU2MDU1MzAzNiwianRpIjoiN3EzMXQ1b3JlRzdtYkJLViIsInN1YiI6MSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.AtK9Hq9OOdnxIlxe9tUvCCJ1wAWNfwUwB4AvcUwJZ8A";
            Call<ResponseBody> call = service.updateTask(token, 1, id, completed);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(TaskActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TaskActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tasks);
        Intent intent = getIntent();
        int messageText = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

        recyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        service = RetrofitInstance.getRetrofitInstance().create(TaskServices.class);
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdHVza3kuYXRhbmFzay5ta1wvYXV0aFwvbG9naW4iLCJpYXQiOjE1NjA1NTMwMzUsIm5iZiI6MTU2MDU1MzAzNiwianRpIjoiN3EzMXQ1b3JlRzdtYkJLViIsInN1YiI6MSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.AtK9Hq9OOdnxIlxe9tUvCCJ1wAWNfwUwB4AvcUwJZ8A";
        Call<TaskList<UserTask>> call = service.getTaskForUser(token, 1,messageText);

        call.enqueue(new Callback<TaskList<UserTask>>() {
            @Override
            public void onResponse(Call<TaskList<UserTask>> call, Response<TaskList<UserTask>> response) {
                ArrayList<UserTask> apiTaskList = response.body().getTaskArrayList();
                taskList.setTaskArrayList(apiTaskList);
                //taskList.setTaskArrayList(response.body().getTaskArrayList());
                Log.wtf("RESHPONS", response.body().getTaskArrayList().get(0).isCompleted() + "");

                mAdapter = new UserTaskAdapter(taskList.getTaskArrayList());
                recyclerView.setAdapter(mAdapter);
                ((UserTaskAdapter) mAdapter).setOnButtonClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<TaskList<UserTask>> call, Throwable t) {
                Toast.makeText(TaskActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

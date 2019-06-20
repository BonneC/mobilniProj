package com.example.bonnana.tusky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TaskAdapter;
import com.example.bonnana.tusky.adapter.TasksAdapter;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TaskServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tasks);
        Intent intent = getIntent();
        int messageText =  Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

        recyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TaskServices service = RetrofitInstance.getRetrofitInstance().create(TaskServices.class);
        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdHVza3kuYXRhbmFzay5ta1wvYXV0aFwvbG9naW4iLCJpYXQiOjE1NjA1NTMwMzUsIm5iZiI6MTU2MDU1MzAzNiwianRpIjoiN3EzMXQ1b3JlRzdtYkJLViIsInN1YiI6MSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.AtK9Hq9OOdnxIlxe9tUvCCJ1wAWNfwUwB4AvcUwJZ8A";
        Call<TaskList> call = service.getTaskData(token, messageText);

        call.enqueue(new Callback<TaskList>() {
            @Override
            public void onResponse(Call<TaskList> call, Response<TaskList> response) {
                TaskList taskList = new TaskList();
                taskList.setTaskArrayList(response.body().getTaskArrayList());

                mAdapter = new TaskAdapter(taskList.getTaskArrayList());
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<TaskList> call, Throwable t) {
                Toast.makeText(TaskActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
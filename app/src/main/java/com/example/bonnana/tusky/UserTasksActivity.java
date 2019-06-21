package com.example.bonnana.tusky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TasksAdapter;
import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TaskServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TaskList taskList = new TaskList();
    private int messageText;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Task task = taskList.getTaskArrayList().get(position);
            String id = task.getId();

            Toast.makeText(UserTasksActivity.this, "SUKSES", Toast.LENGTH_LONG).show();

            callExplicitIntent(id);
        }
    };

    public void callExplicitIntent(String id) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        messageText =  Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getTasks();


    }

    public void getTasks(){
        TaskServices service = RetrofitInstance.getRetrofitInstance().create(TaskServices.class);

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdHVza3kuYXRhbmFzay5ta1wvYXV0aFwvbG9naW4iLCJpYXQiOjE1NjA1NTMwMzUsIm5iZiI6MTU2MDU1MzAzNiwianRpIjoiN3EzMXQ1b3JlRzdtYkJLViIsInN1YiI6MSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.AtK9Hq9OOdnxIlxe9tUvCCJ1wAWNfwUwB4AvcUwJZ8A";
        Call<TaskList> call = service.getTasksData(token, messageText);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<TaskList>() {
            @Override
            public void onResponse(Call<TaskList> call, Response<TaskList> response) {
//                String resp = response.body().toString();


                taskList.setTaskArrayList(response.body().getTaskArrayList());

                mAdapter = new TasksAdapter(taskList.getTaskArrayList());
                recyclerView.setAdapter(mAdapter);
                ((TasksAdapter) mAdapter).setOnItemClickListener(onItemClickListener);
            }

            @Override
            public void onFailure(Call<TaskList> call, Throwable t) {
                Toast.makeText(UserTasksActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkTask(View view) {
        int pos = (Integer) view.getTag();

        Task task = taskList.getTaskArrayList().get(pos);
        String id = task.getId();

        Toast.makeText(UserTasksActivity.this, id, Toast.LENGTH_LONG).show();
    }
}

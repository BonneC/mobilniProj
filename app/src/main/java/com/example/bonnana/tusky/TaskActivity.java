package com.example.bonnana.tusky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TopicTaskAdapter;
import com.example.bonnana.tusky.adapter.UserTaskAdapter;
import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.model.UserTask;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TaskServices;
import com.example.bonnana.tusky.services.jsonClasses.UpdatedTask;

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


//            CheckBox checkBox = (CheckBox) v.findViewById(R.id.chbox_check_task);
            boolean completed;

            ImageButton imgButton = (ImageButton) v.findViewById(R.id.btn_check_task);

            String tmp = (String) imgButton.getContentDescription();
            Toast.makeText(TaskActivity.this, tmp, Toast.LENGTH_LONG).show();

            if (task.isCompleted() == 1) {
                completed = false;
                task.updateTaskStatus(0);
                //imgButton.setBackgroundResource(R.drawable.icon_unchecked);
            } else{
                completed = true;
                task.updateTaskStatus(1);
                //imgButton.setBackgroundResource(R.drawable.icon_checked);
            }


            UpdatedTask updated = new UpdatedTask(completed);

            Call<ResponseBody> call = service.updateTask(1, id, updated);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(TaskActivity.this, "Task Completed", Toast.LENGTH_LONG).show();
                    mAdapter.notifyDataSetChanged();
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

        service = RetrofitInstance.getRetrofitInstance(TaskActivity.this).create(TaskServices.class);

        Call<TaskList<UserTask>> call = service.getTaskForUser(1, messageText);

        call.enqueue(new Callback<TaskList<UserTask>>() {
            @Override
            public void onResponse(Call<TaskList<UserTask>> call, Response<TaskList<UserTask>> response) {
                ArrayList<UserTask> apiTaskList = response.body().getTaskArrayList();
                taskList.setTaskArrayList(apiTaskList);
                //taskList.setTaskArrayList(response.body().getTaskArrayList());
                //Log.wtf("RESHPONS", response.body().getTaskArrayList().get(0).getName());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPref.edit().remove("idToken").apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(TaskActivity.this, "Logout", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

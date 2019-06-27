package com.example.bonnana.tusky;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.TopicTasksAdapter;
import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TaskServices;
import com.example.bonnana.tusky.services.TopicServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicTasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TaskList<Task> taskList = new TaskList<>();
    private TopicServices service;
    private int messageText;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Task task = (Task) taskList.getTaskArrayList().get(position);
            String id = task.getId();

            Toast.makeText(TopicTasksActivity.this, id, Toast.LENGTH_LONG).show();

            callExplicitIntent(id);
        }
    };

    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int pos = (Integer) v.getTag();

            Task task = (Task) taskList.getTaskArrayList().get(pos);
            int id = Integer.parseInt(task.getId());

            Toast.makeText(TopicTasksActivity.this, task.getId(), Toast.LENGTH_LONG).show();

            TaskServices service = RetrofitInstance.getRetrofitInstance(TopicTasksActivity.this).create(TaskServices.class);


            Call<ResponseBody> call = service.addTask(1, id);

            Log.wtf("URL Called", call.request().url() + "");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    String resp = response.body().toString();
                    Toast.makeText(TopicTasksActivity.this, "Added task", Toast.LENGTH_LONG).show();
                    getTasks();

//                    FragmentManager fm = getFragmentManager();
//                    UserTasksActivity fragm = (UserTasksActivity) fm.findFragmentByTag("Tasks");
//                    fragm.otherList();

                    UserTasksActivity.getInstance().getTasks();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TopicTasksActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

//            callExplicitIntent(id);
        }
    };



    public void callExplicitIntent(String id) {
        Intent intent = new Intent(this, TopicTaskActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        messageText = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        service = RetrofitInstance.getRetrofitInstance(TopicTasksActivity.this).create(TopicServices.class);

        getTasks();
    }

    private void getTasks() {
        Call<TaskList<Task>> call = service.getTasksData(messageText);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<TaskList<Task>>() {
            @Override
            public void onResponse(Call<TaskList<Task>> call, Response<TaskList<Task>> response) {
//                String resp = response.body().toString();

                taskList.setTaskArrayList(response.body().getTaskArrayList());

                mAdapter = new TopicTasksAdapter(taskList.getTaskArrayList());
                recyclerView.setAdapter(mAdapter);
                ((TopicTasksAdapter) mAdapter).setOnItemClickListener(onItemClickListener);
                ((TopicTasksAdapter) mAdapter).setOnButtonClickListener(onButtonClickListener);
            }

            @Override
            public void onFailure(Call<TaskList<Task>> call, Throwable t) {
                Toast.makeText(TopicTasksActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

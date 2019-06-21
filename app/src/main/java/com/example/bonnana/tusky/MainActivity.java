package com.example.bonnana.tusky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.CardViewAdapter;
import com.example.bonnana.tusky.model.Topic;
import com.example.bonnana.tusky.model.TopicList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TopicServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TopicList topicList = new TopicList();
    private Toolbar toolbar;

//    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
//            int position = viewHolder.getAdapterPosition();
//
//            Topic topic = topicList.getTopicArrayList().get(position);
//            String id = topic.getId();
//
//            callExplicitIntent(id);
//        }
//    };
//
//    public void callExplicitIntent(String id) {
//        Intent intent = new Intent(this, TopicTasksActivity.class);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, id);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        layoutManager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(layoutManager);

        toolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getTopics();
    }

    public void getTopics(){
        Intent intent = new Intent(this, TopicsActivity.class);

        startActivity(intent);
    }

    public void getTasks(View view) {
        Intent intent = new Intent(this, UserTasksActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "1");
        startActivity(intent);
    }

    public void getTopics(View view) {
        getTopics();
    }
}

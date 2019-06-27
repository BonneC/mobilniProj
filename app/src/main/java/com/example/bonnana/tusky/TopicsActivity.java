package com.example.bonnana.tusky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.CardViewAdapter;
import com.example.bonnana.tusky.model.Topic;
import com.example.bonnana.tusky.model.TopicList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TopicServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicsActivity extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TopicList topicList = new TopicList();

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Topic topic = topicList.getTopicArrayList().get(position);
            String id = topic.getId();

            callExplicitIntent(id);
        }
    };

    public void callExplicitIntent(String id) {
        Intent intent = new Intent(this.getContext(), TopicTasksActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);

        getTopics();

        return v;
    }

    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Intent intent = getIntent();
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//
//        layoutManager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(layoutManager);
//
//        getTopics();
//    }

    private void getTopics() {
        TopicServices service = RetrofitInstance.getRetrofitInstance(TopicsActivity.this.getContext()).create(TopicServices.class);

        Call<TopicList> call = service.getTopicsData();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<TopicList>() {
                         @Override
                         public void onResponse(Call<TopicList> call, Response<TopicList> response) {
                             String resp = response.body().getTopicArrayList().get(0).getTitle();
//                             TextView messageView = findViewById(R.id.message);
//                             messageView.setText(resp);


                             topicList.setTopicArrayList(response.body().getTopicArrayList());

                             mAdapter = new CardViewAdapter(topicList.getTopicArrayList());
                             recyclerView.setAdapter(mAdapter);
                             ((CardViewAdapter) mAdapter).setOnItemClickListener(onItemClickListener);

                         }


                         @Override
                         public void onFailure(Call<TopicList> call, Throwable t) {
                             Toast.makeText(TopicsActivity.this.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );

    }
}

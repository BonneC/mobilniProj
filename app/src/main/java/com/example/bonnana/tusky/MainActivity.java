package com.example.bonnana.tusky;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bonnana.tusky.model.TopicList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TopicServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TopicServices service = RetrofitInstance.getRetrofitInstance().create(TopicServices.class);

        Call<TopicList> call = service.getTopicsData();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<TopicList>() {
                         @Override
                         public void onResponse(Call<TopicList> call, Response<TopicList> response) {
                             String resp = response.body().getTopicArrayList().get(0).getTitle();
                             TextView messageView = findViewById(R.id.message);
                             messageView.setText(resp);
                         }


                         @Override
                         public void onFailure(Call<TopicList> call, Throwable t) {
                             Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );

    }
}

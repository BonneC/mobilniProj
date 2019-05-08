package com.example.bonnana.tusky.services;

import com.example.bonnana.tusky.model.TopicList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopicServices {

    @GET("/topics")
    Call<TopicList> getTopicsData();
}

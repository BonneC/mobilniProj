package com.example.bonnana.tusky.services;

import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.model.TopicList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TopicServices {

    @GET("/topics")
    Call<TopicList> getTopicsData();

    @GET("/topic/{id}/tasks")
    Call<TaskList<Task>> getTasksData(@Header("Authorization")String token, @Path("id")int topicId);
}

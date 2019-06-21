package com.example.bonnana.tusky.services;

import com.example.bonnana.tusky.model.TaskList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TaskServices {

    @GET("/task/{id}/")
    Call<TaskList> getTaskData(@Header("Authorization") String token, @Path("id") int taskId);

    @GET("user/{id}/tasks")
    Call<TaskList> getTasksData(@Header("Authorization") String token, @Path("id") int userId);
}

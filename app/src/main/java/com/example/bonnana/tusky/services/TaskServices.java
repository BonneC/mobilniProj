package com.example.bonnana.tusky.services;

import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;
import com.example.bonnana.tusky.model.UserTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskServices {

    @GET("/task/{id}/")
    Call<TaskList<UserTask>> getTaskData(@Header("Authorization") String token, @Path("id") int taskId);

    @GET("user/{id}/tasks")
    Call<TaskList<UserTask>> getTasksData(@Header("Authorization") String token, @Path("id") int userId);

    @GET("user/{id}/tasks/{task_id}")
    Call<TaskList<UserTask>> getTaskForUser(
            @Header("Authorization") String token,
            @Path("id") int userId,
            @Path("task_id") int taskId
    );

    @PUT("user/{id}/tasks/{task_id}")
    Call<ResponseBody> updateTask(
            @Header("Authorization") String token,
            @Path("id") int userId,
            @Path("task_id") int task_id,
            @Field("completed") boolean completed
    );
}

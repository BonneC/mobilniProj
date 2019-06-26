package com.example.bonnana.tusky.services;

import com.example.bonnana.tusky.model.Login;
import com.example.bonnana.tusky.model.Token;
import com.example.bonnana.tusky.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserServices {
    @POST("/auth/login")
    Call<Token> login(@Body Login login);

    @POST("/auth/me")
    Call<User> getUser(@Header("Authorization") String token);
}

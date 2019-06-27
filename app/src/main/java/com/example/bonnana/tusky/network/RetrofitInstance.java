package com.example.bonnana.tusky.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://tusky.atanask.mk";

    public static Retrofit getRetrofitInstance(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token = prefs.getString("idToken", "error");
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

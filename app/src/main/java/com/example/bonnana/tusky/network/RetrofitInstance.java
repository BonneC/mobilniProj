package com.example.bonnana.tusky.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bonnana.tusky.MainActivity;
import com.example.bonnana.tusky.model.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://tusky.atanask.mk";
    private static SharedPreferences sharedPref;
    private static String token;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            token = prefs.getString("idToken", "error");

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }
            }).build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

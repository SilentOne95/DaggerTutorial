package com.example.android.daggertutorial;

import android.app.Application;
import android.support.annotation.UiThread;

import com.example.android.daggertutorial.Networking.StackOverflowApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication  extends Application {

    private Retrofit mRetrofit;
    private StackOverflowApi mStackOverflowApi;

    @UiThread
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    @UiThread
    public StackOverflowApi getStackOverflowApi() {
        if (mStackOverflowApi == null) {
            mStackOverflowApi = getRetrofit().create(StackOverflowApi.class);
        }

        return mStackOverflowApi;
    }
}
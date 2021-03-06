package com.example.android.daggertutorial.common.dependencyinjection.application;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.networking.StackOverflowApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    @Singleton
    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    StackOverflowApi getStackoverflowApi(Retrofit retrofit) {
        return retrofit.create(StackOverflowApi.class);
    }
}

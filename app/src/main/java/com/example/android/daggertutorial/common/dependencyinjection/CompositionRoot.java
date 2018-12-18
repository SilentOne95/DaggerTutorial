package com.example.android.daggertutorial.common.dependencyinjection;

import android.support.annotation.UiThread;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.networking.StackOverflowApi;
import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@UiThread
public class CompositionRoot {

    private Retrofit mRetrofit;
    private StackOverflowApi mStackOverflowApi;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private StackOverflowApi getStackoverflowApi() {
        if (mStackOverflowApi == null) {
            mStackOverflowApi = getRetrofit().create(StackOverflowApi.class);
        }
        return mStackOverflowApi;
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return new FetchQuestionsListUseCase(getStackoverflowApi());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackoverflowApi());
    }
}
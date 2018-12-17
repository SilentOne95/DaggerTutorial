package com.example.android.daggertutorial;

import android.support.annotation.UiThread;

import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.Screens.FetchQuestions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.Screens.FetchQuestions.FetchQuestionsListUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    private StackOverflowApi getStackOverflowApi() {
        if (mStackOverflowApi == null) {
            mStackOverflowApi = getRetrofit().create(StackOverflowApi.class);
        }

        return mStackOverflowApi;
    }

    @UiThread
    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return new FetchQuestionsListUseCase(getStackOverflowApi());
    }

    @UiThread
    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackOverflowApi());
    }
}
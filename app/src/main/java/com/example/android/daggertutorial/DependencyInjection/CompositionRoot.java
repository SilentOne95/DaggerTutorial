package com.example.android.daggertutorial.DependencyInjection;

import android.support.annotation.UiThread;
import android.support.v4.app.FragmentManager;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.Questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.Questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.Screens.Dialogs.DialogsManager;

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

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return new FetchQuestionsListUseCase(getStackOverflowApi());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackOverflowApi());
    }

    public DialogsManager getDialogsManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }
}
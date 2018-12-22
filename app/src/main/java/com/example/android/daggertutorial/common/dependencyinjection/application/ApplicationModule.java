package com.example.android.daggertutorial.common.dependencyinjection.application;

import android.app.Application;

import com.example.android.daggertutorial.networking.StackOverflowApi;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) { mApplication = application; }

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase(StackOverflowApi stackOverflowApi) {
        return new FetchQuestionsListUseCase(stackOverflowApi);
    }
}
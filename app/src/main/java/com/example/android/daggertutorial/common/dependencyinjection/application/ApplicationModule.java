package com.example.android.daggertutorial.common.dependencyinjection.application;

import com.example.android.daggertutorial.networking.StackOverflowApi;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase(StackOverflowApi stackOverflowApi) {
        return new FetchQuestionsListUseCase(stackOverflowApi);
    }
}
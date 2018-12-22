package com.example.android.daggertutorial.common.dependencyinjection.application;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    FetchQuestionsListUseCase getFetchQuestionsListUseCase();
    FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase();
}
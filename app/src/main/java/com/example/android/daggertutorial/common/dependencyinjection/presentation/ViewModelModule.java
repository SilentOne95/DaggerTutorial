package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.viewmodel.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelFactory viewModelFactory(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        return new ViewModelFactory(fetchQuestionDetailsUseCase);
    }
}

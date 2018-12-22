package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;

import dagger.Component;

@Component(modules = PresentationModule.class)
public interface PresentationComponent {
    DialogsManager getDialogsManager();
    ViewMvcFactory getViewMvcFactory();
    FetchQuestionsListUseCase getFetchQuestionsListUseCase();
    FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase();
}

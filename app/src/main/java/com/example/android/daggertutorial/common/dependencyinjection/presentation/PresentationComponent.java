package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import com.example.android.daggertutorial.common.dependencyinjection.PresentationScope;
import com.example.android.daggertutorial.common.dependencyinjection.application.ApplicationComponent;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.screens.questionslist.QuestionsListActivity;

import dagger.Component;

@PresentationScope
@Component(dependencies = ApplicationComponent.class, modules = PresentationModule.class)
public interface PresentationComponent {
    void inject(QuestionsListActivity questionsListActivity);
    void inject(QuestionDetailsActivity questionDetailsActivity);
}

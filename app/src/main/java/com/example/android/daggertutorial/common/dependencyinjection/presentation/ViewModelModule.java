package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import android.arch.lifecycle.ViewModel;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsViewModel;
import com.example.android.daggertutorial.screens.questionslist.QuestionsListViewModel;
import com.example.android.daggertutorial.screens.viewmodel.ViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(QuestionDetailsViewModel.class)
    ViewModel questionDetailsViewModel(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        return new QuestionDetailsViewModel(fetchQuestionDetailsUseCase);
    }

    @Provides
    @IntoMap
    @ViewModelKey(QuestionsListViewModel.class)
    ViewModel questionsListViewModel() {
        return new QuestionsListViewModel();
    }
}

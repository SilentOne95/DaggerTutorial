package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsViewModel;
import com.example.android.daggertutorial.screens.questionslist.QuestionsListViewModel;
import com.example.android.daggertutorial.screens.viewmodel.ViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    ViewModelFactory viewModelFactory(QuestionDetailsViewModel questionDetailsViewModel,
                                      QuestionsListViewModel questionsListViewModel) {
        return new ViewModelFactory(questionDetailsViewModel, questionsListViewModel);
    }

    @Provides
    QuestionDetailsViewModel questionDetailsViewModel(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        return new QuestionDetailsViewModel(fetchQuestionDetailsUseCase);
    }

    @Provides
    QuestionsListViewModel questionsListViewModel(){
        return new QuestionsListViewModel();
    }
}

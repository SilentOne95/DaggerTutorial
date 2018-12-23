package com.example.android.daggertutorial.screens.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsViewModel;
import com.example.android.daggertutorial.screens.questionslist.QuestionsListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;

    public ViewModelFactory(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        mFetchQuestionDetailsUseCase = fetchQuestionDetailsUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel;

        if (modelClass == QuestionDetailsViewModel.class) {
            viewModel = new QuestionDetailsViewModel(mFetchQuestionDetailsUseCase);
        } else if(modelClass == QuestionsListViewModel.class) {
            viewModel = new QuestionsListViewModel();
        } else {
            throw new RuntimeException("invalid view model class: " + modelClass);
        }

        return (T) viewModel;
    }
}

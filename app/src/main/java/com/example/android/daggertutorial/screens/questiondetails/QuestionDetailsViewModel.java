package com.example.android.daggertutorial.screens.questiondetails;

import android.arch.lifecycle.ViewModel;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.QuestionDetails;

import java.util.HashSet;
import java.util.Set;

public class QuestionDetailsViewModel extends ViewModel implements FetchQuestionDetailsUseCase.Listener {

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);
        void onQuestionDetailsFetchFailed();
    }

    private final FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    private Set<Listener> mListeners = new HashSet<>();
    private QuestionDetails mQuestionDetails;

    public QuestionDetailsViewModel(FetchQuestionDetailsUseCase fetchQuestionDetailsUseCase) {
        mFetchQuestionDetailsUseCase = fetchQuestionDetailsUseCase;
        mFetchQuestionDetailsUseCase.registerListener(this);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mFetchQuestionDetailsUseCase.unregisterListener(this);
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {
        if (mQuestionDetails == null) {
            mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(questionId);
        } else {
            notifySuccess(mQuestionDetails);
        }
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionDetails question) {
        mQuestionDetails = question;
        notifySuccess(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        notifyFailure();
    }

    private void notifyFailure() {
        for (Listener listener : mListeners) {
            listener.onQuestionDetailsFetchFailed();
        }
    }

    private void notifySuccess(QuestionDetails questionDetails) {
        for (Listener listener : mListeners) {
            listener.onQuestionDetailsFetched(questionDetails);
        }
    }


    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }
}

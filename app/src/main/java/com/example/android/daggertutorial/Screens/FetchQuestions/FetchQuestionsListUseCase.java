package com.example.android.daggertutorial.Screens.FetchQuestions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.daggertutorial.Networking.QuestionsListResponseSchema;
import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.Screens.BaseObservable;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionsListUseCase extends BaseObservable<FetchQuestionsListUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionsSucceeded(List<Question> questions);
        void onFetchOfQuestionsFailed();
    }

    private final StackOverflowApi mStackOverflowApi;

    @Nullable
    private
    Call<QuestionsListResponseSchema> mCall;

    public FetchQuestionsListUseCase(StackOverflowApi stackOverflowApi) {

        mStackOverflowApi = stackOverflowApi;
    }

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions) {

        cancelCurrentFetchIfActive();

        mCall = mStackOverflowApi.lastActiveQuestions(numOfQuestions);
        mCall.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsListResponseSchema> call,
                                   @NonNull Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notifySucceeded(response.body().getQuestions());
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuestionsListResponseSchema> call, @NonNull Throwable t) {
                notifyFailed();
            }
        });
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(List<Question> questions) {
        List<Question> unmodifiableQuestions = Collections.unmodifiableList(questions);
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionsSucceeded(unmodifiableQuestions);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionsFailed();
        }
    }
}
package com.example.android.daggertutorial.questions;

import android.support.annotation.Nullable;

import com.example.android.daggertutorial.networking.QuestionSchema;
import com.example.android.daggertutorial.networking.SingleQuestionResponseSchema;
import com.example.android.daggertutorial.networking.StackOverflowApi;
import com.example.android.daggertutorial.common.BaseObservable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onFetchOfQuestionDetailsSucceeded(QuestionDetails question);
        void onFetchOfQuestionDetailsFailed();
    }

    private final StackOverflowApi mStackOverflowApi;

    @Nullable Call<SingleQuestionResponseSchema> mCall;

    public FetchQuestionDetailsUseCase(StackOverflowApi stackoverflowApi) {
        mStackOverflowApi = stackoverflowApi;
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {

        cancelCurrentFetchIfActive();

        mCall = mStackOverflowApi.questionDetails(questionId);
        mCall.enqueue(new Callback<SingleQuestionResponseSchema>() {
            @Override
            public void onResponse(Call<SingleQuestionResponseSchema> call, Response<SingleQuestionResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(questionDetailsFromQuestionSchema(response.body().getQuestion()));
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<SingleQuestionResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private QuestionDetails questionDetailsFromQuestionSchema(QuestionSchema question) {
        return new QuestionDetails(
                question.getId(),
                question.getTitle(),
                question.getBody(),
                question.getOwner().getUserDisplayName(),
                question.getOwner().getUserAvatarUrl()
        );
    }

    private void cancelCurrentFetchIfActive() {
        if (mCall != null && !mCall.isCanceled() && !mCall.isExecuted()) {
            mCall.cancel();
        }
    }

    private void notifySucceeded(QuestionDetails question) {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsSucceeded(question);
        }
    }

    private void notifyFailed() {
        for (Listener listener : getListeners()) {
            listener.onFetchOfQuestionDetailsFailed();
        }
    }
}
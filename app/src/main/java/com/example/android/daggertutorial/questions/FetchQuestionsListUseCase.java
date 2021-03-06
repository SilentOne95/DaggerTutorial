package com.example.android.daggertutorial.questions;

import android.support.annotation.Nullable;

import com.example.android.daggertutorial.networking.QuestionSchema;
import com.example.android.daggertutorial.networking.QuestionsListResponseSchema;
import com.example.android.daggertutorial.networking.StackOverflowApi;
import com.example.android.daggertutorial.common.BaseObservable;

import java.util.ArrayList;
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

    @Nullable Call<QuestionsListResponseSchema> mCall;

    public FetchQuestionsListUseCase(StackOverflowApi stackoverflowApi) {
        mStackOverflowApi = stackoverflowApi;
    }

    public void fetchLastActiveQuestionsAndNotify(int numOfQuestions) {

        cancelCurrentFetchIfActive();

        mCall = mStackOverflowApi.lastActiveQuestions(numOfQuestions);
        mCall.enqueue(new Callback<QuestionsListResponseSchema>() {
            @Override
            public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                if (response.isSuccessful()) {
                    notifySucceeded(questionsFromQuestionsSchemas(response.body().getQuestions()));
                } else {
                    notifyFailed();
                }
            }

            @Override
            public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                notifyFailed();
            }
        });
    }

    private List<Question> questionsFromQuestionsSchemas(List<QuestionSchema> questionSchemas) {
        List<Question> questions = new ArrayList<>(questionSchemas.size());
        for (QuestionSchema questionSchema : questionSchemas) {
            questions.add(new Question(questionSchema.getId(), questionSchema.getTitle()));
        }
        return questions;
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
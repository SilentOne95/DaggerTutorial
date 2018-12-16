package com.example.android.daggertutorial.Screens.QuestionsList;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.Networking.QuestionsListResponseSchema;
import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.Screens.QuestionDetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.Screens.ServerErrorDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity implements
        Callback<QuestionsListResponseSchema>, QuestionsListViewMVC.Listener {

    private StackOverflowApi mStackOverflowApi;
    private Call<QuestionsListResponseSchema> mCall;
    private QuestionsListViewMVC mViewMVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());

        // init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackOverflowApi = retrofit.create(StackOverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mCall = mStackOverflowApi.lastActiveQuestions(20);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(@NonNull Call<QuestionsListResponseSchema> call,
                           @NonNull Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if (response.isSuccessful() && (responseSchema = response.body()) != null) {
            mViewMVC.bindQuestions(responseSchema.getQuestions());
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(@NonNull Call<QuestionsListResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
    }
}
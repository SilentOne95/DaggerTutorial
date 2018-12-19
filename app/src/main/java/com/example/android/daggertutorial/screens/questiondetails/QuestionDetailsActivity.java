package com.example.android.daggertutorial.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.daggertutorial.common.dependencyinjection.Service;
import com.example.android.daggertutorial.questions.QuestionDetails;
import com.example.android.daggertutorial.screens.activities.BaseActivity;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.dialogs.ServerErrorDialogFragment;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;

public class QuestionDetailsActivity extends BaseActivity implements
        QuestionDetailsViewMvc.Listener, FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Service private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    @Service private DialogsManager mDialogsManager;
    @Service private ViewMvcFactory mViewMvcFactory;

    private String mQuestionId;
    private QuestionDetailsViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);

        mViewMvc = mViewMvcFactory.newInstance(QuestionDetailsViewMvc.class, null);

        setContentView(mViewMvc.getRootView());

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionDetailsUseCase.registerListener(this);

        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionDetailsUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionDetails question) {
        mViewMvc.bindQuestion(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
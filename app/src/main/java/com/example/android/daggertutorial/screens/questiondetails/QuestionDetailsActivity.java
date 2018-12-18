package com.example.android.daggertutorial.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.daggertutorial.questions.QuestionDetails;
import com.example.android.daggertutorial.screens.activities.BaseActivity;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.dialogs.ServerErrorDialogFragment;

public class QuestionDetailsActivity extends BaseActivity implements
        QuestionDetailsViewMvc.Listener, FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private String mQuestionId;
    private QuestionDetailsViewMvc mViewMvc;
    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getViewMvcFactory().newInstance(QuestionDetailsViewMvc.class, null);

        setContentView(mViewMvc.getRootView());

        mFetchQuestionDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        mDialogsManager = getCompositionRoot().getDialogsManager();
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
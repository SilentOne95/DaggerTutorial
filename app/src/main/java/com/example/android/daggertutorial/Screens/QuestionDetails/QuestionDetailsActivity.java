package com.example.android.daggertutorial.Screens.QuestionDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.Questions.QuestionWithBody;
import com.example.android.daggertutorial.Screens.Dialogs.DialogsManager;
import com.example.android.daggertutorial.Screens.Questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.Screens.Dialogs.ServerErrorDialogFragment;

public class QuestionDetailsActivity extends AppCompatActivity implements
        QuestionDetailsViewMVC.Listener, FetchQuestionDetailsUseCase.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private String mQuestionId;
    private QuestionDetailsViewMVC mViewMVC;
    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionDetailsViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());

        mFetchQuestionDetailsUseCase = new FetchQuestionDetailsUseCase();

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

        mDialogsManager = new DialogsManager(getSupportFragmentManager());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mFetchQuestionDetailsUseCase.registerListener(this);
        mFetchQuestionDetailsUseCase.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        mFetchQuestionDetailsUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionDetailsSucceeded(QuestionWithBody question) {
        mViewMVC.bindQuestions(question);
    }

    @Override
    public void onFetchOfQuestionDetailsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
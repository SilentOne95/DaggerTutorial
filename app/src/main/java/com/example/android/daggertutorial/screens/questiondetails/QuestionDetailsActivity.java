package com.example.android.daggertutorial.screens.questiondetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.daggertutorial.questions.QuestionDetails;
import com.example.android.daggertutorial.screens.activities.BaseActivity;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.screens.dialogs.ServerErrorDialogFragment;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;
import com.example.android.daggertutorial.screens.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class QuestionDetailsActivity extends BaseActivity implements
        QuestionDetailsViewMvc.Listener, QuestionDetailsViewModel.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    @Inject DialogsManager mDialogsManager;
    @Inject ViewMvcFactory mViewMvcFactory;
    @Inject ViewModelFactory mViewModelFactory;

    private String mQuestionId;
    private QuestionDetailsViewMvc mViewMvc;
    private QuestionDetailsViewModel mQuestionDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);

        mViewMvc = mViewMvcFactory.newInstance(QuestionDetailsViewMvc.class, null);

        mQuestionDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(QuestionDetailsViewModel.class);

        setContentView(mViewMvc.getRootView());

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mQuestionDetailsViewModel.registerListener(this);

        mQuestionDetailsViewModel.fetchQuestionDetailsAndNotify(mQuestionId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mQuestionDetailsViewModel.unregisterListener(this);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }
}
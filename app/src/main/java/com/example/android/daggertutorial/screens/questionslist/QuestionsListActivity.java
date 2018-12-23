package com.example.android.daggertutorial.screens.questionslist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.example.android.daggertutorial.questions.Question;
import com.example.android.daggertutorial.screens.activities.BaseActivity;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.dialogs.ServerErrorDialogFragment;
import com.example.android.daggertutorial.screens.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class QuestionsListActivity extends BaseActivity implements
        QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;

    @Inject FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    @Inject DialogsManager mDialogsManager;
    @Inject ViewMvcFactory mViewMvcFactory;
    @Inject ViewModelFactory mViewModelFactory;

    private QuestionsListViewMvc mViewMvc;
    private QuestionsListViewModel mQuestionsListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);

        mViewMvc = mViewMvcFactory.newInstance(QuestionsListViewMvc.class, null);

        mQuestionsListViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(QuestionsListViewModel.class);

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionsListUseCase.registerListener(this);

        if (mQuestionsListViewModel.getQuestions().isEmpty()){
            mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
        } else {
            mViewMvc.bindQuestions(mQuestionsListViewModel.getQuestions());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mQuestionsListViewModel.setQuestions(questions);
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
    }
}
package com.example.android.daggertutorial.screens.questionslist;

import android.os.Bundle;

import com.example.android.daggertutorial.questions.Question;
import com.example.android.daggertutorial.screens.activities.BaseActivity;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.questiondetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.dialogs.ServerErrorDialogFragment;

import java.util.List;

public class QuestionsListActivity extends BaseActivity implements
        QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    private QuestionsListViewMvc mViewMvc;
    private DialogsManager mDialogsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getViewMvcFactory().newInstance(QuestionsListViewMvc.class, null);

        setContentView(mViewMvc.getRootView());

        mFetchQuestionsListUseCase = getCompositionRoot().getFetchQuestionsListUseCase();

        mDialogsManager = getCompositionRoot().getDialogsManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchQuestionsListUseCase.registerListener(this);

        mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
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
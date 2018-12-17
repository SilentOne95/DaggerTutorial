package com.example.android.daggertutorial.Screens.QuestionsList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.MyApplication;
import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.Screens.Dialogs.DialogsManager;
import com.example.android.daggertutorial.Screens.QuestionDetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.Screens.FetchQuestions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.Screens.Dialogs.ServerErrorDialogFragment;

import java.util.List;

public class QuestionsListActivity extends AppCompatActivity implements
        QuestionsListViewMVC.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    private QuestionsListViewMVC mViewMVC;
    private DialogsManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());

        mFetchQuestionsListUseCase = ((MyApplication) getApplication()).getCompositionRoot().getFetchQuestionsListUseCase();

        mDialogManager = new DialogsManager(getSupportFragmentManager());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMVC.registerListener(this);
        mFetchQuestionsListUseCase.registerListener(this);
        mFetchQuestionsListUseCase.fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMVC.unregisterListener(this);
        mFetchQuestionsListUseCase.unregisterListener(this);
    }

    @Override
    public void onFetchOfQuestionsSucceeded(List<Question> questions) {
        mViewMVC.bindQuestions(questions);
    }

    @Override
    public void onFetchOfQuestionsFailed() {
        mDialogManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "");
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
    }
}
package com.example.android.daggertutorial.Screens.QuestionsList;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.Screens.QuestionDetails.QuestionDetailsActivity;
import com.example.android.daggertutorial.Screens.Questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.Screens.ServerErrorDialogFragment;

import java.util.List;


public class QuestionsListActivity extends AppCompatActivity implements
        QuestionsListViewMVC.Listener, FetchQuestionsListUseCase.Listener {

    private static final int NUM_OF_QUESTIONS_TO_FETCH = 20;
    private FetchQuestionsListUseCase mFetchQuestionsListUseCase;
    private QuestionsListViewMVC mViewMVC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMVC = new QuestionsListViewMVCImpl(LayoutInflater.from(this), null);
        setContentView(mViewMVC.getRootView());

        mFetchQuestionsListUseCase = new FetchQuestionsListUseCase();
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
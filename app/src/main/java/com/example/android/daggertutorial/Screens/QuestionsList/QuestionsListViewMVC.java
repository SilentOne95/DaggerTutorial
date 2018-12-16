package com.example.android.daggertutorial.Screens.QuestionsList;

import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.Screens.MVCViews.ObservableViewMVC;

import java.util.List;

public interface QuestionsListViewMVC extends ObservableViewMVC<QuestionsListViewMVC.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> question);
}
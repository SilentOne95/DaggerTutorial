package com.example.android.daggertutorial.screens.questionslist;

import com.example.android.daggertutorial.questions.Question;
import com.example.android.daggertutorial.screens.mvcviews.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);
}
package com.example.android.daggertutorial.screens.questiondetails;

import com.example.android.daggertutorial.questions.QuestionDetails;
import com.example.android.daggertutorial.screens.mvcviews.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestion(QuestionDetails question);
}
package com.example.android.daggertutorial.Screens.QuestionDetails;

import com.example.android.daggertutorial.Questions.QuestionWithBody;
import com.example.android.daggertutorial.Screens.MVCViews.ObservableViewMVC;

public interface QuestionDetailsViewMVC extends ObservableViewMVC<QuestionDetailsViewMVC.Listener> {

    interface Listener {
        // currently no user actions
    }

    void bindQuestions(QuestionWithBody question);
}
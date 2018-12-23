package com.example.android.daggertutorial.screens.questionslist;

import android.arch.lifecycle.ViewModel;

import com.example.android.daggertutorial.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListViewModel extends ViewModel {

    private List<Question> mQuestions = new ArrayList<>();

    public List<Question> getQuestions() { return mQuestions; }

    public void setQuestions(List<Question> questions) { mQuestions = questions; }
}

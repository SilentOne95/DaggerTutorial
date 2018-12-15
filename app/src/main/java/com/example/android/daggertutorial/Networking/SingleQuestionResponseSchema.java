package com.example.android.daggertutorial.Networking;

import com.example.android.daggertutorial.Questions.QuestionWithBody;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class SingleQuestionResponseSchema {

    @SerializedName("items")
    private final List<QuestionWithBody> mQuestions;

    public SingleQuestionResponseSchema(QuestionWithBody question) {
        mQuestions = Collections.singletonList(question);
    }

    public QuestionWithBody getQuestion() {
        return mQuestions.get(0);
    }
}
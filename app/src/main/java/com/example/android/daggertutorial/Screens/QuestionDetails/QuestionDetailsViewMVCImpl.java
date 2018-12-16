package com.example.android.daggertutorial.Screens.QuestionDetails;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.daggertutorial.Questions.QuestionWithBody;
import com.example.android.daggertutorial.R;
import com.example.android.daggertutorial.Screens.MVCViews.BaseViewMVC;

public class QuestionDetailsViewMVCImpl extends BaseViewMVC<QuestionDetailsViewMVC.Listener>
        implements QuestionDetailsViewMVC {

    private final TextView mTxtQuestionBody;

    public QuestionDetailsViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_question_details, container, false));
        mTxtQuestionBody = findViewById(R.id.txt_question_body);
    }

    @Override
    public void bindQuestions(QuestionWithBody question) {
        String questionBody = question.getBody();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
        } else {
            mTxtQuestionBody.setText(Html.fromHtml(questionBody));
        }
    }
}
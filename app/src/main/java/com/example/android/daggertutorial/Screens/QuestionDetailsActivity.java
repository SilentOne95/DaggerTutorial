package com.example.android.daggertutorial.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.Networking.SingleQuestionResponseSchema;
import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDetailsActivity extends AppCompatActivity implements
        Callback<SingleQuestionResponseSchema> {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private TextView mTxtQuestionBody;

    private StackOverflowApi mStackOverflowApi;

    private Call<SingleQuestionResponseSchema> mCall;

    private String mQuestionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question_details);

        mTxtQuestionBody = findViewById(R.id.txt_question_body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackOverflowApi = retrofit.create(StackOverflowApi.class);

        //noinspection ConstantConditions
        mQuestionId = getIntent().getExtras().getString(EXTRA_QUESTION_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = mStackOverflowApi.questionDetails(mQuestionId);
        mCall.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onResponse(@NonNull Call<SingleQuestionResponseSchema> call,
                           @NonNull Response<SingleQuestionResponseSchema> response) {
        SingleQuestionResponseSchema questionResponseSchema;
        if (response.isSuccessful() && (questionResponseSchema = response.body()) != null) {
            String questionBody = questionResponseSchema.getQuestion().getBody();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mTxtQuestionBody.setText(Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY));
            } else {
                mTxtQuestionBody.setText(Html.fromHtml(questionBody));
            }
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(@NonNull Call<SingleQuestionResponseSchema> call, @NonNull Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }
}

package com.example.android.daggertutorial.Screens;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.daggertutorial.Constants;
import com.example.android.daggertutorial.Networking.QuestionsListResponseSchema;
import com.example.android.daggertutorial.Networking.StackOverflowApi;
import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsListActivity extends AppCompatActivity implements
        Callback<QuestionsListResponseSchema> {

    private RecyclerView mRecyclerView;
    private QuestionsAdapter mQuestionsAdapter;
    private StackOverflowApi mStackOverflowApi;
    private Call<QuestionsListResponseSchema> mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_questions_list);

        // init recycler view
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mQuestionsAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                QuestionDetailsActivity.start(QuestionsListActivity.this, question.getId());
            }
        });

        mRecyclerView.setAdapter(mQuestionsAdapter);

        // init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mStackOverflowApi = retrofit.create(StackOverflowApi.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCall = mStackOverflowApi.lastActiveQuestions(20);
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
    public void onResponse(@NonNull Call<QuestionsListResponseSchema> call,
                           @NonNull Response<QuestionsListResponseSchema> response) {
        QuestionsListResponseSchema responseSchema;
        if (response.isSuccessful() && (responseSchema = response.body()) != null) {
            mQuestionsAdapter.bindData(responseSchema.getQuestions());
        } else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss();
    }

    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------


    public interface OnQuestionClickListener {
        void onQuestionClicked(Question question);
    }

    public static class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

        private final OnQuestionClickListener mOnQuestionClickListener;

        private List<Question> mQuestionsList = new ArrayList<>(0);

        class QuestionViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle;

            QuestionViewHolder(View view) {
                super(view);
                mTitle = view.findViewById(R.id.txt_title);
            }
        }

        QuestionsAdapter(OnQuestionClickListener onQuestionClickListener) {
            mOnQuestionClickListener = onQuestionClickListener;
        }

        void bindData(List<Question> questions) {
            mQuestionsList = new ArrayList<>(questions);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_questions, parent, false);

            return new QuestionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionViewHolder holder, final int position) {
            holder.mTitle.setText(mQuestionsList.get(position).getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnQuestionClickListener.onQuestionClicked(mQuestionsList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mQuestionsList.size();
        }
    }
}

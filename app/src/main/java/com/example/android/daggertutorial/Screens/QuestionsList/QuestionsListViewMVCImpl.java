package com.example.android.daggertutorial.Screens.QuestionsList;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.daggertutorial.Questions.Question;
import com.example.android.daggertutorial.R;
import com.example.android.daggertutorial.Screens.MVCViews.BaseViewMVC;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListViewMVCImpl extends BaseViewMVC<QuestionsListViewMVC.Listener>
        implements QuestionsListViewMVC {

    private QuestionsAdapter mQuestionsAdapter;

    public QuestionsListViewMVCImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_questions_list, container, false));

        // init recycler view
        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mQuestionsAdapter = new QuestionsAdapter(new OnQuestionClickListener() {
            @Override
            public void onQuestionClicked(Question question) {
                for (Listener listener : getListeners()) {
                    listener.onQuestionClicked(question);
                }
            }
        });
        mRecyclerView.setAdapter(mQuestionsAdapter);
    }

    @Override
    public void bindQuestions(List<Question> questions) {
        mQuestionsAdapter.bindData(questions);
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
        public void onBindViewHolder(QuestionViewHolder holder, final int position) {
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
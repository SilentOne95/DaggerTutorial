package com.example.android.daggertutorial.Screens.MVCViews;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.daggertutorial.Screens.QuestionDetails.QuestionDetailsViewMVC;
import com.example.android.daggertutorial.Screens.QuestionDetails.QuestionDetailsViewMVCImpl;
import com.example.android.daggertutorial.Screens.QuestionsList.QuestionsListViewMVC;
import com.example.android.daggertutorial.Screens.QuestionsList.QuestionsListViewMVCImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMVC> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container) {

        ViewMVC viewMvc;

        if (mvcViewClass == QuestionsListViewMVC.class) {
            viewMvc = new QuestionsListViewMVCImpl(mLayoutInflater, container);
        } else if (mvcViewClass == QuestionDetailsViewMVC.class){
            viewMvc = new QuestionDetailsViewMVCImpl(mLayoutInflater, container);
        } else {
            throw new IllegalArgumentException("Unsupported MVC view class " + mvcViewClass);
        }

        return (T) viewMvc;
    }
}

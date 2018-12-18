package com.example.android.daggertutorial.common.dependencyinjection;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.ImageLoader;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final AppCompatActivity mActivity;

    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       AppCompatActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    private FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    private Activity getActivity() {
        return mActivity;
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(getFragmentManager());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mCompositionRoot.getFetchQuestionDetailsUseCase();
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mCompositionRoot.getFetchQuestionsListUseCase();
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getImageLoader());
    }

    private ImageLoader getImageLoader() {
        return new ImageLoader(getActivity());
    }
}
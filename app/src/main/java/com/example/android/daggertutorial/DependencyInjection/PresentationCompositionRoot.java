package com.example.android.daggertutorial.DependencyInjection;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.Questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.Questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.Screens.Dialogs.DialogsManager;
import com.example.android.daggertutorial.Screens.MVCViews.ViewMvcFactory;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentManager mFragmentManager;
    private LayoutInflater mLayoutInflater;

    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       FragmentManager fragmentManager,
                                       LayoutInflater layoutInflater) {
        mCompositionRoot = compositionRoot;
        mFragmentManager = fragmentManager;
        mLayoutInflater = layoutInflater;
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(mFragmentManager);
    }

    public FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mCompositionRoot.getFetchQuestionsListUseCase();
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mCompositionRoot.getFetchQuestionDetailsUseCase();
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(mLayoutInflater);
    }
}
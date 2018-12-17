package com.example.android.daggertutorial.DependencyInjection;

import android.support.v4.app.FragmentManager;

import com.example.android.daggertutorial.Questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.Questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.Screens.Dialogs.DialogsManager;

public class PresentationCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentManager mFragmentManager;

    public PresentationCompositionRoot(CompositionRoot compositionRoot,
                                       FragmentManager fragmentManager) {
        mCompositionRoot = compositionRoot;
        mFragmentManager = fragmentManager;
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
}
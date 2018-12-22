package com.example.android.daggertutorial.common.dependencyinjection.presentation;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.android.daggertutorial.common.dependencyinjection.application.ApplicationComponent;
import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.ImageLoader;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity mActivity;
    private final ApplicationComponent mApplicationComponent;

    public PresentationModule(FragmentActivity fragmentActivity, ApplicationComponent applicationComponent) {
        mActivity = fragmentActivity;
        mApplicationComponent = applicationComponent;
    }

    @Provides
    FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    @Provides
    Activity getActivity() {
        return mActivity;
    }

    @Provides
    DialogsManager getDialogsManager() {
        return new DialogsManager(getFragmentManager());
    }

    @Provides
    FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return mApplicationComponent.getFetchQuestionDetailsUseCase();
    }

    @Provides
    FetchQuestionsListUseCase getFetchQuestionsListUseCase() {
        return mApplicationComponent.getFetchQuestionsListUseCase();
    }

    @Provides
    ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getImageLoader());
    }

    @Provides
    ImageLoader getImageLoader() {
        return new ImageLoader(getActivity());
    }
}

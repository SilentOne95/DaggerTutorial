package com.example.android.daggertutorial.screens.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.example.android.daggertutorial.common.dependencyinjection.Injector;
import com.example.android.daggertutorial.MyApplication;
import com.example.android.daggertutorial.common.dependencyinjection.application.ApplicationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.presentation.DaggerPresentationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationModule;

public class BaseActivity extends AppCompatActivity {

    private boolean mIsInjectorUsed;

    @UiThread
    protected Injector getInjector() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;

        return new Injector(getPresentationComponent());
    }

    private PresentationComponent getPresentationComponent() {
        return DaggerPresentationComponent.builder()
                .presentationModule(new PresentationModule(this, getApplicationComponent()))
                .build();
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
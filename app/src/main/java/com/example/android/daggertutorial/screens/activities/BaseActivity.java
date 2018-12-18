package com.example.android.daggertutorial.screens.activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.example.android.daggertutorial.common.dependencyinjection.CompositionRoot;
import com.example.android.daggertutorial.common.dependencyinjection.Injector;
import com.example.android.daggertutorial.common.dependencyinjection.PresentationCompositionRoot;
import com.example.android.daggertutorial.MyApplication;

public class BaseActivity extends AppCompatActivity {

    private PresentationCompositionRoot mPresentationCompositionRoot;

    @UiThread
    protected Injector getInjector() {
        return new Injector(getCompositionRoot());
    }

    private PresentationCompositionRoot getCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),
                    this
            );
        }

        return mPresentationCompositionRoot;
    }

    private CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
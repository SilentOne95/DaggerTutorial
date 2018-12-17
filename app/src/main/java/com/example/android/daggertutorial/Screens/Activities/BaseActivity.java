package com.example.android.daggertutorial.Screens.Activities;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.example.android.daggertutorial.DependencyInjection.CompositionRoot;
import com.example.android.daggertutorial.DependencyInjection.PresentationCompositionRoot;
import com.example.android.daggertutorial.MyApplication;

public class BaseActivity extends AppCompatActivity {

    private PresentationCompositionRoot mPresentationCompositionRoot;

    @UiThread
    protected PresentationCompositionRoot getCompositionRoot() {
        if (mPresentationCompositionRoot == null) {
            mPresentationCompositionRoot = new PresentationCompositionRoot(
                    getAppCompositionRoot(),
                    getSupportFragmentManager()
            );
        }

        return mPresentationCompositionRoot;
    }

    private CompositionRoot getAppCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
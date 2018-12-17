package com.example.android.daggertutorial;

import android.app.Application;

import com.example.android.daggertutorial.DependencyInjection.CompositionRoot;

public class MyApplication  extends Application {

    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return mCompositionRoot;
    }
}
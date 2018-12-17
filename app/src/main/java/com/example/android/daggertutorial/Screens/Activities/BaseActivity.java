package com.example.android.daggertutorial.Screens.Activities;

import android.support.v7.app.AppCompatActivity;

import com.example.android.daggertutorial.DependencyInjection.CompositionRoot;
import com.example.android.daggertutorial.MyApplication;

public class BaseActivity extends AppCompatActivity {

    protected CompositionRoot getCompositionRoot() {
        return ((MyApplication) getApplication()).getCompositionRoot();
    }
}
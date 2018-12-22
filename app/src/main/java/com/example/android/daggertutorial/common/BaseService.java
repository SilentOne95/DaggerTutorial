package com.example.android.daggertutorial.common;

import android.app.Service;
import android.support.annotation.UiThread;

import com.example.android.daggertutorial.MyApplication;
import com.example.android.daggertutorial.common.dependencyinjection.application.ApplicationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.service.ServiceComponent;
import com.example.android.daggertutorial.common.dependencyinjection.service.ServiceModule;

public abstract class BaseService extends Service {

    private boolean mIsServiceComponentUsed;

    @UiThread
    protected ServiceComponent getServiceComponenet() {
        if (mIsServiceComponentUsed) {
            throw new RuntimeException("there is no reason to perform injection more than once");
        }

        mIsServiceComponentUsed = true;

        return getApplicationComponent().newServiceComponent(new ServiceModule(this));
    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}

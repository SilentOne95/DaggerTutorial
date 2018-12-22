package com.example.android.daggertutorial.common.dependencyinjection.application;

import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationModule;
import com.example.android.daggertutorial.common.dependencyinjection.service.ServiceComponent;
import com.example.android.daggertutorial.common.dependencyinjection.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
    ServiceComponent newServiceComponent(ServiceModule serviceModule);
}
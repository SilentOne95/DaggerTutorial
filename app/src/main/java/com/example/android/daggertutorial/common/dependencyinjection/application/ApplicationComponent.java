package com.example.android.daggertutorial.common.dependencyinjection.application;

import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationComponent;
import com.example.android.daggertutorial.common.dependencyinjection.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
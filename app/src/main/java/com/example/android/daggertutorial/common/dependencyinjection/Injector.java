package com.example.android.daggertutorial.common.dependencyinjection;

import com.example.android.daggertutorial.questions.FetchQuestionDetailsUseCase;
import com.example.android.daggertutorial.questions.FetchQuestionsListUseCase;
import com.example.android.daggertutorial.screens.dialogs.DialogsManager;
import com.example.android.daggertutorial.screens.mvcviews.ViewMvcFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Injector {

    private final PresentationCompositionRoot mPresentationCompositionRoot;

    public Injector(PresentationCompositionRoot presentationCompositionRoot) {
        mPresentationCompositionRoot = presentationCompositionRoot;
    }

    public void inject(Object client) {
        Class clazz = client.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (isPublicNotStaticNotFinal(field)) {
                injectField(client, field);
            }
        }
    }

    private boolean isPublicNotStaticNotFinal(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers);
    }

    private void injectField(Object client, Field field) {
        try {
            field.set(client, getServiceForClass(field.getType()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getServiceForClass(Class<?> type) {
        if (type.equals(DialogsManager.class)) {
            return mPresentationCompositionRoot.getDialogsManager();
        }
        else if (type.equals(ViewMvcFactory.class)) {
            return mPresentationCompositionRoot.getViewMvcFactory();
        }
        else if (type.equals(FetchQuestionsListUseCase.class)) {
            return mPresentationCompositionRoot.getFetchQuestionsListUseCase();
        }
        else if (type.equals(FetchQuestionDetailsUseCase.class)) {
            return mPresentationCompositionRoot.getFetchQuestionDetailsUseCase();
        }
        else {
            throw new RuntimeException("unsupported service type class: " + type);
        }
    }
}
package com.example.android.daggertutorial.Screens.Dialogs;

import android.support.v4.app.FragmentManager;

public class DialogsManagerFactory {

    public DialogsManager newDialogManager(FragmentManager fragmentManager) {
        return new DialogsManager(fragmentManager);
    }
}
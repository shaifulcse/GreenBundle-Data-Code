package com.enrico.sample;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.view.ContextThemeWrapper;

class Preferences {

    //multi-preference dialog for theme options
    private static int resolveTheme(Context context) {

        //Themes options

        //light theme
        int light = R.style.AppTheme;

        //dark theme
        int dark = R.style.AppThemeDark;

        String choice = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_theme), String.valueOf(0));
        switch (Integer.parseInt(choice)) {
            default:
            case 0:
                return light;
            case 1:
                return dark;
        }
    }

    //method to apply selected theme
    static void applyTheme(ContextThemeWrapper contextThemeWrapper, Context context) {
        int theme = resolveTheme(context);
        contextThemeWrapper.setTheme(theme);

    }
}

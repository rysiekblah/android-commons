package com.rysiekblah.android.commons.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.rysiekblah.android.commons.exception.ExceptionHandlingStrategy;
import com.rysiekblah.android.commons.exception.UncaughtExceptionHandler;

/**
 * Created by tomek on 7/5/14.
 */
public class FitActionBarActivity extends ActionBarActivity {

    protected ExceptionHandlingStrategy myExceptionHandlingStrategy() {
        return null;
    };

    protected boolean isExceptionHandingEnabled() {
        return false;
    }

    protected UncaughtExceptionHandler createHandler() {
        if (myExceptionHandlingStrategy() == null) {
            return new UncaughtExceptionHandler();
        } else {
            return new UncaughtExceptionHandler(myExceptionHandlingStrategy());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isExceptionHandingEnabled()) {
            Thread.setDefaultUncaughtExceptionHandler(createHandler());
        }
    }

}

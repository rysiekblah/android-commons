package com.rysiekblah.android.commons.service;

import android.app.Service;

import com.rysiekblah.android.commons.exception.ExceptionHandlingStrategy;
import com.rysiekblah.android.commons.exception.UncaughtExceptionHandler;

/**
 * Created by tomek on 7/17/14.
 */
public abstract class FitService extends Service {

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
    public void onCreate() {
        if (isExceptionHandingEnabled()) {
            Thread.setDefaultUncaughtExceptionHandler(createHandler());
        }
    }
}

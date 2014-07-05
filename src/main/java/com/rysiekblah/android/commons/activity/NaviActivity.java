package com.rysiekblah.android.commons.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

import com.rysiekblah.android.commons.exception.ExceptionHandlingStrategy;
import com.rysiekblah.android.commons.exception.UncaughtExceptionHandler;

/**
 * Created by tomek on 7/5/14.
 */
public abstract class NaviActivity extends Activity {

    protected abstract Class myParent();

    protected abstract ExceptionHandlingStrategy myExceptionHandlingStrategy();

    protected void navigateUp(Activity activity) {
        Intent upIntent = NavUtils.getParentActivityIntent(activity);
        if (upIntent == null) {
            upIntent = new Intent(activity, myParent());
        }
        if (NavUtils.shouldUpRecreateTask(activity, upIntent)) {
            TaskStackBuilder.create(activity).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            NavUtils.navigateUpTo(activity, upIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(myExceptionHandlingStrategy()));
    }
}

package com.rysiekblah.android.commons.exception;

import android.os.Build;
import android.util.Log;

/**
 * Created by tomek on 7/5/14.
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String LINE_SEPARATOR = "\n";
    private static final String TAG = "";
    private ExceptionHandlingStrategy strategy;

    public UncaughtExceptionHandler(ExceptionHandlingStrategy strategy) {
        this.strategy = strategy;
    }

    public UncaughtExceptionHandler() {
        this.strategy = null;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {

        final StringBuilder errorReport = new StringBuilder();
        errorReport.append("CAUSE OF ERROR:\n\n");
        errorReport.append(StackTraceReader.asString(exception));

        errorReport.append("\nDEVICE INFORMATION:\n");
        errorReport.append("Brand: ").append(Build.BRAND).append(LINE_SEPARATOR);
        errorReport.append("Device: ").append(Build.DEVICE).append(LINE_SEPARATOR);
        errorReport.append("Model: ").append(Build.MODEL).append(LINE_SEPARATOR);
        errorReport.append("Id: ").append(Build.ID).append(LINE_SEPARATOR);
        errorReport.append("Product: ").append(Build.PRODUCT).append(LINE_SEPARATOR);

        errorReport.append("\nFIRMWARE:\n");
        errorReport.append("SDK: ").append(Build.VERSION.SDK_INT).append(LINE_SEPARATOR);
        errorReport.append("Release: ").append(Build.VERSION.RELEASE).append(LINE_SEPARATOR);
        errorReport.append("Incremental: ").append(Build.VERSION.INCREMENTAL).append(LINE_SEPARATOR);

        Log.e(TAG, "ERROR: " + errorReport.toString());

        if (strategy != null) {
            if (strategy.isFast()) {
                strategy.execute(errorReport.toString());
                return;
            }
            Thread th = new Thread() {
                @Override
                public void run() {
                    strategy.execute(errorReport.toString());
                }
            };
            th.start();
        }
    }

}
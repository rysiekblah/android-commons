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

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
//        StringWriter stackTrace = new StringWriter();
//        exception.printStackTrace(new PrintWriter(stackTrace));
        final StringBuilder errorReport = new StringBuilder();
        errorReport.append("CAUSE OF ERROR:\n\n");
        errorReport.append(StackTraceReader.go(exception));
        //errorReport.append(stackTrace.toString());

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

        Thread th = new Thread() {
            @Override
            public void run() {
                strategy.execute();
            }
        };
        th.start();

    }
}
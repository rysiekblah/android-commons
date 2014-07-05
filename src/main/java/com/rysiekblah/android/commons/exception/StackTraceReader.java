package com.rysiekblah.android.commons.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by tomek on 7/5/14.
 */
public class StackTraceReader {
    public static String go(Throwable throwable) {
        StringWriter stackTrace = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTrace));
        return stackTrace.toString();
    }
}

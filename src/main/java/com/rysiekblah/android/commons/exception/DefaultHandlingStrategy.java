package com.rysiekblah.android.commons.exception;

/**
 * Created by tomek on 7/5/14.
 */
public class DefaultHandlingStrategy implements ExceptionHandlingStrategy {
    @Override
    public void execute(String err) {

    }

    @Override
    public boolean isFast() {
        return false;
    }
}

package com.rysiekblah.android.commons.media;

/**
 * Created by tomek on 7/6/14.
 */
public class MediaFileException extends RuntimeException {
    public MediaFileException(String message) {
        super(message);
    }

    public MediaFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

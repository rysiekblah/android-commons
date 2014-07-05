package com.rysiekblah.android.commons.media;

/**
 * Created by tomek on 7/5/14.
 */
public interface MediaFileStrategy {
    void store();

    void send();

    void delete();

    PictureDescriptor transform(int outWidth, int outHeight);

    String getFolder();

}

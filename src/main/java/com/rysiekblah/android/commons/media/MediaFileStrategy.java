package com.rysiekblah.android.commons.media;

import android.graphics.Bitmap;

/**
 * Created by tomek on 7/5/14.
 */
public interface MediaFileStrategy {

    PictureDescriptor transform(int outWidth, int outHeight);

    Bitmap transform(Bitmap bitmap);

    String getFolder();

}

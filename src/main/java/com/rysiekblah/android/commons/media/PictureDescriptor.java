package com.rysiekblah.android.commons.media;

/**
 * Created by tomek on 7/5/14.
 */
public class PictureDescriptor {
    public int width=0;
    public int height=0;

    public boolean isValid() {
        return width!=0 && height!=0;
    }
}

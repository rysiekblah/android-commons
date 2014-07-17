package com.rysiekblah.android.commons.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.common.base.Function;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tomek on 7/5/14.
 */
public class MediaFilesUtils {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String APP_TAG = "";

    private static final String MEDIA_IMAGE_EXTENSION = ".jpg";
    private static final String MEDIA_VIDEO_EXTENSION = ".mp4";

    private static final Function<Date, String> TIMESTAMP = new Function<Date, String>() {
        @Override
        public String apply(Date input) {
            return new SimpleDateFormat("yyyyMMdd_HHmmss").format(input);
        }
    };

    private static final Function<String, File> IMAGE_FILE = new Function<String, File>() {
        @Override
        public File apply(String path) {
            return new File(path + File.separator + "IMG_" + TIMESTAMP.apply(new Date()) + MEDIA_IMAGE_EXTENSION);
        }
    };

    private static final Function<String, File> VIDEO_FILE = new Function<String, File>() {
        @Override
        public File apply(String path) {
            return new File(path + File.separator + "VID_" + TIMESTAMP.apply(new Date()) + MEDIA_VIDEO_EXTENSION);
        }
    };

    private static MediaFileStrategy mediaFileStrategy;

    public static void setStrategy(MediaFileStrategy strategy) {
        mediaFileStrategy = strategy;
    }

    /**
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     *
     * @param path
     * @return
     */
    public static Bitmap decodeAndResize(String path) {
        if (mediaFileStrategy == null) {
            throw new MediaFileException("MediaFileStrategy missing");
        }
        return decodeAndResize(path, mediaFileStrategy);
    }

    /**
     *
     * @param path
     * @param strategy
     * @return
     */
    public static Bitmap decodeAndResize(String path, MediaFileStrategy strategy) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        if (strategy == null) {
            options.inSampleSize = calculateInSampleSize(options, options.outWidth, options.outHeight);
        } else {
            PictureDescriptor picture = strategy.transform(options.outWidth, options.outHeight);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, picture.width, picture.height);
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     *
     * @param path
     * @return
     */
    public static Bitmap decode(String path) {
        return decodeAndResize(path, null);
    }

    /**
     *
     * @param type
     * @return
     */
    public static Uri getOutputMediaFileUri(int type) {
        if (mediaFileStrategy == null) {
            throw new MediaFileException("MediaFileStrategy missing");
        }
        return getOutputMediaFileUri(type, mediaFileStrategy);
    }

    /**
     * Create a file Uri for saving an image or video
     * @param type
     * @param strategy
     * @return
     */
    public static Uri getOutputMediaFileUri(int type, MediaFileStrategy strategy){
        return Uri.fromFile(getOutputMediaFile(type, strategy));
    }

    /**
     *
     * @param type
     * @return
     */
    public static File getOutputMediaFile(int type) {
        if (mediaFileStrategy == null) {
            throw new MediaFileException("MediaFileStrategy missing");
        }
        return getOutputMediaFile(type, mediaFileStrategy);
    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(int type, MediaFileStrategy strategy) {
        return getOutputMediaFile(pathDiskSpace(strategy), type);
    }

    /**
     *
     * @param folder
     * @param type
     * @return
     */
    public static File getOutputMediaFile(String folder, int type) {
        // TODO:
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(folder);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
                return null;
            }
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = IMAGE_FILE.apply(mediaStorageDir.getPath());
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = VIDEO_FILE.apply(mediaStorageDir.getPath());
        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     *
     * @param strategy
     * @return
     */
    public static String pathDiskSpace(MediaFileStrategy strategy) {
        return Environment.getExternalStorageDirectory() + "/" + strategy.getFolder();
    }


    /**
     *
     * @param strategy
     * @return
     */
    public static boolean createDiscSpace(MediaFileStrategy strategy) {
        Log.d(APP_TAG, "Creating app folder on SD " + pathDiskSpace(strategy));
        File file = new File(pathDiskSpace(strategy));
        if (!file.exists()) {
            Log.d(APP_TAG, "Path doesn't exist: " + file.getPath());
            return file.mkdirs();
        }
        Log.d(APP_TAG, "Path exists");
        return true;
    }

}

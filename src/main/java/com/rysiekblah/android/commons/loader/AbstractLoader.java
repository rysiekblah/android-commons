package com.rysiekblah.android.commons.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by tomek on 7/9/14.
 */
public abstract class AbstractLoader<T> extends AsyncTaskLoader<T> {

    protected T mLoadedData;

    public AbstractLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        T oldLoadedData = mLoadedData;
        mLoadedData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldLoadedData != null) {
            onReleaseResources(oldLoadedData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mLoadedData == null) {
            deliverResult(mLoadedData);
        }
        if (takeContentChanged() || mLoadedData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(T data) {
        super.onCanceled(data);
        onReleaseResources(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

        if (mLoadedData != null) {
            onReleaseResources(mLoadedData);
            mLoadedData = null;
        }
    }

    protected void onReleaseResources(T data) {

    }


}

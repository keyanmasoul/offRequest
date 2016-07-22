package com.zjj.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * WeakHandler
 * Created by zjj on 2016/7/18.
 */
public class WeakHandler extends Handler {
    private final WeakReference<Activity> mActivity;

    private final IHandlerDo iHandlerDo;

    public WeakHandler(Activity activity) {
        mActivity = new WeakReference<>(activity);
        this.iHandlerDo = (IHandlerDo) activity;
    }

    @Override
    public void handleMessage(Message msg) {
        Activity activity = mActivity.get();
        if (activity != null) {
            iHandlerDo.doUI();
        }
    }
}

package com.zjj.http.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.OkHttpStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by zjj on 2016/3/2.
 */
public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    private VolleySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(),new OkHttpStack());
    }

    public RequestQueue getmRequestQueue() {
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }

    public void cancleRequest(Object tag) {
        getmRequestQueue().cancelAll(tag);
    }
}

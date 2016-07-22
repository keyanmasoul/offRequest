package com.zjj.http;

import com.litesuits.android.log.Log;
import com.zjj.http.offqueue.ImplOffResponse;

/**
 * UserOffResponseImpl
 * Created by zjj on 2016/7/15.
 */
public class UserOffResponseImpl implements ImplOffResponse {
    @Override
    public void handleResponse(Object o) {
        UserManager.getInstance().setUser((User) o);
        Log.e("UserOffResponseImpl", "---callback");

    }
}

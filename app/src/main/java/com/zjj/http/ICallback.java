package com.zjj.http;

/**
 * Created by zjj on 2016/5/4.
 */
public interface ICallback {
    void onSuccess(String result);

    void onFailure(Exception e);
}

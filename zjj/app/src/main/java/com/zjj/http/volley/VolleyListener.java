package com.zjj.http.volley;

/**
 * Created by zjj on 2016/3/2.
 */
public interface VolleyListener {
    /**
     * 在请求之前调用方法
     */
    abstract void onPreRequest(String data);

    /**
     * 请求成功调用
     *
     * @param response
     */
    abstract void onRequestSuccess(VolleyResponse response);

    /**
     * 调用参数错误，致命错误
     *
     * @param code
     * @param msg
     */
    abstract void onRequestError(int code, String msg);

    /**
     * 服务器返回失败是调用
     *
     * @param code
     * @param msg
     */
    abstract void onRequestFail(int code, String msg);
}

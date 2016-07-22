package com.zjj.http.volley;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;
import java.util.Set;


/**
 * Created by zjj on 2016/3/2.
 */
public class VolleyHttpClient {
    private static VolleyHttpClient mInstance;

    public static synchronized VolleyHttpClient getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyHttpClient();
        }
        return mInstance;
    }

    private VolleyHttpClient() {
    }

    public void post(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        request(context, url, Request.Method.POST, params, listener, false);
    }

    public void get(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        request(context, url, Request.Method.GET, params, listener, false);
    }

    public void getSign(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        requestSign(context, url, Request.Method.GET, params, listener, false);
    }

    public void postSign(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        requestSign(context, url, Request.Method.POST, params, listener, false);
    }

    public void getSignCacheable(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        requestSign(context, url, Request.Method.GET, params, listener, true);
    }

    public void getUnName(Context context, String url, Map<String, Object> params, final VolleyListener listener) {
        request(context, url, Request.Method.GET, params, listener, true);
    }

    /**
     * 签名请求
     *
     * @param url
     * @param method
     * @param params
     * @param listener
     */
    private void requestSign(final Context context, String url, int method, Map<String, Object> params,
                             final VolleyListener listener, final boolean bCache) {
        if (listener == null) {
            return;
        }
        if (method == Request.Method.GET) {
            url = makeupUrl(url, params);
            if (bCache) {
                Cache.Entry entry = VolleySingleton.getInstance(context).getmRequestQueue().getCache()
                        .get(url);
                if (entry != null) {
                    listener.onPreRequest(new String(entry.data));
                } else {
                    listener.onPreRequest(null);
                }
            }
        }

        final String finalUrl = url;
        VolleyRequestSign volleyRequest = new VolleyRequestSign(method, url, params,
                new Response.Listener<VolleyResponse>() {

                    @Override
                    public void onResponse(VolleyResponse response) {
                        if (response.isSuccess()) {
                            listener.onRequestSuccess(response);
                        } else {
                            listener.onRequestFail(response.getErrorCode(), response.getErrorMessge());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error != null) {
                        }
                        if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                        }
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String jsonStr = new String(error.networkResponse.data);
                            parseErrorJsonString(jsonStr, context);
                        } else {
                        }

                    }
                }
        );
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        VolleySingleton.getInstance(context).addToRequestQueue(volleyRequest);
    }


    /**
     * 非签名请求
     *
     * @param url
     * @param method
     * @param params
     * @param listener
     */
    private void request(final Context context, String url, int method, Map<String, Object> params,
                         final VolleyListener listener, boolean bUnName) {
        if (listener == null) {
            return;
        }
        if (method == Request.Method.GET) {
            if (bUnName) {
                url = makeupUnNameUrl(url, params);
            } else {
                url = makeupUrl(url, params);
            }
        }
        VolleyRequest volleyRequest = new VolleyRequest(context, method, url, params,
                new Response.Listener<VolleyResponse>() {
                    @Override
                    public void onResponse(VolleyResponse response) {

                        if (response.isSuccess()) {
                            listener.onRequestSuccess(response);
                        } else {
                            listener.onRequestFail(response.getErrorCode(), response.getErrorMessge());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                            }
                        }
                    }
                }
        );
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        VolleySingleton.getInstance(context).addToRequestQueue(volleyRequest);
    }

    private String makeupUnNameUrl(String url, Map<String, Object> params) {
        String paramsStr = "";
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String vals = (String) entry.getValue();
                paramsStr += "/" + vals;
            }
        }
        return url + paramsStr;
    }

    private String makeupUrl(String url, Map<String, Object> params) {
        String paramsStr = "";
        if (params != null && !params.isEmpty()) {
            paramsStr = "?";
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String vals = (String) entry.getValue();
                paramsStr += "&" + key + "=" + vals;
            }
        }
        url += paramsStr;
        return url;
    }

    private void parseErrorJsonString(String jsonStr, Context context) {

    }
}

package com.zjj.http.volley;


import android.content.Context;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * VolleyRequest
 * Created by zjj on 2016/3/2.
 */
public class VolleyRequest extends Request<VolleyResponse> {
    public Response.Listener<VolleyResponse> mListener;
    public Map<String, Object> mParams;
    private Context mContext;

    public VolleyRequest(Context context, int method, String url, Map<String, Object> params, Response.Listener<VolleyResponse> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        mParams = params;
        mListener = listener;
        this.mContext = context;
    }

    @Override
    protected Response<VolleyResponse> parseNetworkResponse(NetworkResponse response) {
        VolleyResponse baseResponse = new VolleyResponse();
        String jsonStr = parseData(baseResponse, response.data);
        baseResponse.setData(jsonStr);
        return Response.success(baseResponse, null);
    }

    private String parseData(VolleyResponse response, byte[] data) {
        String jsonStr = new String(data);
        if (jsonStr.startsWith("[")) {
            // TODO: 2016/4/8

        } else if (this.getUrl().contains("syncUserData")) {
        } else {
            JSONObject js = JSON.parseObject(jsonStr);
            Set<String> set = js.keySet();
            for (String key : set) {
                if (key.equals("code")) {
                    response.setErrorCode(Integer.parseInt(js.getString("code")));
                    response.setErrorMessge(js.getString("message"));
                    break;
                }
            }
        }
        return jsonStr;
    }

    private void parseJsonString(VolleyResponse response, String jsonStr) {
        JSONObject js = JSON.parseObject(jsonStr);
        Set<String> set = js.keySet();
        for (String key : set) {
            if (key.equals("code")) {
                response.setErrorCode(Integer.parseInt(js.getString("code")));
                response.setErrorMessge(js.getString("message"));
                break;
            }
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mHeader = new HashMap<>();
        mHeader.put("appVersion", "2");
        mHeader.put("hwModel", "android");
        mHeader.put("osVersion", Build.VERSION.SDK_INT + "");
        return mHeader;
    }

    @Override
    protected Map<String, Object> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(VolleyResponse response) {
        mListener.onResponse(response);
    }
}

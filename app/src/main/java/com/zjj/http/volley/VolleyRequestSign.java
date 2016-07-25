package com.zjj.http.volley;


import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.zjj.http.UserManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.zip.GZIPInputStream;


/**
 * Created by zjj on 2016/3/2.
 */
public class VolleyRequestSign extends Request<VolleyResponse> {
    public Response.Listener<VolleyResponse> mListener;
    public Map<String, Object> mParams;
    public String signature;

    public VolleyRequestSign(int method, String url, Map<String, Object> params, Response.Listener<VolleyResponse> listener, Response.ErrorListener errorlistener) {
        super(method, url, errorlistener);
        mParams = params;
        mListener = listener;
        signature = getMd5(getSignatureStr());
    }

    private String getSignatureStr() {
        String paramsString = "";
        List<String> items = new LinkedList<>();
        if (mParams != null && !mParams.isEmpty()) {
            for (Map.Entry<String, Object> entry : mParams.entrySet()) {
                Object vals = entry.getValue();
                String key = entry.getKey();
                if (vals instanceof String) {
                    items.add(key + "=" + entry.getValue());
                } else if (vals instanceof List) {
                    String phones = "";
                    for (int i = 0; i < ((List) vals).size(); i++) {
                        phones = phones + ((List) vals).get(i);
                    }
                    items.add(key + "=" + phones);
                }
            }
        }
        Collections.sort(items);
        paramsString = joinStringArray(items.toArray(new String[0]));

        String secret = UserManager.getInstance().getUser().getSecret();
        String accessToken = UserManager.getInstance().getUser().getAccessToken();
        String signatureStr = secret + paramsString + accessToken;
        Log.e("REQUEST", signatureStr);
        return signatureStr;
    }

    private String joinStringArray(String[] strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb.toString();
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse == null || volleyError.networkResponse.data == null) {
            return super.parseNetworkError(volleyError);
        }
        return super.parseNetworkError(volleyError);
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
            try {
                return new String(decompress(data), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mHeader = new HashMap<String, String>();
        mHeader.put("Access-Token", UserManager.getInstance().getUser().getAccessToken());
        mHeader.put("signature", signature);
        mHeader.put("biuVersion", UserManager.getInstance().getUser().getVersion() + "");
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

    public static String getMd5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder strBuf = new StringBuilder();
            for (byte anEncryption : encryption) {
                if (Integer.toHexString(0xff & anEncryption).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & anEncryption));
                } else {
                    strBuf.append(Integer.toHexString(0xff & anEncryption));
                }
            }
            android.util.Log.d("request", "before==" + info + " after==" + strBuf.toString());
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 解压缩
        decompress(bais, baos);

        data = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return data;
    }

    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os)
            throws Exception {

        GZIPInputStream gis = new GZIPInputStream(is);

        int count;
        byte data[] = new byte[1024];
        while ((count = gis.read(data, 0, 1024)) != -1) {
            os.write(data, 0, count);
        }

        gis.close();
    }
}


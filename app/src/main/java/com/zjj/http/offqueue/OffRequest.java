package com.zjj.http.offqueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alibaba.fastjson.JSON;
import com.litesuits.android.log.Log;
import com.zjj.http.BaseBean;
import com.zjj.http.MainActivity;
import com.zjj.http.volley.VolleyCallback;
import com.zjj.http.volley.VolleyHttpClient;
import com.zjj.http.volley.VolleyResponse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * OffRequest
 * Created by zjj on 2016/7/14.
 */
public class OffRequest {
    private static final String TAG = "OffRequest";
    private volatile static OffRequest mInstance;

    private Context mContext;

    private OffDbImpl db;

    public static OffRequest getInstance() {
        if (mInstance == null) {
            synchronized (OffRequest.class) {
                if (mInstance == null) {
                    mInstance = new OffRequest();
                }
            }
        }
        return mInstance;
    }

    private ConnectionChangerReceiver receiver;

    public void init(Context context, OffDbImpl db) {
        if (mContext != null) {
            return;
        }
        if (db == null) {
            Log.e(TAG, "DbImpl must not be null");
        }
        mContext = context;
        setDb(db);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new ConnectionChangerReceiver();
        mContext.registerReceiver(receiver, filter);

    }

    //与init相对应,release后需重新init
    public void release() {
        mContext.unregisterReceiver(receiver);
        mContext = null;
        this.db = null;
        mInstance = null;
    }

    public void setDb(OffDbImpl db) {
        this.db = db;
    }

    public void add(Map<String, String> param, BaseBean scoure, String url,
                    OffCallback callback, ImplOffResponse implOffResponse) {
        if (this.db == null) {
            return;
        }
        //生成修改后的数据
        for (Map.Entry<String, String> entry : param.entrySet()) {
            editScoure(scoure, entry.getKey(), entry.getValue());
        }
        OffResponse response = new OffResponse();
        response.setData(JSON.toJSONString(scoure));
        callback.success(response);
        //构造请求数据

        RequestBean requestBean = new RequestBean();
        requestBean.setUrl(url);
        requestBean.setClassName(scoure.getClass().getName());
        requestBean.setParams(JSON.toJSONString(param));
        requestBean.setCreatedTime(String.valueOf(System.currentTimeMillis()));
        requestBean.setImplOffResponseName(implOffResponse.getClass().getName());

        //保存请求
        db.save(requestBean);
        readyToSend();
    }

    private List list;

    private void readyToSend() {
        if (!networkState) {
            Log.e(TAG, "no network!");
            list = null;
            return;
        }
        list = db.queryAll(mInstance);
        //还原发送请求
        sendOffRequest();
    }

    private void sendOffRequest() {
        if (list != null && list.size() > 0) {
            if (list.get(0) instanceof RequestBean) {
                commit((RequestBean) list.get(0));
            }
        }
    }

    private void commit(final RequestBean request) {
        Map<String, Object> params = JSON.parseObject(request.getParams(), Map.class);
        String url = request.getUrl();

        VolleyHttpClient.getInstance().postSign(mContext, url, params, new VolleyCallback() {
            @Override
            public void onRequestSuccess(VolleyResponse response) {
                super.onRequestSuccess(response);
                String className = request.getClassName();
                String implName = request.getImplOffResponseName();
                try {
                    Object result = JSON.parseObject(response.getData(), Class.forName(className));
                    Class<?> implClass = Class.forName(implName);
                    Object impl = implClass.newInstance();

                    Method method = implClass.getDeclaredMethod("handleResponse", Object.class);
                    method.invoke(impl, result);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                list.remove(0);
                MainActivity.liteOrm.delete(request);
                sendOffRequest();
            }

            @Override
            public void onRequestFail(int code, String msg) {
                super.onRequestFail(code, msg);
                Log.e("volloy-fail", msg);
                sendOffRequest();
            }

            @Override
            public void onRequestError(int code, String msg) {
                super.onRequestError(code, msg);
                Log.e("volloy-error", msg);
                sendOffRequest();
            }
        });
    }

    private BaseBean editScoure(BaseBean bean, String key, String value) {
        try {
            Field mField = bean.getClass().getDeclaredField(key);
            if (mField != null) {
                mField.setAccessible(true);
                mField.set(bean, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private boolean networkState;

    public void setNetwordState(boolean state) {
        this.networkState = state;
        readyToSend();
    }

    class ConnectionChangerReceiver extends BroadcastReceiver {

        private final String TAG = ConnectionChangerReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Network state change");

            boolean success;
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            success = connectivityManager.getActiveNetworkInfo().isAvailable();
            OffRequest.getInstance().setNetwordState(success);
        }
    }
}


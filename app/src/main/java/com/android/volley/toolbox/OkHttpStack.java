package com.android.volley.toolbox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.internal.huc.OkHttpURLConnection;

/**
 * OkHttpStack
 * Created by zjj on 2016/7/18.
 */
public class OkHttpStack extends HurlStack {
    private OkHttpClient okHttpClient;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        OkHttpClient copy = okHttpClient.newBuilder()
                .proxy(okHttpClient.proxy())
                .build();

        return new OkHttpURLConnection(url, copy, null);
    }
}

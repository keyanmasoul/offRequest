package com.zjj.http;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by zjj on 2016/5/4.
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {
    private Request request;

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return HttpUriConnectionUtil.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception) {
            request.getCallback().onFailure((Exception) o);
        } else {
            request.getCallback().onSuccess((String) o);
        }

    }
}

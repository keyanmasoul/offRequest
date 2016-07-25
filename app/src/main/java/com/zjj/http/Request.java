package com.zjj.http;

import java.util.Map;

/**
 * Created by zjj on 2016/5/4.
 */
public class Request {

    private String url;
    private String content;
    private Map<String, String> headers;

    private ICallback callback;

    public ICallback getCallback() {
        return callback;
    }

    public void setCallback(ICallback callback) {
        this.callback = callback;
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public Request(String url) {
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    private RequestMethod method;

    public enum RequestMethod {
        GET, POST, PUT, DELET
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}

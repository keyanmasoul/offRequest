package com.zjj.http.volley;



import java.io.Serializable;

/**
 * Created by zjj on 2016/3/2.
 */
public class VolleyResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int SUCCESS = 0;// 代表成功
    public static final int FAIL = 1;// 代表失败
    private int errorCode = 0;// 定义状态吗
    private String errorMessge;// 提示信息
    private String data;// 数据内容
    private String moreUrl;// 加载更多URL
    private String jsonData;// JSON数据
    private int total;// 总数

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public String getData() {
        return data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode != null ? errorCode : 0;
    }

    public String getErrorMessge() {
        return errorMessge;
    }

    public void setErrorMessge(String errorMessge) {
        this.errorMessge = errorMessge;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return errorCode == SUCCESS;
    }
}

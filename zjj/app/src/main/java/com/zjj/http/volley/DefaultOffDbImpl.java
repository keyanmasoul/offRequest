package com.zjj.http.volley;

import com.zjj.http.MainActivity;
import com.zjj.http.offqueue.OffDbImpl;
import com.zjj.http.offqueue.OffRequest;
import com.zjj.http.offqueue.RequestBean;

import java.util.List;

/**
 * DefaultOffDbImpl
 * Created by zjj on 2016/7/20.
 */
public class DefaultOffDbImpl implements OffDbImpl<RequestBean> {
    @Override
    public void save(RequestBean requestBean) {
        MainActivity.liteOrm.save(requestBean);
    }

    @Override
    public void delete(RequestBean requestBean) {

    }

    @Override
    public List queryAll(OffRequest offRequest) {
        return MainActivity.liteOrm.query(RequestBean.class);
    }

}

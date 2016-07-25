package com.zjj.http.offqueue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjj on 2016/7/20.
 */
public interface OffDbImpl<T> extends Serializable {
    void save(T t);

    void delete(T t);

    List<T> queryAll(OffRequest offRequest);
}

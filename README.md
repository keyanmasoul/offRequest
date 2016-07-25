OffRequest
================
离线请求提交最简版

----
使用方法
----

## 1 初始化
```java
OffRequest.getInstance().init(this);
```
## 2 数据库实现
```java
public class DefaultOffDbImpl implements OffDbImpl<RequestBean> {
    @Override
    public void save(RequestBean requestBean) {

    }

    @Override
    public void delete(RequestBean requestBean) {

    }

    @Override
    public List queryAll(OffRequest offRequest) {

    }

}

OffRequest.getInstance().setDb(new DefaultOffDbImpl());
```

## 3 添加请求(请求参数,数据源,请求url,fakeCallback,ServerResponse实现)
```java
OffRequest.getInstance().add(params, user, url, new OffCallback() {
            @Override
            public void success(OffResponse response) {
                
            }
        }, new UserOffResponseImpl());
		
public class UserOffResponseImpl implements ImplOffResponse {
    @Override
    public void handleResponse(Object o) {

    }
}
```
----
备注
----
简单的离线网络请求实现,复杂得多部请求需自行实现.
下个版本:网络层剥离


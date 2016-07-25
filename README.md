================
OffRequest  离线请求提交最简版
================


使用方法


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

## 3 添加请求
```java
//请求参数,数据源,请求url,fakeCallback,ServerResponse实现
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

原理
----
离线添加请求时,通过传入的参数和数据源构造FakeData返回给用户.同时保存数据源类型,请求参数和回调实现类.在网络恢复时,读取请求数据逐条发送.
数据库接口和服务器应答回调接口需自行实现


备注
----
简单的离线网络请求实现,复杂得多部请求需自行实现.
下个版本:网络状态判断,网络层剥离


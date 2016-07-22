package com.zjj.http;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.litesuits.orm.LiteOrm;
import com.zjj.http.offqueue.OffCallback;
import com.zjj.http.offqueue.OffRequest;
import com.zjj.http.offqueue.OffResponse;
import com.zjj.http.volley.DefaultOffDbImpl;
import com.zjj.http.volley.VolleyCallback;
import com.zjj.http.volley.VolleyHttpClient;
import com.zjj.http.volley.VolleyRequestSign;
import com.zjj.http.volley.VolleyResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity implements IHandlerDo {

    private User user;
    public static LiteOrm liteOrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button login = (Button) findViewById(R.id.tv_login);
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toLogin();

                }
            });
        }
        Button edit = (Button) findViewById(R.id.tv_edit);
        if (edit != null)
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user == null) {
                        return;
                    }
//                    toEidt();
                    //syncUserData();
                    JCFullScreenActivity.startActivity(MainActivity.this,
                            "http://192.168.1.19:8080/html/video_20160722_110411.mp4",
                            JCVideoPlayerStandard.class,
                            "test");
                }
            });
        OffRequest.getInstance().init(this);
        OffRequest.getInstance().setDb(new DefaultOffDbImpl());
        WeakHandler handler = new WeakHandler(this);


        ActivityManager activityManager = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = activityManager.getMemoryClass();

        Log.e("Use Memory==", Runtime.getRuntime().maxMemory() + "");

        //观察者(new)
        Subscriber<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("=======我是观察者 Observer 的 onCompleted 事件 ");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("=======我是观察者 Observer 的 onError 事件，打印参数s：" +
                        e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println("=======我是观察者 Observer 的 onNext 事件，打印参数s：" + s);
            }
        };

        //被观察者(create)
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("onNext Fist");
                subscriber.onNext("onNext Second");
                subscriber.onNext("onNext Third");
                subscriber.onStart();
                subscriber.onCompleted();
            }
        });

        //被观察者 订阅 观察者
        observable.subscribe(observer);

        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.player);
        jcVideoPlayerStandard.setUp("http://192.168.1.19:8080/html/video_20160722_110411.mp4", "");


    }

    private void toEidt() {
        final Map<String, String> params = new LinkedHashMap<>();
        params.put("nickname", "Success");
        params.put("profileId", "2");

        String url = "http://pf.8jiong.cn/user/updateProfile2";
        OffRequest.getInstance().add(params, user, url, new OffCallback() {
            @Override
            public void success(OffResponse response) {
                User user = JSON.parseObject(response.getData(), User.class);
                UserManager.getInstance().setUser(user);
                setUserInfo();
            }
        }, new UserOffResponseImpl());
    }

    private void toLogin() {
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("id", "130130");
        params.put("password", VolleyRequestSign.getMd5("123456"));
        String url = "http://pf.8jiong.cn/user/login";
        VolleyHttpClient.getInstance().post(this, url, params, new VolleyCallback() {
            @Override
            public void onRequestSuccess(VolleyResponse response) {
                super.onRequestSuccess(response);
                Log.e("User==", response.getData());
                user = JSON.parseObject(response.getData(), User.class);
                UserManager.getInstance().setUser(user);
                setUserInfo();
                if (liteOrm == null) {
                    liteOrm = LiteOrm.newSingleInstance(MainActivity.this, user.getUserId() + ".db");
                    liteOrm.setDebugged(true);
                }
            }

            @Override
            public void onRequestFail(int code, String msg) {
                super.onRequestFail(code, msg);
            }

            @Override
            public void onRequestError(int code, String msg) {
                super.onRequestError(code, msg);
            }
        });
    }

    private void setUserInfo() {
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        assert tvName != null;
        tvName.setText(UserManager.getInstance().getUser().getNickname());
    }

    private void test() {
        String url = "http://www.baidu.com";
        Request request = new Request(url, Request.RequestMethod.GET);
        request.setCallback(new ICallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("http---", result);
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        RequestTask task = new RequestTask(request);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void syncUserData() {
        List<String> list = new ArrayList<>();
        list.add("13799913375");
        list.add("13950397572");
        final Map<String, Object> params = new LinkedHashMap<>();
        params.put("allInit", "true");
        params.put("phones", list);
        String url = "http://pf.8jiong.cn/user/syncUserData";

        VolleyHttpClient.getInstance().postSign(this, url, params, new VolleyCallback() {
            @Override
            public void onRequestSuccess(VolleyResponse response) {
                super.onRequestSuccess(response);
                Log.e("user", response.getData());
            }

            @Override
            public void onRequestFail(int code, String msg) {
                super.onRequestFail(code, msg);
            }
        });
    }

    @Override
    public void doUI() {

    }
}

package com.zjj.http;


import java.util.List;

/**
 * Created by zjj on 2016/3/3.
 */
public class UserManager {
    private volatile static UserManager mInstance;
    private User user;

    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class){
                if(mInstance == null){
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    private UserManager() {

    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

}

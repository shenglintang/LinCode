package com.lin.jpushdemo;


import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * 初始化类
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}

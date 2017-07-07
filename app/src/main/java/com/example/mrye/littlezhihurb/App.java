package com.example.mrye.littlezhihurb;

import android.app.Application;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * Application类，在该类做一些初始化工作
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //必须在Application类中的onCreate()方法中执行下面的初始化工作
        BGASwipeBackManager.getInstance().init(this);
    }
}

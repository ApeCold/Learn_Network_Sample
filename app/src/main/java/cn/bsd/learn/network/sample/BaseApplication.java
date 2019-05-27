package cn.bsd.learn.network.sample;

import android.app.Application;

import cn.bsd.learn.network.library.NetworkManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getDefault().init(this);
    }
}

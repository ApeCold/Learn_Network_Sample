package cn.bsd.learn.network.library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import cn.bsd.learn.network.library.core.NetworkCallbackImpl;

public class NetworkManager {
    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault(){
        if(instance==null){
            synchronized (NetworkManager.class){
                if(instance==null){
                    instance=new NetworkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        return application;
    }
    @SuppressLint("MissingPermission")
    public void init(Application application){
        this.application=application;
        //做动态广播注册
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
//        application.registerReceiver(receiver,filter);

        //第二种方式监听，不通过广播
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager cmgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cmgr!=null){
                cmgr.registerNetworkCallback(request,networkCallback);
            }else{

            }
        }
    }

    //注册
    public void registerObserver(Object regsiter){
        receiver.registerObserver(regsiter);
    }

    //移除
    public void unRegisterObserver(Object regsiter){
        receiver.unRegisterObserver(regsiter);
    }
}

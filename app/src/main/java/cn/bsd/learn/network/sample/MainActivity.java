package cn.bsd.learn.network.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.bsd.learn.network.library.NetworkManager;
import cn.bsd.learn.network.library.annotation.Network;
import cn.bsd.learn.network.library.type.NetType;
import cn.bsd.learn.network.library.utils.Constants;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册
//        NetworkManager.getDefault().setListener(this);
        NetworkManager.getDefault().registerObserver(this);
    }

    @Network(netType = NetType.WIFI)
    public void network(NetType netType){
        switch (netType){
            case WIFI:
                Log.e(Constants.LOG_TAG,"MainActivity >>> WIFI");
                break;
            case CMNET:
            case CMWAP:
                //流量网络
                Log.e(Constants.LOG_TAG,"MainActivity >>> " +netType.name());
                break;
            case NONE:
                //没哟网络
                Log.e(Constants.LOG_TAG,"MainActivity >>> 没有网络");
                break;
        }
    }

    @Network(netType = NetType.AUTO)
    private void abc(NetType netType){
        Log.e(Constants.LOG_TAG,"MainActivity222222 >>> " +netType.name());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
    }
}

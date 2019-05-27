package cn.bsd.learn.network.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.bsd.learn.network.library.NetworkManager;
import cn.bsd.learn.network.library.type.NetType;

public class NetWorkUtils {
    /**
     * 网络是否可用
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connMgr==null)return false;
        //返回所有网络信息
        NetworkInfo[] info = connMgr.getAllNetworkInfo();
        if(info!=null){
            for (NetworkInfo anInfo : info) {
                if(anInfo.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前的网络类型：-1：没有网络，1:WIFI网络，2:wap网络，3:net网络
     */
    @SuppressLint("MissingPermission")
    public static NetType getNetType(){
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connMgr==null)return NetType.NONE;
        //获取当前激活的网络连接信息
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo==null){
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE){
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                return NetType.CMNET;
            }else{
                return NetType.CMWAP;
            }
        }else if(nType == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
    /**
     * 打开网络设置界面
     */
    public static void openSetting(Context context,int requestCode){
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.setting.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        ((Activity) context).startActivityForResult(intent,requestCode);
    }
}

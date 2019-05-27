package cn.bsd.learn.network.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bsd.learn.network.library.annotation.Network;
import cn.bsd.learn.network.library.bean.MethodManager;
import cn.bsd.learn.network.library.type.NetType;
import cn.bsd.learn.network.library.utils.Constants;
import cn.bsd.learn.network.library.utils.NetWorkUtils;

//广播
public class NetStateReceiver extends BroadcastReceiver {
    private NetType netType;
    //key:MainActivity value:MainActivity注解的方法
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        //初始化网络
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent ==null||intent.getAction()==null){
            Log.e(Constants.LOG_TAG,"异常......");
            return;
        }

        //处理广播事件
        if(intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)){
            Log.e(Constants.LOG_TAG,"网络发生改变");
            netType = NetWorkUtils.getNetType();
            if(NetWorkUtils.isNetworkAvailable()){//所有能联网的方式循环判断
                Log.e(Constants.LOG_TAG,"网络连接成功！");

            }else{
                Log.e(Constants.LOG_TAG,"网络连接失败！");

            }

            //总线：全局通知
            post(netType);
        }
    }

    //同时分发过程
    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        //比如：获取MainActivity对象
        for(Object getter : set){
            List<MethodManager> methodList = networkList.get(getter);
            if (methodList != null) {
                //循环每个方法
                for (MethodManager methodManager : methodList) {
                    //两者参数比较
                    if(methodManager.getType().isAssignableFrom(netType.getClass())){
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager,getter,netType);
                                break;
                            case WIFI:
                                if(netType==NetType.WIFI||netType==NetType.NONE){
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case CMWAP:
                                if(netType==NetType.CMWAP||netType==NetType.NONE){
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case CMNET:
                                if(netType==NetType.CMNET||netType==NetType.NONE){
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager,Object getter,NetType netType){
        try {
            //在MainActivity中执行方法，且参数值为netType
            methodManager.getMethod().invoke(getter,netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将应用中所有Activity注册了网络监听的方法，添加到集合
    //key:MainActivity value:MainActivity注解的方法
    public void registerObserver(Object regsiter) {
        //MainActivity所有网络监听注解方法
        List<MethodManager> methodList = networkList.get(regsiter);
        if (methodList == null) {
            //开始添加方法
            methodList = findAnnotationMethod(regsiter);
            networkList.put(regsiter,methodList);
        }
    }

    //通过放射技术，从注解中找到方法，添加到集合
    private List<MethodManager> findAnnotationMethod(Object regsiter) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = regsiter.getClass();
        //获取MainActivity中所有方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //获取方法的注解
            Network network = method.getAnnotation(Network.class);
            if(network==null){
                continue;
            }
            //方法的参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length!=1){
                throw new RuntimeException(method.getName()+"方法有且只有一个参数");
            }
            //过滤方法完成,添加到集合（不完整校验）
            MethodManager manager = new MethodManager(parameterTypes[0],network.netType(),method);
            methodList.add(manager);
        }
        return methodList;
    }

    public void unRegisterObserver(Object regsiter) {
        if(!networkList.isEmpty()){
            networkList.remove(regsiter);
        }
        Log.e(Constants.LOG_TAG,regsiter.getClass().getName()+"注销成功");

        //应用退出时
//        if(!networkList.isEmpty()){
//            networkList.clear();
//        }
//
//        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
//        networkList=null;
//        Log.e(Constants.LOG_TAG,"注销ALL成功");
    }
}

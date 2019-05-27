package cn.bsd.learn.network.library.listener;

import cn.bsd.learn.network.library.type.NetType;

//网络监听接口
public interface NetChangeObserver {
    void onConnect(NetType type);

    //没有网络连接
    void onDisConnect();
}

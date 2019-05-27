package cn.bsd.learn.network.library.type;

//网络类型
public enum NetType {
    //只要有网络，包括：WIFI/GPRS
    AUTO,
    //WIFI网络
    WIFI,
    //主要是PC/笔记本电脑/PDA设备 上网
    CMNET,
    //手机上网
    CMWAP,
    //没有网络
    NONE;
}

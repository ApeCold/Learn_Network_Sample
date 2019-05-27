package cn.bsd.learn.network.library.bean;

import java.lang.reflect.Method;

import cn.bsd.learn.network.library.type.NetType;

//保存符合要求的网络监听注解方法
public class MethodManager {
    //参数的类型，NetType netType
    private Class<?> type;

    //网络类型，（netType = NetType.WIFI）
    private NetType netType;

    //需要执行的方法（自动执行） method.invoke()
    //network（NetType netType）{}
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

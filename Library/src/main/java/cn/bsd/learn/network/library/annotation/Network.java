package cn.bsd.learn.network.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.bsd.learn.network.library.type.NetType;

@Target(ElementType.METHOD)//作用在方法之上
@Retention(RetentionPolicy.RUNTIME)//jvm运行时，通过反射获取该注解的值
public @interface Network {
    NetType netType() default NetType.AUTO;
}

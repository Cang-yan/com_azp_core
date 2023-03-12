package com.azp.core.sys.util;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2022/4/27 1:57 下午
 **/
public class ProxyUtils {

    private static final Proxy PROXY1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.29.147.11", 8080));

    private static final Proxy PROXY2 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.29.147.194", 8080));

    public static Proxy getProdProxy() {
        if (System.currentTimeMillis() % 2 == 0)
            return PROXY1;
        else return PROXY2;
    }

    public static Proxy getDevProxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(8080));
    }
}

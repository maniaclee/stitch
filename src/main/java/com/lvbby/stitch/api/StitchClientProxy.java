package com.lvbby.stitch.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by lipeng on 16/8/30.
 */
public class StitchClientProxy {

    public StitchClient proxy(List<KeyDecorator> keyDecorators, StitchClient stitchClient) {
        return (StitchClient) Proxy.newProxyInstance(StitchClientProxy.class.getClassLoader(), new Class[]{StitchClient.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().startsWith("get")) {
                    String key = (String) args[0];
                    for (KeyDecorator keyDecorator : keyDecorators) {
                        key = keyDecorator.decorateKey(key);
                    }
                    args[0] = key;
                }
                return method.invoke(stitchClient, args);
            }
        });
    }

}

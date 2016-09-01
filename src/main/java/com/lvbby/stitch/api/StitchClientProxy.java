package com.lvbby.stitch.api;

import com.google.common.collect.Lists;
import com.lvbby.stitch.decorator.EnvDecorator;
import com.lvbby.stitch.decorator.PathDecorator;
import com.lvbby.stitch.env.Env;
import com.lvbby.stitch.env.PropertiesEnvironment;
import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.kv.KvService;
import com.lvbby.stitch.kv.KvServiceCache;
import com.lvbby.stitch.kv.impl.ZkKvService;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lipeng on 16/8/30.
 */
public class StitchClientProxy {


    /***
     * create StitchClient proxy
     */
    public static StitchClient proxy(List<KeyDecorator> keyDecorators, StitchClient stitchClient) {
        return (StitchClient) Proxy.newProxyInstance(StitchClientProxy.class.getClassLoader(), new Class[]{StitchClient.class}, (proxy, method, args) -> {
            /** intercept all public and non-static methods, first parameter is the key */
            if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()))
                return method.invoke(stitchClient, args);
            if (args.length > 0 && args[0] instanceof String) {
                String key = StringUtils.trimToNull((String) args[0]);
                for (KeyDecorator keyDecorator : keyDecorators) {
                    key = keyDecorator.decorateKey(key);
                }
                args[0] = key;
            }
            return method.invoke(stitchClient, args);
        });
    }

    public static StitchClient create(String... paths) {
        return createWithEnv(mergePath(paths));
    }

    /***
     * with env added inside
     */
    public static StitchClient createWithEnv(List<KeyDecorator> decorators) {
        List<KeyDecorator> re = Lists.newArrayList();
        re.add(new EnvDecorator(getEnv()));
        re.addAll(decorators);
        return create(re);
    }

    /***
     * basic create method
     */
    public static StitchClient create(List<KeyDecorator> decorators) {
        Env env = new PropertiesEnvironment("/data/config/env.properties");

        KvService zk = new ZkKvService(env.getZkServer());
        KvService kvServiceCache = new KvServiceCache(zk);
        StitchClient defaultStitchClient = new DefaultStitchClient(kvServiceCache);
        return proxy(decorators, defaultStitchClient);
    }

    public static Env getEnv() {
        return new PropertiesEnvironment("/data/config/env.properties");
    }

    private static List<KeyDecorator> mergePath(String... paths) {
        LinkedList<KeyDecorator> decorators = Lists.newLinkedList();
        if (paths != null && paths.length > 0)
            for (int i = paths.length - 1; i >= 0; i--) {
                decorators.add(new PathDecorator(paths[i]));
            }
        return decorators;
    }


}

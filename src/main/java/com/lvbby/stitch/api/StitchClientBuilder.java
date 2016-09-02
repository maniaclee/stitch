package com.lvbby.stitch.api;

import com.google.common.collect.Lists;
import com.lvbby.stitch.decorator.EnvDecorator;
import com.lvbby.stitch.decorator.PathDecorator;
import com.lvbby.stitch.env.Env;
import com.lvbby.stitch.env.PropertiesEnvironment;
import com.lvbby.stitch.exception.StitchException;
import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.kv.KvService;
import com.lvbby.stitch.kv.KvServiceCache;
import com.lvbby.stitch.kv.impl.ZkKvService;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by peng on 16/9/2.
 */
public class StitchClientBuilder {
    public static final String envPropertiesPath = "/data/config/env.properties";

    List<KeyDecorator> keyDecorators = Lists.newLinkedList();
    private String zkServer;
    private Env env;

    public static StitchClientBuilder instance() {
        return new StitchClientBuilder();
    }

    public static StitchClient defaultClient(String... paths) {
        return instance()
                .fromEnv()
                .decorateKeyWithEnv()
                .path(paths)
                .build()
                ;
    }

    public StitchClientBuilder fromEnv() {
        return fromEnv(envPropertiesPath);
    }

    public StitchClientBuilder fromEnv(String envProperties) {
        env = new PropertiesEnvironment(envProperties);
        zkServer = env.getZkServer();
        return this;
    }

    public StitchClientBuilder zkServer(String zkServer) {
        this.zkServer = zkServer;
        return this;
    }

    public StitchClientBuilder decorateKeyWithEnv() {
        if (env == null)
            throw new StitchException("env must be given first");
        keyDecorators.add(new EnvDecorator(env));
        return this;
    }

    public StitchClientBuilder path(String... root) {
        keyDecorators.addAll(mergePath(root));
        return this;
    }

    public static StitchClientBuilder withPath(String... root) {
        return instance().path(root);
    }

    public StitchClientBuilder withKeyDecorators(KeyDecorator... decorators) {
        if (decorators != null && decorators.length > 0)
            keyDecorators.addAll(Arrays.asList(decorators));
        return this;
    }

    public StitchClient build() {
        KvService zk = new ZkKvService(zkServer);
        KvService kvServiceCache = new KvServiceCache(zk);
        StitchClient defaultStitchClient = new DefaultStitchClient(kvServiceCache);
        return proxy(keyDecorators, defaultStitchClient);
    }

    private static List<KeyDecorator> mergePath(String... paths) {
        LinkedList<KeyDecorator> decorators = Lists.newLinkedList();
        if (paths != null && paths.length > 0)
            for (int i = paths.length - 1; i >= 0; i--) {
                decorators.add(new PathDecorator(paths[i]));
            }
        return decorators;
    }

    public static StitchClient proxy(List<KeyDecorator> keyDecorators, StitchClient stitchClient) {
        return (StitchClient) Proxy.newProxyInstance(StitchClientBuilder.class.getClassLoader(), new Class[]{StitchClient.class}, (proxy, method, args) -> {
            /** intercept all public and non-static methods, first parameter is the key */
            if (Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
                if (args.length > 0 && args[0] instanceof String) {
                    String key = StringUtils.trimToNull((String) args[0]);
                    for (KeyDecorator keyDecorator : keyDecorators) {
                        key = keyDecorator.decorateKey(key);
                    }
                    args[0] = key;
                }
            }
            return method.invoke(stitchClient, args);
        });
    }
}

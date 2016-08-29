package com.lvbby.stitch.api;

import com.lvbby.stitch.KvServiceCache;
import com.lvbby.stitch.kv.PropertyService;
import com.lvbby.stitch.kv.ZkKvService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by peng on 16/8/29.
 */
public class Stitch {

    private static KvServiceCache kvServiceCache;

    public static void zookeeper(String zookeeper) {
        init(new ZkKvService(zookeeper));
    }

    public static void properties(InputStream properties) {
        try {
            init(PropertyService.from(properties));
        } catch (IOException e) {
            throw new RuntimeException("failed to init stitch from properties:", e);
        }
    }


    private static void init(KvService kvService) {
        kvService.init();
        kvServiceCache = new KvServiceCache(kvService);
    }

    public static String get(String s, String defaultValue) {
        return Optional.of(trimToNull(s)).map(v -> kvServiceCache.get(v)).orElse(defaultValue);
    }

    public static String get(String s) {
        return get(s, null);
    }

    public static boolean getBoolean(String s) {
        return getBoolean(s, false);
    }

    public static boolean getBoolean(String s, boolean defaultValue) {
        return Optional.of(get(s)).map(v -> Boolean.parseBoolean(v.toLowerCase())).orElse(defaultValue);
    }

    public static int getInt(String s) {
        return getInt(s, 0);
    }

    public static int getInt(String s, int defaultValue) {
        return Optional.of(get(s)).map(v -> Integer.parseInt(v.toLowerCase())).orElse(defaultValue);
    }

    private static String trimToNull(String s) {
        if (s == null)
            return null;
        String trim = s.trim();
        return trim.length() == 0 ? null : trim;
    }

}

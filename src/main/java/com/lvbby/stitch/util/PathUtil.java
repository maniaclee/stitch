package com.lvbby.stitch.util;

/**
 * Created by lipeng on 16/8/30.
 */
public class PathUtil {
    public static String keyAndKey(String key, String... keyExtra) {
        if (key != null && keyExtra != null && keyExtra.length > 0) {
            for (String s : keyExtra) {
                key = key + "." + s;
            }
        }
        return key;
    }

    public static String rootAndKey(String root, String keyExtra) {
        if (root != null && keyExtra != null) {
            keyExtra = root + "/" + keyExtra;
        }
        return keyExtra;
    }
}

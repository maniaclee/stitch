package com.lvbby.stitch.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 16/8/30.
 */
public class PathUtil {
    public static String concatKey(String... keyExtra) {
        if (keyExtra != null && keyExtra.length > 0) {
            return Arrays.stream(keyExtra).map(PathUtil::formatKey).collect(Collectors.joining("."));
        }
        return "";
    }

    /***
     * remove the . before and after the key
     *
     * @param key
     * @return
     */
    public static String formatKey(String key) {
        String s = StringUtils.trimToEmpty(key);
        if (s.startsWith("."))
            s = s.length() == 1 ? "" : s.substring(1);
        if (s.endsWith("."))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    /***
     * format path to /asdfa/sdf
     *
     * @param s
     * @return
     */
    public static String formatPath(String s) {
        s = StringUtils.trimToEmpty(s);
        if (!s.startsWith("/"))
            s = "/" + s;
        if (s.endsWith("/"))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    public static String concatPath(String... paths) {
        if (paths == null || paths.length == 0)
            return "";
        return Arrays.stream(paths).map(PathUtil::formatPath).collect(Collectors.joining());
    }
}

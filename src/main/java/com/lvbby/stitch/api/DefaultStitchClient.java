package com.lvbby.stitch.api;

import com.lvbby.stitch.kv.KvService;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Created by peng on 16/8/29.
 * the api for manipulate kv
 */
public class DefaultStitchClient implements StitchClient {

    private KvService kvService;

    public DefaultStitchClient(KvService kvService) {
        kvService.init();
        this.kvService = kvService;
    }

    @Override
    public String get(String s, String defaultValue) {
        return Optional.of(trimToNull(s)).map(v -> kvService.get(v)).orElse(defaultValue);
    }

    @Override
    public String get(String s) {
        return get(s, null);
    }

    @Override
    public boolean getBoolean(String s) {
        return getBoolean(s, false);
    }

    @Override
    public boolean getBoolean(String s, boolean defaultValue) {
        return Optional.of(get(s)).map(v -> Boolean.parseBoolean(v.toLowerCase())).orElse(defaultValue);
    }

    @Override
    public int getInt(String s) {
        return getInt(s, 0);
    }

    @Override
    public int getInt(String s, int defaultValue) {
        return Optional.of(get(s)).map(v -> Integer.parseInt(v.toLowerCase())).orElse(defaultValue);
    }

    @Override
    public void set(String s, String value) {
        value = StringUtils.trimToEmpty(value);
        if (s != null)
            kvService.set(s, value);
    }

    private String trimToNull(String s) {
        if (s == null)
            return null;
        String trim = s.trim();
        return trim.length() == 0 ? null : trim;
    }

}

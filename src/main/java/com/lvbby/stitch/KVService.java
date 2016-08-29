package com.lvbby.stitch;

/**
 * Created by lipeng on 16/8/29.
 * kv service , without cache
 */
public interface KvService extends ListenerSupport {

    void init();

    String get(String key);
}

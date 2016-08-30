package com.lvbby.stitch.kv;

/**
 * Created by peng on 16/8/29.
 */
public interface KvService extends ListenerSupport {

    void init();

    String get(String key);
}

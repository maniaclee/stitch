package com.lvbby.stitch.api;

/**
 * Created by peng on 16/8/29.
 */
public interface KvService extends ListenerSupport {

    void init();

    String get(String key);
}

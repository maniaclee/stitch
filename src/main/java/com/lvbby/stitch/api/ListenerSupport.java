package com.lvbby.stitch.api;

/**
 * Created by lipeng on 16/8/29.
 */
public interface ListenerSupport {

    void addListener(EventListener eventListener);

    void removeListener(EventListener eventListener);
}

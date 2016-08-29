package com.lvbby.stitch;

import com.google.common.collect.Lists;
import com.lvbby.stitch.api.EventListener;
import com.lvbby.stitch.api.KvService;

import java.util.List;

/**
 * Created by lipeng on 16/8/29.
 */
public abstract class AbstractKvService implements KvService {
    private List<EventListener> listeners = Lists.newArrayList();

    public void addListener(EventListener eventListener) {
        if (eventListener != null)
            listeners.add(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        listeners.remove(eventListener);
    }
}

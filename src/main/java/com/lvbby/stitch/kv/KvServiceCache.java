package com.lvbby.stitch.kv;

import com.lvbby.stitch.kv.EventListener;
import com.lvbby.stitch.kv.KvService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peng on 16/8/29.
 */
public class KvServiceCache implements KvService {

    private ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();
    private KvService kvService;

    public KvServiceCache(KvService kvService) {
        this.kvService = kvService;
    }

    @Override
    public void init() {
        kvService.init();
        /** when update/delete event received , update the cache*/
        kvService.addListener(event -> cache.put(event.getKey(), event.getValue()));
    }

    @Override
    public String get(String key) {
        String s = cache.get(key);
        if (s == null) {
            s = kvService.get(key);
            cache.put(key, s);
        }
        return s;
    }

    @Override
    public void addListener(EventListener eventListener) {
        kvService.addListener(eventListener);
    }

    @Override
    public void removeListener(EventListener eventListener) {
        kvService.removeListener(eventListener);
    }
}

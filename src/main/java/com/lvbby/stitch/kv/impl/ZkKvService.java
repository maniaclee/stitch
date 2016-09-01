package com.lvbby.stitch.kv.impl;

import com.lvbby.stitch.kv.EventListener;
import com.lvbby.stitch.kv.KvEvent;
import com.lvbby.stitch.kv.KvService;
import com.lvbby.stitch.util.ZookeeperDelegate;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by lipeng on 16/8/29.
 */
public class ZkKvService extends AbstractKvService implements KvService {
    private static Logger logger = LoggerFactory.getLogger(ZkKvService.class);

    private CuratorFramework client;
    private ConcurrentMap<String, String> keyValueMap = new ConcurrentHashMap<>();

    private String zkAddress = "localhost:2181";
    private ZookeeperDelegate zookeeperDelegate;

    public ZkKvService(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public void init() {
        client = CuratorFrameworkFactory.newClient(
                zkAddress, 60 * 1000, 30 * 1000, new RetryNTimes(3, 1000));
        zookeeperDelegate = new ZookeeperDelegate(client);
        client.getCuratorListenable().addListener((curatorFramework, event) -> {
            if (event.getType() == CuratorEventType.WATCHED) {
                WatchedEvent watchedEvent = event.getWatchedEvent();
                if (watchedEvent.getPath() != null) {
                    try {
                        process(watchedEvent);
                    } catch (Exception e) {
                        logger.error("failed to process when zookeeper value changed:", e);
                    }
                }
            }
        });
        client.getConnectionStateListenable().addListener((curatorFramework, newState) -> {
            if (newState == ConnectionState.RECONNECTED) {
                try {
                    sync();
                } catch (Exception e) {
                    logger.error("failed to sync when zookeeper reconnected: ", e);
                }
            }
        });
        client.start();
    }

    public String get(String key) {
        try {
            byte[] dataWatched = zookeeperDelegate.getDataWatched(key);
            String s = dataWatched == null ? null : new String(dataWatched, "utf-8");
            keyValueMap.put(key, s);
            return s;
        } catch (Exception e) {
            logger.error("failed to get key: " + String.valueOf(key), e);
        }
        return null;
    }

    @Override
    public void set(String key, String value) {
        zookeeperDelegate.set(key, value);
        keyValueMap.put(key, value);

    }


    private void sync() {
        for (String key : keyValueMap.keySet()) {
            String newValue = get(key);
            String oldValue = keyValueMap.get(key);
            /** sync status */
            if (newValue != null && !Objects.equals(newValue, oldValue)) {
                processKvChanged(key);
            }
        }
    }


    private void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        if (watchedEvent.getType() == Watcher.Event.EventType.NodeCreated || watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
            processKvChanged(path);
        } else if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) {
            processKvDeleted(path);
        }
    }

    private void processKvDeleted(String path) {
        KvEvent kvEvent = new KvEvent();
        kvEvent.setType(KvEvent.type_kv_deleted);
        kvEvent.setKey(path);
        logger.info("value deleted key:{}", path);
        notifyListeners(kvEvent);
    }

    private void processKvChanged(String path) {
        KvEvent kvEvent = new KvEvent();
        kvEvent.setType(KvEvent.type_kv_deleted);
        kvEvent.setKey(path);
        kvEvent.setValue(get(path));
        logger.info("value changed , before[{}] , after[{}]", keyValueMap.get(path), kvEvent.getValue());
        notifyListeners(kvEvent);
    }

    private void notifyListeners(KvEvent kvEvent) {
        for (EventListener listener : listeners) {
            listener.changed(kvEvent);
        }
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

}

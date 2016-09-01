package com.lvbby.stitch.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.data.Stat;

/**
 * Created by lipeng on 16/8/29.
 */

public class ZookeeperDelegate {

    private CuratorFramework curatorClient;

    public ZookeeperDelegate(CuratorFramework curatorClient) {
        this.curatorClient = curatorClient;
    }

    public void watch(String path) throws Exception {
        curatorClient.checkExists().watched().forPath(path);
    }

    public boolean exists(String path) throws Exception {
        Stat stat = curatorClient.checkExists().forPath(path);
        return stat != null;
    }

    public boolean existsWatched(String path) throws Exception {
        Stat stat = curatorClient.checkExists().watched().forPath(path);
        return stat != null;
    }

    public byte[] getData(String path) throws Exception {
        try {
            byte[] data = curatorClient.getData().forPath(path);
            return data;
        } catch (NoNodeException e) {
            return null;
        }
    }

    public byte[] getDataWatched(String path) throws Exception {
        try {
            byte[] data = curatorClient.getData().watched().forPath(path);
            return data;
        } catch (NoNodeException e) {
            curatorClient.checkExists().watched().forPath(path);
            return null;
        }
    }

    public void set(String key, String value) {
        try {
            curatorClient.create().creatingParentsIfNeeded().forPath(key, value.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

package com.lvbby.stitch.zk;

import com.lvbby.stitch.AbstractKvService;
import com.lvbby.stitch.Kver;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by lipeng on 16/8/29.
 */
public class ZkKVService extends AbstractKvService implements Kver {
    private CuratorFramework client;
    private String zkAddress = "localhost:2181";
    private ZookeeperDelegate zookeeperDelegate;

    public void init() {
        client = CuratorFrameworkFactory.newClient(
                zkAddress, 60 * 1000, 30 * 1000, new RetryNTimes(3, 1000));
        zookeeperDelegate = new ZookeeperDelegate(client);
        client.start();
    }

    public String get(String key) {
        byte[] dataWatched = new byte[0];
        try {
            dataWatched = zookeeperDelegate.getDataWatched(key);
            return dataWatched == null ? null : new String(dataWatched, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }
}

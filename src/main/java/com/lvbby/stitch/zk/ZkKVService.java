package com.lvbby.stitch.zk;

import com.lvbby.stitch.AbstractKvService;
import com.lvbby.stitch.KvService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by lipeng on 16/8/29.
 */
public class ZkKVService extends AbstractKvService implements KvService {
    private CuratorFramework client;
    private String zkAddress;

    public void init() {
        CuratorFramework curatorClient = CuratorFrameworkFactory.newClient(
                zkAddress, 60 * 1000, 30 * 1000, new RetryNTimes(3, 1000));
    }

    public String get(String key) {
        return null;
    }
}

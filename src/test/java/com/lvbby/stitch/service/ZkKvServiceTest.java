package com.lvbby.stitch.service;

import com.lvbby.stitch.api.StitchClient;
import com.lvbby.stitch.api.StitchClientProxy;
import com.lvbby.stitch.kv.impl.ZkKvService;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by peng on 16/8/29.
 */
public class ZkKvServiceTest {

    private ZkKvService zkKvService;

    @Before
    public void init() {
        zkKvService = new ZkKvService("localhost:2181");
        zkKvService.init();
    }

    @Test
    public void get() throws Exception {
        System.out.println(zkKvService.get("/stitch/testkey"));
    }

    @Test
    public void cookie() {
        StitchClient proxy = StitchClientProxy.create("stitch", "app");

        while (true) {
            System.out.println(proxy.get("testkey"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
package com.lvbby.stitch.zk;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by peng on 16/8/29.
 */
public class ZkKVXServiceTest {

    private ZkKVXService zkKVXService;

    @Before
    public void init() {
        zkKVXService = new ZkKVXService();
        zkKVXService.init();
    }

    @Test
    public void get() throws Exception {
        System.out.println(zkKVXService.get("/test/testkey"));
    }

}
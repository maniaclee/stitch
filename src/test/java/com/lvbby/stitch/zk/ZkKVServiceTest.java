package com.lvbby.stitch.zk;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by peng on 16/8/29.
 */
public class ZkKVServiceTest {

    private ZkKVService zkKVService;

    @Before
    public void init() {
        zkKVService = new ZkKVService();
        zkKVService.init();
    }

    @Test
    public void get() throws Exception {
        System.out.println(zkKVService.get("/test/testkey"));
    }

}
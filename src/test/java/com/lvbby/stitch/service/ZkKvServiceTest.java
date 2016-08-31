package com.lvbby.stitch.service;

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

}
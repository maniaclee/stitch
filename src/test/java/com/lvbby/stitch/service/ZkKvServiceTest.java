package com.lvbby.stitch.service;

import com.lvbby.stitch.api.StitchClient;
import com.lvbby.stitch.api.StitchClientBuilder;
import com.lvbby.stitch.kv.impl.ZkKvService;
import com.lvbby.stitch.tool.StitchTool;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
        StitchClient proxy = StitchClientBuilder.defaultClient("config", "labrador");

        while (true) {
            System.out.println(proxy.get("hostUrl"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void builder() {
        StitchClient proxy = StitchClientBuilder.withPath("config", "labrador")
                .fromEnv()
                .decorateKeyWithEnv()
                .build();

        System.out.println(proxy.get("hostUrl"));
    }

    @Test
    public void store() throws IOException {
        StitchClient proxy = StitchClientBuilder
                .withPath("config", "labrador")
                .zkServer("localhost:2181").build();
        StitchTool
                .of(proxy)
                .store("/Users/peng/workspace/github/stitch/src/test/resources/a.properties");
    }

}
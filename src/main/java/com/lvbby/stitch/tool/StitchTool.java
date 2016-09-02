package com.lvbby.stitch.tool;

import com.lvbby.stitch.api.StitchClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by peng on 16/9/1.
 */
public class StitchTool {

    private StitchClient stitchClient;

    private StitchTool(StitchClient stitchClient) {
        this.stitchClient = stitchClient;
    }

    public static StitchTool of(StitchClient stitchClient) {
        return new StitchTool(stitchClient);
    }

    public void store(String propertiesPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(propertiesPath);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        store(properties);
    }

    public void store(Properties properties) {
        for (Object o : properties.keySet()) {
            String key = o.toString();
            String property = properties.getProperty(key);
            stitchClient.set(key, property);
        }
    }
}

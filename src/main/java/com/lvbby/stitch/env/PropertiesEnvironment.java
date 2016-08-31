package com.lvbby.stitch.env;

import com.lvbby.stitch.exception.StitchException;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lipeng on 16/8/31.
 */
public class PropertiesEnvironment implements Env {
    private String env;
    private String zkServer;

    public PropertiesEnvironment(String absolutePath) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(absolutePath));
            this.env = StringUtils.trimToEmpty(properties.getProperty("env"));
            this.zkServer = StringUtils.trimToEmpty(properties.getProperty("zkServer"));
            if (StringUtils.isBlank(env))
                throw new StitchException("can't find env");
            if (StringUtils.isBlank(zkServer))
                throw new StitchException("can't find zkServer");
        } catch (IOException e) {
            throw new StitchException("failed to find environment:", e);
        }
    }

    @Override
    public String getEnv() {
        return env;
    }

    @Override
    public String getZkServer() {
        return zkServer;
    }

}

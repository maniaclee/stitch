package com.lvbby.stitch.decorator;

import com.lvbby.stitch.env.EnvironMent;
import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/30.
 */
public class EnvDecorator implements KeyDecorator {

    private EnvironMent environMent;

    public EnvDecorator(EnvironMent environMent) {
        this.environMent = environMent;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.keyAndKey(key, environMent.getEnv());
    }
}

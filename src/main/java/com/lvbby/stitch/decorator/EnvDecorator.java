package com.lvbby.stitch.decorator;

import com.lvbby.stitch.env.Env;
import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/30.
 */
public class EnvDecorator implements KeyDecorator {

    private Env env;

    public EnvDecorator(Env env) {
        this.env = env;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.concatKey(key, env.getEnv());
    }
}

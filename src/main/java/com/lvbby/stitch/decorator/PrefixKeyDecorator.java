package com.lvbby.stitch.decorator;

import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/31.
 */
public class PrefixKeyDecorator implements KeyDecorator {
    private final String prefix;

    public PrefixKeyDecorator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.concatKey(prefix,key);
    }
}

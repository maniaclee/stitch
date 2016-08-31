package com.lvbby.stitch.decorator;

import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/31.
 */
public class SuffixKeyDecorator implements KeyDecorator {
    private final String suffix;

    public SuffixKeyDecorator(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.concatKey(key,suffix);
    }
}

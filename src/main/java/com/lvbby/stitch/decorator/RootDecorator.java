package com.lvbby.stitch.decorator;

import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/30.
 */
public class RootDecorator implements KeyDecorator {
    private String root;

    public RootDecorator(String root) {
        this.root = root;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.concatPath(root, key);
    }
}

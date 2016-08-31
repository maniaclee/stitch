package com.lvbby.stitch.decorator;

import com.lvbby.stitch.kv.KeyDecorator;
import com.lvbby.stitch.util.PathUtil;

/**
 * Created by lipeng on 16/8/30.
 * add path before the key
 */
public class PathDecorator implements KeyDecorator {
    private String root;

    public PathDecorator(String root) {
        this.root = root;
    }

    @Override
    public String decorateKey(String key) {
        return PathUtil.concatPath(root, key);
    }
}

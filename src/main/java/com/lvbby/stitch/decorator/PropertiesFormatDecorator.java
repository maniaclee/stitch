package com.lvbby.stitch.decorator;

import com.lvbby.stitch.kv.KeyDecorator;

/**
 * Created by lipeng on 16/8/30.
 */
public class PropertiesFormatDecorator implements KeyDecorator {


    @Override
    public String decorateKey(String key) {
        return key.replaceAll("/", ".");
    }
}

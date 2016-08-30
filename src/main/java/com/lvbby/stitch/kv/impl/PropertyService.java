package com.lvbby.stitch.kv.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by peng on 16/8/29.
 */
public class PropertyService extends AbstractKvService {

    private Properties properties;

    public static PropertyService fromFile(File file) throws IOException {
        return from(new FileInputStream(file));
    }

    public static PropertyService from(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        PropertyService propertyService = new PropertyService();
        propertyService.properties = properties;
        return propertyService;
    }

    public static PropertyService fromFile(String file) throws IOException {
        return fromFile(new File(file));
    }

    public static PropertyService fromClassPathResource(String file) throws IOException {
        return from(PropertyService.class.getClassLoader().getResourceAsStream(file));
    }

    @Override
    public void init() {

    }

    @Override
    public String get(String key) {
        return properties.getProperty(key);
    }
}

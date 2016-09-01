package com.lvbby.stitch.api;

/**
 * Created by lipeng on 16/8/30.
 */
public interface StitchClient {
    String get(String s, String defaultValue);

    String get(String s);

    boolean getBoolean(String s);

    boolean getBoolean(String s, boolean defaultValue);

    int getInt(String s);

    int getInt(String s, int defaultValue);

    void set(String s, String value);

}

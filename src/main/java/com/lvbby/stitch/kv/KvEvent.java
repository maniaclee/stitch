package com.lvbby.stitch.kv;

/**
 * Created by lipeng on 16/8/29.
 */
public class KvEvent {
    public static final int type_kv_changed = 0;
    public static final int type_kv_deleted = 1;
    private String key;
    private String value;
    private int type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.lvbby.stitch.exception;

/**
 * Created by lipeng on 16/8/31.
 */
public class StitchException extends RuntimeException {
    public StitchException(String message) {
        super(message);
    }

    public StitchException(String message, Throwable cause) {
        super(message, cause);
    }

    public StitchException(Throwable cause) {
        super(cause);
    }
}

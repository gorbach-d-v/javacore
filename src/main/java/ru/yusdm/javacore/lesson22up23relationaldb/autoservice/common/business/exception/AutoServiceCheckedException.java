package ru.yusdm.javacore.lesson22up23relationaldb.autoservice.common.business.exception;

public abstract class AutoServiceCheckedException extends Exception {

    protected int code;

    public AutoServiceCheckedException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AutoServiceCheckedException(int code, String message, Exception cause) {
        super(message);
        this.code = code;
        initCause(cause);
    }
}

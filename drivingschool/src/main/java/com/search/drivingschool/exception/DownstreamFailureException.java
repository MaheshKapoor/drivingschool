package com.search.drivingschool.exception;

/**
 * Created by d818414 on 22/07/2017.
 */
public class DownstreamFailureException extends Exception {
    private String errorcode;

    public DownstreamFailureException() {
    }

    public DownstreamFailureException(String errorcode, String message) {
        super(message);
        this.errorcode = errorcode;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public DownstreamFailureException(Throwable cause) {
        super(cause);
    }

}

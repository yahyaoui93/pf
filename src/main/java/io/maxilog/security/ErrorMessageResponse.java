package io.maxilog.security;

import java.io.Serializable;

/**
 * Created by mossa on 06/11/2017.
 */
public class ErrorMessageResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String error;
    private final String message;

    public ErrorMessageResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }
    public String getMessage() {
        return message;
    }

}
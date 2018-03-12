package io.maxilog.security;

import java.io.Serializable;

/**
 * Created by mossa on 01/12/2017.
 */
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

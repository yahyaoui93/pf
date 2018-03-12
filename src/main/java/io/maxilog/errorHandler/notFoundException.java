package io.maxilog.errorHandler;

/**
 * Created by mossa on 04/11/2017.
 */
public class notFoundException extends RuntimeException {
    public notFoundException(String msg) {
        super(msg);
    }
}

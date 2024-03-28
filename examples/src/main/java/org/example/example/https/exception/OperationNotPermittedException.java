package org.example.example.https.exception;

import org.example.example.rest.exception.AppException;

public class OperationNotPermittedException extends AppException {
    public OperationNotPermittedException() {
    }

    public OperationNotPermittedException(String message) {
        super(message);
    }

    public OperationNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotPermittedException(Throwable cause) {
        super(cause);
    }

    public OperationNotPermittedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

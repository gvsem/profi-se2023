package org.example.profise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Conflict")
public class ConflictException extends RuntimeException {
    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
}

package com.hemiplegia.server.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameAlreadyInUseException extends IllegalStateException {
    public UsernameAlreadyInUseException(String message) {
        super(message);
    }

    public UsernameAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}

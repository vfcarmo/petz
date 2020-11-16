package com.vfc.petz.domain.exception;

public class EntityBadRequestException extends RuntimeException {
    public EntityBadRequestException(String message) {
        super(message);
    }
}

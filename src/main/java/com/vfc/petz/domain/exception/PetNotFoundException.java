package com.vfc.petz.domain.exception;

import java.util.UUID;

public class PetNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE_TEMPLATE = "Pet not found: %s";

    public PetNotFoundException(UUID petId) {
        super(String.format(MESSAGE_TEMPLATE, petId));
    }
}

package com.vfc.petz.domain.exception;

import java.util.UUID;

public class CustomerNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE_TEMPLATE = "Customer not found: %s";

    public CustomerNotFoundException(UUID customerId) {
        super(String.format(MESSAGE_TEMPLATE, customerId));
    }
}

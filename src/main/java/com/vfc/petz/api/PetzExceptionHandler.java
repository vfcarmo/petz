package com.vfc.petz.api;

import com.vfc.petz.domain.exception.EntityAlreadyExistException;
import com.vfc.petz.domain.exception.EntityBadRequestException;
import com.vfc.petz.domain.exception.EntityNotFoundException;
import com.vfc.petz.domain.exception.SystemException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Log4j2
public class PetzExceptionHandler {

    @ExceptionHandler({HttpMessageConversionException.class, EntityBadRequestException.class, DateTimeParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadRequestException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler({EntityAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleConflictException(EntityAlreadyExistException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ExceptionHandler({SystemException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleGenericException(Exception ex) {
        log.error(ex.getMessage(), ex);
    }
}

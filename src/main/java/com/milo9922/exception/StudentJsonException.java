package com.milo9922.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StudentException extends JsonProcessingException {
    public StudentException(String message) {
        super(message);
        log.error(message);
    }
}

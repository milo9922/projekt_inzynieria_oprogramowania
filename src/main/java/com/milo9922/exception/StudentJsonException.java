package com.milo9922.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StudentJsonException extends JsonProcessingException {
    public StudentJsonException(String message) {
        super(message);
    }
}

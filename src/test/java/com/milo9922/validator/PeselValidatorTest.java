package com.milo9922.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeselValidatorTest {

    private static final PeselValidator peselValidator = new PeselValidator();

    @Test
    void shouldReturnTrueForValidPeselNumber() {
        String pesel = "80082932514";
        boolean isValid = peselValidator.validatePesel(pesel);
        assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseForInvalidPeselNumber() {
        String pesel = "99999999999";
        boolean isValid = peselValidator.validatePesel(pesel);
        assertFalse(isValid);
    }
}

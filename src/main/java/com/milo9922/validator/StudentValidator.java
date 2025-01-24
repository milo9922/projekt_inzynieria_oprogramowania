package com.milo9922.validator;

import com.milo9922.dto.StudentDTO;

public class StudentValidator {

    private static final IndexValidator indexValidator = new IndexValidator();
    private static final PeselValidator peselValidator = new PeselValidator();

    public boolean validate(StudentDTO student) {
        return indexValidator.validate(student.getIndexNumber()) &&
                peselValidator.validatePesel(student.getPeselNumber());
    }
}

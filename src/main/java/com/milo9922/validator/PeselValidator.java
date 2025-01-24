package com.milo9922.validator;

import com.milo9922.service.StudentService;

public class PeselValidator {
    private static final StudentService studentService = new StudentService();
    private static final String peselRegex = "^(\\d{2})(0[1-9]|1[0-2]|2[1-9]|3[0-2])(0[1-9]|[12][0-9]|3[01])\\d{5}$";

    public boolean validatePesel(String pesel) {
        return isValid(pesel) && isUnique(pesel);
    }

    private boolean isValid(String pesel) {
        return pesel.matches(peselRegex);
    }

    private boolean isUnique(String pesel) {
        return studentService.getAllStudents().stream()
                .noneMatch(s -> s.getPeselNumber().equals(pesel));
    }
}

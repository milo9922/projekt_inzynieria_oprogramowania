package com.milo9922.validator;

import com.milo9922.service.StudentService;

public class IndexValidator {
    private static final String regex = "\\d{5}";
    private static final StudentService studentService = new StudentService();

    public boolean validate(String indexNumber) {
        return isUnique(indexNumber) && isValid(indexNumber);
    }

    private boolean isValid(String indexNumber) {
        return indexNumber.matches(regex);
    }

    private boolean isUnique(String indexNumber) {
        return studentService.getAllStudents().stream()
                .noneMatch(s -> s.getIndexNumber().equals(indexNumber));
    }

}

package com.milo9922.entity;

import com.milo9922.util.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String address;
    private String indexNumber;
    private String peselNumber;
}

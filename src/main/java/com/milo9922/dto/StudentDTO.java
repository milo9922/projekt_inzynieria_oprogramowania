package com.milo9922.dto;

import com.milo9922.util.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    public StudentDTO(String firstName, String lastName, Gender gender, String address, String indexNumber, String peselNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.indexNumber = indexNumber;
        this.peselNumber = peselNumber;
    }

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String address;
    private String indexNumber;
    private String peselNumber;
}

package com.milo9922.repository;

import com.milo9922.dto.StudentDTO;
import com.milo9922.util.Gender;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Optional<StudentDTO> findById(Long id);
    List<StudentDTO> findAll();
    List<StudentDTO> findByGender(Gender gender);
    Optional<StudentDTO> findByPeselNumber(String peselNumber);
    Optional<StudentDTO> findByIndexNumber(String indexNumber);
    void saveAll(List<StudentDTO> students) throws IOException;
    void delete(Long id);
    void deleteAll();
}

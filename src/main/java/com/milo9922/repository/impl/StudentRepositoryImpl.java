package com.milo9922.repository.impl;

import com.milo9922.dto.StudentDTO;
import com.milo9922.repository.StudentRepository;
import com.milo9922.util.Gender;
import com.milo9922.util.StudentJsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public Optional<StudentDTO> findById(Long id) {
        return getStudentsFromDefaultJsonFile().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<StudentDTO> findAll() {
        return getStudentsFromDefaultJsonFile();
    }

    @Override
    public List<StudentDTO> findByGender(Gender gender) {
        return getStudentsFromDefaultJsonFile().stream()
                .filter(s -> s.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDTO> findByPeselNumber(String peselNumber) {
        return getStudentsFromDefaultJsonFile().stream()
                .filter(s -> s.getPeselNumber().equals(peselNumber))
                .findFirst();
    }

    @Override
    public Optional<StudentDTO> findByIndexNumber(String indexNumber) {
        return getStudentsFromDefaultJsonFile().stream()
                .filter(s -> s.getIndexNumber().equals(indexNumber))
                .findFirst();
    }

    @Override
    public void saveAll(List<StudentDTO> students) throws IOException {
        StudentJsonHandler.saveStudentListToDefaultJsonFile(students);
    }

    @Override
    public void delete(Long id) {
        List<StudentDTO> students = getStudentsFromDefaultJsonFile();
        students.removeIf(s -> s.getId().equals(id));
        try {
            StudentJsonHandler.saveStudentListToDefaultJsonFile(students);
        } catch (IOException e) {
            System.out.println("Błąd przy próbie usunięcia studenta!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            StudentJsonHandler.saveStudentListToDefaultJsonFile(new ArrayList<>());
        } catch (IOException e) {
            System.out.println("Błąd przy usuwaniu danych z listy studentów!");
            e.printStackTrace();
        }
    }

    private List<StudentDTO> getStudentsFromDefaultJsonFile() {
        return StudentJsonHandler.getStudentsFromJsonFile(StudentJsonHandler.STUDENT_JSON_PATH);
    }
}

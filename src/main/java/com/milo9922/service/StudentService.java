package com.milo9922.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.milo9922.dto.StudentDTO;
import com.milo9922.exception.StudentJsonException;
import com.milo9922.repository.StudentRepository;
import com.milo9922.repository.impl.StudentRepositoryImpl;
import com.milo9922.util.Gender;
import com.milo9922.util.StudentJsonHandler;
import com.milo9922.validator.PeselValidator;
import com.milo9922.validator.StudentValidator;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class StudentService {
    private static final StudentRepository studentRepository = new StudentRepositoryImpl();
    private static final StudentValidator validator = new StudentValidator();
    private final AtomicLong idGenerator = new AtomicLong(1L);

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll();
    }

    public String getAllStudentsAsJson() throws JsonProcessingException {
        return StudentJsonHandler.getStudentJsonFromStudentList(studentRepository.findAll());
    }

    public List<StudentDTO> getAllStudentsOrderByFirstName() {
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(StudentDTO::getFirstName))
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getAllStudentsOrderByLastName() {
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(StudentDTO::getLastName))
                .toList();
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Optional<StudentDTO> getStudentByIndexNumber(String indexNumber) {
        return studentRepository.findByIndexNumber(indexNumber);
    }

    public List<StudentDTO> getStudentsByGender(Gender gender) {
        return studentRepository.findByGender(gender);
    }

    public Optional<StudentDTO> getStudentByPeselNumber(String peselNumber) {
        return studentRepository.findByPeselNumber(peselNumber);
    }

    public void addStudent(StudentDTO studentDTO) {
        if(!validator.validate(studentDTO)) {
            System.out.println("Niepoprawny numer pesel bądź indeks! Wartości powinny być unikalne, numer indeksu to liczba 5 cyfrowa");
            return;
        }
        List<StudentDTO> students = studentRepository.findAll();
        studentDTO.setId(idGenerator.getAndIncrement());
        students.add(studentDTO);
        try {
            studentRepository.saveAll(students);
        } catch (IOException e) {
            System.out.println("Błąd podczas dodawania nowego studenta!");
            e.printStackTrace();
        }
    }

    public void importStudentsFromJson(String path) throws IOException {
        List<StudentDTO> studentsToImport = StudentJsonHandler.getStudentsFromJsonFile(path);
        validatePath(path);
        if(!studentsToImport.isEmpty()) {
            studentsToImport.forEach(this::addStudent);
            System.out.println("Pomyślnie zaimportowano listę studentów!");
        } else {
            System.out.println("Brak wartości do zaimportowania!");
        }
    }

    public void exportStudentsToJson(String path) throws IOException {
        validatePath(path);
        StudentJsonHandler.saveStudentListToJsonFile(studentRepository.findAll(), path);
        System.out.println("Pomyslnie zapisano listę studentów do pliku: " + path);
    }

    public void deleteStudentByIndex(String indexNumber) {
        if(indexNumber == null || indexNumber.isEmpty()) {
            System.out.println("Brak numeru indeksu!");
            return;
        }
        Long id = findStudentIdByIndex(indexNumber);
        if(id == null || id.equals(-1L)) {
            System.out.println("Nie znaleziono studenta o podanym numerze indeksu!");
            return;
        }
        studentRepository.delete(id);
        System.out.println("Usunięto studenta o indeksie: " + indexNumber);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    private Long findStudentIdByIndex(String indexNumber) {
        Optional<StudentDTO> optionalStudentDTO = studentRepository.findAll().stream()
                .filter(s -> s.getIndexNumber().equals(indexNumber))
                .findAny();
        if(optionalStudentDTO.isPresent()) {
            return optionalStudentDTO.get().getId();
        } else {
            return -1L;
        }
    }

    private void validatePath(String path) throws StudentJsonException {
        if(path == null || path.isEmpty()) {
            throw new StudentJsonException("Niewłaściwa ścieżka!");
        }
    }
}

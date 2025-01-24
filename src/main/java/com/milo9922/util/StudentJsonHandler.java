package com.milo9922.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.milo9922.entity.Student;
import com.milo9922.exception.StudentJsonException;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public final class JsonStudentMapper {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static final String EMPTY_JSON = "{}";
    public static final String STUDENT_JSON_PATH = "student.json";

    public static String mapStudentToJson(Student student) throws StudentJsonException {
        if(student == null) {
            log.warn("Student is null");
            return EMPTY_JSON;
        }
        try {
            return mapper.writeValueAsString(student);
        } catch (JsonProcessingException e) {
            log.error("Could not convert student object to JSON");
            throw new StudentJsonException(e.getMessage());
        }
    }

    public static Student jsonToStudent(String json) throws StudentJsonException {
        if(json.equals(EMPTY_JSON) || json.isEmpty()) {
            log.warn("Student json is empty");
        }
        try {
            return mapper.readValue(json, Student.class);
        } catch (JsonProcessingException e) {
            log.error("Could not convert json to Student");
            throw new StudentJsonException(e.getMessage());
        }
    }

    public static void studentToJsonFile(Student student) throws StudentJsonException {
        if(student == null) {
            log.warn("Student is null");
            throw new StudentJsonException("Could not convert student object to JSON file");
        }
        try {
            mapper.writeValue(new File(STUDENT_JSON_PATH), student);
        } catch (IOException e) {
            log.error("Could not convert student object to JSON file");
            throw new StudentJsonException(e.getMessage());
        }
    }

    public static List<Student> getStudentsFromJsonFile(String pathToJson) {
        InputStream inputStream = Student.class.getClassLoader().getResourceAsStream(pathToJson);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);
        try {
            List<Student> students = mapper.readValue(inputStream, collectionType);
            if(students.isEmpty()) {
                return new ArrayList<>();
            } else {
                return students;
            }
        } catch (IOException e) {
            log.error("Could not convert json to Student list");
            throw new RuntimeException(e);
        }
    }

}

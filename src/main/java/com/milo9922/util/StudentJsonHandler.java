package com.milo9922.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.milo9922.dto.StudentDTO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public final class StudentJsonHandler {

    public static final String EMPTY_JSON = "{}";
    public static final String STUDENT_JSON_PATH = System.getProperty("user.dir") + "\\students.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CollectionType studentListCollectionType = mapper.getTypeFactory().constructCollectionType(List.class, StudentDTO.class);

    public static String mapStudentToJson(StudentDTO studentDTO) {
        try {
            if(studentDTO != null) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(studentDTO);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return EMPTY_JSON;
    }

    public static List<StudentDTO> getStudentsFromJsonFile(String pathToJson) {
        try {
            File jsonFile = new File(pathToJson);
            if(!jsonFile.exists()) {
                return new ArrayList<>();
            }
            List<StudentDTO> studentDTOS = mapper.readValue(jsonFile, studentListCollectionType);
            if(!studentDTOS.isEmpty()) {
                return studentDTOS;
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytu pliku: " + pathToJson);
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String getStudentJsonFromStudentList(List<StudentDTO> studentsList) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(studentsList);
    }

    public static void saveStudentListToDefaultJsonFile(List<StudentDTO> students) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(STUDENT_JSON_PATH), students);
    }

    public static void saveStudentListToJsonFile(List<StudentDTO> students, String path) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), students);
    }
}

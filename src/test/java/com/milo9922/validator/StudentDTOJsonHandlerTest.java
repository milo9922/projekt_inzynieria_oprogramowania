package com.milo9922.validator;

import com.milo9922.dto.StudentDTO;
import com.milo9922.exception.StudentJsonException;
import com.milo9922.util.Gender;
import com.milo9922.util.StudentJsonHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDTOJsonHandlerTest {

    @Test
    void shouldConvertStudentToJson() throws StudentJsonException {
        StudentDTO studentDTO = new StudentDTO(
                1L,
                "Jan",
                "Kowalski",
                Gender.MALE,
                "ul. Projektowa 4, 20-209 Lublin",
                "12345",
                "920902002551"
        );
        StringBuilder correctJson = new StringBuilder();
        correctJson.append("{");
        correctJson.append("\"id\":1,");
        correctJson.append("\"firstName\":\"Jan\",");
        correctJson.append("\"lastName\":\"Kowalski\",");
        correctJson.append("\"gender\":\"MALE\",");
        correctJson.append("\"address\":\"ul. Projektowa 4, 20-209 Lublin\",");
        correctJson.append("\"indexNumber\":\"12345\",");
        correctJson.append("\"peselNumber\":\"920902002551\"");
        correctJson.append("}");

        String result = StudentJsonHandler.mapStudentToJson(studentDTO);

        assertEquals(correctJson.toString(), result);
    }

    @Test
    void shouldReturnEmptyJsonForNullStudent() throws StudentJsonException {
        String result = StudentJsonHandler.mapStudentToJson(null);
        assertEquals(StudentJsonHandler.EMPTY_JSON, result);
    }
}

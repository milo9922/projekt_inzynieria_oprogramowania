package com.milo9922.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.milo9922.dto.StudentDTO;
import com.milo9922.service.StudentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUIAction {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public void printMenuOptions() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nWybierz działanie:\n");
        builder.append("1 - Wyświetl listę studentów\n");
        builder.append("2 - Importuj z pliku\n");
        builder.append("3 - Dodaj studenta\n");
        builder.append("4 - Usuń studenta\n");
        builder.append("5 - Exportuj dane do pliku\n");
        builder.append("6 - Wyczyść listę studentów\n");
        builder.append("7 - Wyświetl przefiltrowaną listę studentów\n");
        builder.append("8 - Wyświetl posortowaną listę studentów\n");
        builder.append("0 - Zakończ działanie programu\n");
        System.out.println(builder);
    }

    public void printAllStudents() throws JsonProcessingException {
        System.out.println("\nWszyscy studenci: \n");
        System.out.println(studentService.getAllStudentsAsJson());
    }

    public void importStudentsFromJson() {
        System.out.println("\nPodaj ścieżkę do pliku: \n");
        String path = scanner.nextLine();
        try {
            studentService.importStudentsFromJson(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addStudent() {
        System.out.println("Imię: ");
        String firstName = scanner.nextLine();
        System.out.println("Nazwisko: ");
        String lastName = scanner.nextLine();
        System.out.println("Płeć (MALE - mężczyzna, FEMALE - kobieta): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Adres: ");
        String address = scanner.nextLine();
        System.out.println("Number indeksu: ");
        String indexNumber = scanner.nextLine();
        System.out.println("Numer pesel: ");
        String peselNumber = scanner.nextLine();

        StudentDTO studentDTO = new StudentDTO(firstName, lastName, gender, address, indexNumber, peselNumber);
        studentService.addStudent(studentDTO);
    }

    public void deleteStudent() {
        System.out.println("\nWprowadź indeks studenta, którego chcesz usunąć: \n");
        String indexNumber = scanner.nextLine();
        studentService.deleteStudentByIndex(indexNumber);
    }

    public void exportStudentsToJson() throws IOException {
        System.out.println("\nPodaj ścieżkę do zapisu pliku: \n");
        String path = scanner.nextLine();
        studentService.exportStudentsToJson(path);
    }

    public void deleteAllStudents() {
        System.out.println("\nCzy na pewno chcesz wyczyścić wszystkie dane studentów? (T - TAK, N - NIE)\n");
        String confirmation = scanner.nextLine();
        if(confirmation.equalsIgnoreCase("T")) {
            studentService.deleteAllStudents();
        } else {
            while(!confirmation.equalsIgnoreCase("T") && !confirmation.equalsIgnoreCase("N")) {
                System.out.println("\nWprowadzono nieprawidłowy symbol!\n");
                System.out.println("\nCzy na pewno chcesz wyczyścić wszystkie dane studentów? (T - TAK, N - NIE)\n");
                confirmation = scanner.nextLine();
            }
        }
    }

    public void printFilteredStudents() throws JsonProcessingException {
        System.out.println("\nWybierz po jakiej wartości chcesz wyszukać:\n");
        System.out.println("ID, INDEKS, PLEC, PESEL\n");
        String filter = scanner.nextLine().toUpperCase();

        switch (filter) {
            case "ID": {
                System.out.println("Wprowadź ID:\n");
                String id = scanner.nextLine();
                if (id.isEmpty()) {
                    System.out.println("Wprowadzono puste ID!");
                    break;
                }

                Optional<StudentDTO> student = studentService.getStudentById(Long.parseLong(id));
                if (student.isPresent()) {
                    System.out.println(StudentJsonHandler.mapStudentToJson(student.get()));
                } else {
                    System.out.println("Nie znaleziono studenta o podanym ID");
                }
                break;
            }

            case "INDEKS": {
                System.out.println("Wprowadź numer indeksu: \n");
                String indexNumber = scanner.nextLine();
                if (indexNumber.isEmpty()) {
                    System.out.println("Wprowadzono pusty numer indeksu!");
                    break;
                }
                Optional<StudentDTO> student = studentService.getStudentByIndexNumber(indexNumber);
                if (student.isPresent()) {
                    System.out.println(StudentJsonHandler.mapStudentToJson(student.get()));
                } else {
                    System.out.println("Nie znaleziono studenta o podanym numerze indeksu");
                }
                break;
            }

            case "PLEC": {
                System.out.println("Wybierz płeć studentów, których chcesz wyświetlić (M - mężczyzna, K - kobieta):\n");
                String genderInput = scanner.nextLine();
                if(genderInput != null) {
                    Gender gender;
                    if(genderInput.equalsIgnoreCase("M")) {
                        gender = Gender.MALE;
                    } else if (genderInput.equalsIgnoreCase("K")) {
                        gender = Gender.FEMALE;
                    } else {
                        System.out.println("Wprowadzono nieprawidłowe dane!");
                        break;
                    }
                    List<StudentDTO> students = studentService.getStudentsByGender(gender);
                    System.out.println(StudentJsonHandler.getStudentJsonFromStudentList(students));
                }
            }

            case "PESEL": {
                System.out.println("Wprowadź pesel po którym chcesz wyszukać studenta: ");
                String peselNumber = scanner.nextLine();
                if(peselNumber == null || peselNumber.isEmpty()) {
                    System.out.println("Wprowadzono pusty numer pesel!");
                    break;
                }
                Optional<StudentDTO> student = studentService.getStudentByPeselNumber(peselNumber);
                if(student.isPresent()) {
                    System.out.println(StudentJsonHandler.mapStudentToJson(student.get()));
                } else {
                    System.out.println("Nie znaleziono studenta o podanym numerze pesel");
                }
                break;
            }

            default:{
                System.out.println("Nieprawidłowa wartość! Powrót do menu głównego...");
            }
        }
    }

    public void printSortedStudents() throws JsonProcessingException {
        System.out.println("Wybierz po jakich wartościach posortować studentów: \n");
        System.out.println("IMIE, NAZWISKO");
        String sortBy = scanner.nextLine().toUpperCase();

        switch (sortBy) {
            case "IMIE": {
                System.out.println("Lista studentów posortowana po imionach: \n");
                System.out.println(StudentJsonHandler.getStudentJsonFromStudentList(studentService.getAllStudentsOrderByFirstName()));
                break;
            }

            case "NAZWISKO": {
                System.out.println("Lista studentów posortowana po nazwisku: \n");
                System.out.println(StudentJsonHandler.getStudentJsonFromStudentList(studentService.getAllStudentsOrderByLastName()));
                break;
            }

            default: {
                System.out.println("Nieprawidłowa wartość! Powrót do menu głównego...");
            }
        }
    }
}

package com.milo9922;

import com.milo9922.util.ConsoleUIAction;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final ConsoleUIAction action = new ConsoleUIAction();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String selectedAction = "";
        while(!selectedAction.equals("0")) {
            action.printMenuOptions();
            selectedAction = scanner.nextLine();
            switch (selectedAction) {
                case "1": {
                    //Wyświetlenie listy wszystkich studentów zawierających się w pliku students.json
                    action.printAllStudents();
                    break;
                }

                case "2": {
                    //Import studentów z pliku (należy podać ścieżkę w kolejnym kroku)
                    action.importStudentsFromJson();
                    break;
                }

                case "3": {
                    //Dodanie studenta poprzez wprowadzanie wartości w konsoli
                    action.addStudent();
                    break;
                }

                case "4" : {
                    //Usunięcie studenta po numerze indeksu
                    action.deleteStudent();
                    break;
                }

                case "5": {
                    //Eksport danych do pliku json (należy podać ścieżkę wraz z nazwą i rozszerzeniem pliku - .json)
                    action.exportStudentsToJson();
                    break;
                }

                case "6": {
                    //Wyczyszczenie zawartości pliku students.json (po potwierdzeniu w 2 kroku)
                    action.deleteAllStudents();
                    break;
                }

                case "7": {
                    //Wyświetlenie przefiltrowanych za pomocą wybranego w kolejnym kroku filtra
                    action.printFilteredStudents();
                    break;
                }

                case "8": {
                    //Wyświetlenie posortowanej na podstawie imienia lub nazwiska listy studentów
                    action.printSortedStudents();
                    break;
                }

                case "0": {
                    //Wyłączenie programu
                    System.out.println("Zamykanie programu...");
                    break;
                }

                default: {
                    System.out.println("Wybrano nieistniejące działanie! Wybierz jedno z dostępnych działań: ");
                }
            }
        }
    }

}
/**
 * Данный класс демонстрирует исполнения методов из класса DataBase.
 * @author Илья Немоляев
 * @version 2.0
 */

import java.sql.*;
import java.util.Scanner;

public class Main {

    static int exit = 1;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        int exit = 1;
        while (exit > 0) {
            System.out.println("Введите номер таблицы\n" +
                    "1 - Студенты \t 2 - Группы \t 3 - Преподаватели\n" +
                    "Для выхода введите 0");
            int table = in.nextInt();
            in.reset();
            switch (table) {
                case 1: {
                    switchStudent();
                    break;
                }
                case 2: {

                }
                case 3: {
                    switchTeacher();
                    break;
                }
                case 0: {
                    exit = 0;
                    break;
                }
            }
        }
    }

    static void switchStudent() throws SQLException {
        int codeExit = 1;
        while (codeExit != 0) {
            System.out.println("Введите номер операции\n" +
                    "1 - SELECT \t 2 - INSERT \t 3 - UPDATE \t 4 - DELETE");
            int operation = in.nextInt();
            in.nextLine();

            switch (operation) {
                case 1: {
                    Student student = new Student();
                    student.select();
                    break;
                }
                case 2: {
                    System.out.println("Введите значения поля в следующем порядке\n" +
                            "Ф.И.О, число рождения, месяц рождения, год рождения, " +
                            "пол и номер группы учащегося:");

                    System.out.println("Ф.И.О");
                    String name = in.nextLine();

                    System.out.println("День рождения");
                    int day = in.nextInt();

                    System.out.println("Месяц");
                    int month = in.nextInt();

                    System.out.println("Год");
                    int year = in.nextInt();
                    in.nextLine();

                    System.out.println("Пол (одной буквой кириллицы)");
                    Male gender;
                    String male = in.nextLine();
                    if (Male.MAN.getValue().equals(male)) {
                        gender = Male.MAN;
                    } else {
                        gender = Male.WOMAN;
                    }

                    System.out.println("Номер группы");
                    int group = in.nextInt();
                    in.reset();

                    Student student = new Student(name, day, month, year, gender, group);
                    student.add();
                    break;
                }
                case 3: {

                    System.out.println("Введите id студента, данные которого" +
                            " вы хотите хотиете изменить");
                    int idStudent = in.nextInt();
                    in.nextLine();

                    Student student = new Student().getById(idStudent);
                    System.out.println("Введите последовательно  ");

                }
                case 4: {
                    System.out.println("Введите id студента, данные которого" +
                            " вы хотите хотиете удалить");
                    int idStudent = in.nextInt();
                    in.nextLine();

                    Student student = new Student().getById(idStudent);

                    student.delete();
                    break;
                }
                case 0: {
                    codeExit = 0;
                    break;
                }
                case -1: {
                    codeExit = 0;
                    exit = 0;
                }
            }
        }
    }

    static void switchTeacher() throws SQLException {
        int codeExit = 1;
        while (codeExit != 0) {
            System.out.println("Введите номер операции \n" +
                    "1 - SELECT \t 2 - INSERT \t 3 - UPDATE \t 4 - DELETE");
            int operation = in.nextInt();
            in.nextLine();

            switch (operation) {
                case 1: {
                    Teacher teacher = new Teacher();
                    teacher.select();
                    break;
                }
                case 2: {
                    System.out.println("Введите значения поля в следующем порядке\n" +
                            "Ф.И.О, число рождения, месяц рождения, год рождения, " +
                            "пол преподавателя:");

                    System.out.println("Ф.И.О");
                    String name = in.nextLine();

                    System.out.println("День рождения");
                    int day = in.nextInt();

                    System.out.println("Месяц");
                    int month = in.nextInt();

                    System.out.println("Год");
                    int year = in.nextInt();
                    in.nextLine();

                    System.out.println("Пол (одной буквой кириллицы)");
                    Male gender;
                    String male = in.nextLine();
                    if (Male.MAN.getValue().equals(male)) {
                        gender = Male.MAN;
                    } else {
                        gender = Male.WOMAN;
                    }

                    Teacher teacher = new Teacher(name, day, month, year, gender);
                    teacher.add();
                    break;
                }
                case 3: {

                }
                case 4: {
                    System.out.println("Введите id преподавателя, данные которого" +
                            " вы хотите хотите удалить");
                    int idTeacher = in.nextInt();
                    in.nextLine();

                    Teacher teacher = new Teacher().getById(idTeacher);

                    teacher.delete();
                    break;
                }
                case 0: {
                    codeExit = 0;
                    break;
                }
            }
        }
    }

    static void switchGroup() throws SQLException {
        int exit = 1;
        while(exit!=0) {
            System.out.println("Введите номер операции \n" +
                    "1 - SELECT \t 2 - INSERT \t 3 - UPDATE \t 4 - DELETE");
            int operation = in.nextInt();
            in.nextLine();

            switch (operation) {
                case 1 : {
                    Group group = new Group();
                    group.select();

                } case 2 : {

                } case 3 : {

                } case 4 : {

                } case 0 : {
                    exit = 0;
                    break;
                }
            }
        }
    }
}

/**
 * Данный класс демонстрирует исполнения методов из класса DataBase.
 * @author Илья Немоляев
 * @version 2.0
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static int exit = 1;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        while (exit > 0) {
            System.out.println("Выберите номер таблицы, с которой хотите работать" +
                    "\n1 - Студенты \t 2 - Группы \t 3 - Преподаватели" +
                    "\nДля выхода из программы введите 0");
            int choose = in.nextInt();
            in.nextLine();
            switch(choose) {
                case 1 : {
                    studentWork();
                    break;
                } case 2 : {
                    break;

                } case 3 : {
                    break;

                } case 0 : {
                    break;

                }
            }
        }
    }

    public static void studentWork() throws SQLException {
        int back = 1;
        while (back > 0) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t 4 - Удаление" +
                    "\nДля выхода из таблицы введите 0");
            int operation = in.nextInt();
            in.nextLine();
            switch (operation) {
                case 1 : {
                    Student2 student = new Student2();
                    System.out.println("Выберите критерий поиска" +
                            "\n1 - ID \t 2 - Ф.И.О. \t 3 - Дата рождения" +
                            "\t 4 - Группа \t 5 - Все");
                    int criterion = in.nextInt();
                    in.nextLine();
                    switch (criterion) {
                        case 1 : {
                            System.out.println("Введите ID студента");

                            int id = in.nextInt();
                            in.nextLine();

                            student.get(id);
                            break;
                        } case 2 : {
                            System.out.println("Введите Ф.И.О. студента");
                            String nameSearch = in.nextLine();
                            student.get(nameSearch);
                            break;
                        } case 3 : {
                            System.out.println("Введите дату рождения студента");

                            System.out.println("День:");
                            int day = in.nextInt();
                            System.out.println("Месяц:");
                            int month = in.nextInt();
                            System.out.println("Год:");
                            int year = in.nextInt();
                            in.nextLine();

                            student.get(day, month, year);
                            break;
                        } case 4 : {
                            System.out.println("Введите номер группы");

                            int numberGroup = in.nextInt();
                            in.nextLine();
                            Group2 group = new Group2();
                            group.getByNumber(numberGroup);

                            student.viewGroupSteudent(group.getId());
                            break;
                        } case 5 : {
                            student.get();
                            break;
                        }
                    }
                    break;
                } case 2 : {
                    System.out.println("Введите данные о студенте");

                    System.out.println("Ф.И.О.");
                    String name = in.nextLine();

                    System.out.println("Дату рождения через пробел");
                    int day = in.nextInt();
                    int month = in.nextInt();
                    int year = in.nextInt();
                    in.nextLine();

                    System.out.println("Пол одной буквой");
                    String gender = in.nextLine();
                    Male male;
                    if (Male.MAN.getValue().equals(gender)) {
                        male = Male.MAN;
                    } else {
                        male = Male.WOMAN;
                    }

                    System.out.println("Введите номер группы");
                    int numberGroup = in.nextInt();
                    in.nextLine();
                    Group2 group = new Group2();
                    group.getByNumber(numberGroup);

                    Student2 student = new Student2(name,
                            LocalDate.of(year,month,day), male, group.getId());
                    student.add();
                    break;
                } case 3 : {
                    System.out.println("Введите номер поля для изменения записи о студенте" +
                            "\n1 - Ф.И.О. \t 2 - Дата рождения \t 3 - Пол \t 4 - Группа" +
                            "\n Введите 0, чтобы вернуться");
                    int numberAtribut = in.nextInt();
                    in.nextLine();
                    switch (numberAtribut) {
                        case 1 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новое Ф.И.О.");
                            String name = in.nextLine();



                        } case 2 : {

                        } case 3 : {

                        } case 4 : {

                        } case 0 : {
                            break;
                        }
                    }
                    break;
                } case 4 : {

                } case 0 : {

                }
            }
        }
    }
}
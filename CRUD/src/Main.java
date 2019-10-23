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
                    groupWork();
                    break;
                } case 3 : {
                    teacherWork();
                    break;
                } case 0 : {
                    exit = 0;
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

                            Student2 student = new Student2();
                            student.get(id);
                            student.setNameStudent(name);
                            break;
                        } case 2 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новая дата рождения");
                            int day = in.nextInt();
                            int month = in.nextInt();
                            int year = in.nextInt();
                            in.nextLine();

                            Student2 student = new Student2();
                            student.get(id);
                            student.setBirthdayStudent(LocalDate.of(year, month, day));
                            break;
                        } case 3 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Пол (Одной буквой)");
                            String gender = in.nextLine();

                            Male male;
                            if (Male.MAN.getValue().equals(gender)) {
                                male = Male.MAN;
                            } else {
                                male = Male.WOMAN;
                            }

                            Student2 student = new Student2();
                            student.get(id);
                            student.setGenderStudent(male);
                            break;
                        } case 4 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новый номер группы");
                            int number = in.nextInt();
                            in.nextLine();

                            Group2 group = new Group2();
                            group.getByNumber(number);

                            Student2 student = new Student2();
                            student.get(id);
                            student.setGroupStudent(group.getId());
                            break;

                        } case 0 : {
                            break;
                        }
                    }
                    break;
                } case 4 : {
                    System.out.println("Введите ID студента");
                    int id = in.nextInt();
                    in.nextLine();

                    System.out.println("Вы уверены, что хотите удалить запись " +
                            "об этом студенте\n Y/N");
                    String answer = in.nextLine();
                    switch (answer) {
                        case "Y" : {
                            Student2 student = new Student2();
                            student.get(id);
                            student.remove();
                            break;
                        }
                        case "N" : {
                            break;
                        }
                    }
                    break;
                } case 0 : {
                    back = 0;
                    break;
                }
            }
        }
    }

    public static void teacherWork() throws SQLException {
        int back = 1;
        while (back > 0) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Группы преподвателя" +
                    "\nДля выхода из таблицы введите 0");
            int operation = in.nextInt();
            in.nextLine();
            switch (operation) {
                case 1 : {
                    Teacher2 teacher = new Teacher2();
                    System.out.println("Выберите критерий поиска" +
                            "\n1 - ID \t 2 - Ф.И.О. \t 3 - Дата рождения" +
                            "\t 4 - Все");
                    int criterion = in.nextInt();
                    in.nextLine();
                    switch (criterion) {
                        case 1 : {
                            System.out.println("Введите ID преподавателя");

                            int id = in.nextInt();
                            in.nextLine();

                            teacher.get(id);
                            break;
                        } case 2 : {
                            System.out.println("Введите Ф.И.О. преподавателя");
                            String nameSearch = in.nextLine();
                            teacher.get(nameSearch);
                            break;
                        } case 3 : {
                            System.out.println("Введите дату рождения преподавателя");

                            System.out.println("День:");
                            int day = in.nextInt();
                            System.out.println("Месяц:");
                            int month = in.nextInt();
                            System.out.println("Год:");
                            int year = in.nextInt();
                            in.nextLine();

                            teacher.get(day, month, year);
                            break;
                        } case 4 : {
                            teacher.get();
                            break;
                        }
                    }
                    break;
                } case 2 : {
                    System.out.println("Введите данные о преподавателе");

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

                    Teacher2 teacher = new Teacher2(name,
                            LocalDate.of(year,month,day), male);
                    teacher.add();
                    break;
                } case 3 : {
                    System.out.println("Введите номер поля для изменения записи о преподавтеле" +
                            "\n1 - Ф.И.О. \t 2 - Дата рождения \t 3 - Пол" +
                            "\n Введите 0, чтобы вернуться");
                    int numberAtribut = in.nextInt();
                    in.nextLine();
                    switch (numberAtribut) {
                        case 1 : {
                            System.out.println("Введите ID преподавтеля");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новое Ф.И.О.");
                            String name = in.nextLine();

                            Teacher2 teacher = new Teacher2();
                            teacher.get(id);
                            teacher.setNameTeacher(name);
                            break;
                        } case 2 : {
                            System.out.println("Введите ID преподавтеля");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новая дата рождения");
                            int day = in.nextInt();
                            int month = in.nextInt();
                            int year = in.nextInt();
                            in.nextLine();

                            Teacher2 teacher = new Teacher2();
                            teacher.get(id);
                            teacher.setBirthdayTeacher(LocalDate.of(year, month, day));
                            break;
                        } case 3 : {
                            System.out.println("Введите ID преподавтеля");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Пол (Одной буквой)");
                            String gender = in.nextLine();

                            Male male;
                            if (Male.MAN.getValue().equals(gender)) {
                                male = Male.MAN;
                            } else {
                                male = Male.WOMAN;
                            }

                            Teacher2 teacher = new Teacher2();
                            teacher.get(id);
                            teacher.setGenderTeacher(male);
                            break;
                        } case 0 : {
                            break;
                        }
                    }
                    break;
                } case 4 : {
                    System.out.println("Введите ID преподавателя");
                    int id = in.nextInt();
                    in.nextLine();

                    System.out.println("Вы уверены, что хотите удалить запись " +
                            "об этом преподавателе\n Y/N");
                    String answer = in.nextLine();
                    switch (answer) {
                        case "Y" : {
                            Teacher2 teacher = new Teacher2();
                            teacher.get(id);
                            teacher.remove();
                            break;
                        }
                        case "N" : {
                            break;
                        }
                    }
                    break;
                } case 5 : {
                    int backMenuGroupTeacher = 1;
                    while (backMenuGroupTeacher > 0) {
                        System.out.println("Выберите операцию для дальнейшей работы" +
                                "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                "4 - Удаление" +
                                "\nДля выхода из таблицы введите 0");
                        int operationWithListGroup = in.nextInt();
                        in.nextLine();

                        Teacher2 teacher = new Teacher2();

                        switch (operationWithListGroup) {
                            case 1 : {
                                System.out.println("Введите ID преподавателя");
                                int id = in.nextInt();
                                in.nextLine();

                                teacher.get(id);
                                teacher.viewGroups();
                                break;
                            } case 2 : {
                                System.out.println("Введите ID преподавателя");
                                int id = in.nextInt();
                                in.nextLine();

                                teacher.get(id);
                                System.out.println("Введите номер группы," +
                                        " который вы хотите присвоить" +
                                        " преподавателю");
                                int number = in.nextInt();
                                in.nextLine();
                                Group2 group = new Group2();
                                group.getByNumber(number);

                                teacher.addGroup(group.getId());
                                break;
                            } case 3 : {
                                System.out.println("Введите ID преподавателя");
                                int id = in.nextInt();
                                in.nextLine();

                                teacher.get(id);
                                System.out.println("Введите номер группы," +
                                        " который вы хотите изменить" +
                                        " преподаватель");
                                int numberOldGroup = in.nextInt();
                                in.nextLine();

                                System.out.println("Введите новый номер группы");
                                int numberNewGroup = in.nextInt();
                                in.nextLine();

                                Group2 group1 = new Group2();
                                group1.getByNumber(numberOldGroup);
                                numberOldGroup = group1.getId();
                                Group2 group2 = new Group2();
                                group2.getByNumber(numberNewGroup);
                                numberNewGroup = group2.getId();

                                teacher.setGroup(numberOldGroup, numberNewGroup);
                                break;
                            } case 4 : {
                                System.out.println("Введите ID преподавателя");
                                int id = in.nextInt();
                                in.nextLine();

                                teacher.get(id);
                                System.out.println("Введите номер группы," +
                                        " который вы хотите удалить" +
                                        " преподавателю");
                                int number = in.nextInt();
                                in.nextLine();
                                Group2 group = new Group2();
                                group.getByNumber(number);

                                teacher.removeGroup(group.getId());
                                break;
                            } case 0 : {
                                backMenuGroupTeacher = 0;
                                break;
                            }
                        }
                    }
                } case 0 : {
                    back = 0;
                    break;
                }
            }
        }
    }

    public static void groupWork() throws SQLException {
        int back = 1;
        while (back > 0) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Прпеодаватели группы" +
                    "\nДля выхода из таблицы введите 0");
            int operation = in.nextInt();
            in.nextLine();
            switch (operation) {
                case 1: {
                    Group2 group = new Group2();
                    System.out.println("Выберите критерий поиска" +
                            "\n1 - ID \t 2 - Номер группы \t 3 - Все");
                    int criterion = in.nextInt();
                    in.nextLine();
                    switch (criterion) {
                        case 1: {
                            System.out.println("Введите ID группы");

                            int id = in.nextInt();
                            in.nextLine();

                            group.getById(id);
                            break;
                        }
                        case 2: {
                            System.out.println("Введите Ф.И.О. преподавателя");

                            int number = in.nextInt();
                            in.nextLine();

                            group.getByNumber(number);
                            break;
                        }
                        case 3: {
                            group.get();
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.println("Введите номер группы");
                    int number = in.nextInt();
                    in.nextLine();

                    Group2 group = new Group2(number);
                    group.add();
                    break;
                }
                case 3: {
                    System.out.println("Введите номер группы");
                    int number = in.nextInt();
                    in.nextLine();

                    System.out.println("Новый номер группы");
                    int newNumber = in.nextInt();
                    in.nextLine();

                    Group2 group = new Group2();
                    group.getByNumber(number);
                    group.set(newNumber);
                    break;
                } case 4 : {
                    System.out.println("Введите номер группы");
                    int number = in.nextInt();
                    in.nextLine();

                    System.out.println("Вы уверены, что хотите удалить запись " +
                            "об этой группе\n Y/N");
                    String answer = in.nextLine();
                    switch (answer) {
                        case "Y" : {
                            Group2 group = new Group2();
                            group.getByNumber(number);
                            group.remove();
                            break;
                        }
                        case "N" : {
                            break;
                        }
                    }
                    break;
                } case 5 : {
                    int backMenuTeacherGroup = 1;
                    while (backMenuTeacherGroup > 0) {
                        System.out.println("Выберите операцию для дальнейшей работы" +
                                "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                "4 - Удаление" +
                                "\nДля выхода из таблицы введите 0");
                        int operationWithListTeacher = in.nextInt();
                        in.nextLine();

                        Group2 group = new Group2();

                        switch (operationWithListTeacher) {
                            case 1 : {
                                System.out.println("Введите номер группы");
                                int number = in.nextInt();
                                in.nextLine();

                                group.getByNumber(number);
                                group.viewTeachers();
                                break;
                            } case 2 : {
                                System.out.println("Введите номер группы");
                                int number = in.nextInt();
                                in.nextLine();

                                group.getByNumber(number);
                                System.out.println("Введите ID реподавателя," +
                                        " которого вы хотите присвоить" +
                                        " группе");
                                int id = in.nextInt();
                                in.nextLine();

                                group.addTeacher(id);
                                break;
                            } case 3 : {
                                System.out.println("Введите номер группы");
                                int number = in.nextInt();
                                in.nextLine();

                                group.getByNumber(number);
                                System.out.println("Введите ID преподавателя," +
                                        " которого вы хотите заменить" +
                                        " группе");
                                int idOldTeacher = in.nextInt();
                                in.nextLine();

                                System.out.println("Введите ID нового преподавателя");
                                int idNewTeacher = in.nextInt();
                                in.nextLine();

                                group.setTeacher(idOldTeacher, idNewTeacher);
                                break;
                            } case 4 : {
                                System.out.println("Введите номер группы");
                                int number = in.nextInt();
                                in.nextLine();

                                group.getByNumber(number);
                                System.out.println("Введите ID преподавателя," +
                                        " которого вы хотите удалить" +
                                        " группе");
                                int idTeacher = in.nextInt();
                                in.nextLine();

                                group.removeTeacher(idTeacher);
                                break;
                            } case 0 : {
                                backMenuTeacherGroup = 0;
                                break;
                            }
                        }
                    }
                } case 0 : {
                    back = 0;
                    break;
                }
            }
        }
    }
}
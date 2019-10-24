import objectForStrokeBase.Group;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import workWithBase.Classes.GroupBase;
import workWithBase.Classes.StudentBase;

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
                switch (choose) {
                    case 1: {
                        studentWork();
                        break;
                    }
                    case 2: {
                        groupWork();
                        break;
                    }
                    case 3: {
                        teacherWork();
                        break;
                    }
                    case 0: {
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
                    Student student = new Student();
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

                            StudentBase studentBase = new StudentBase();
                            studentBase.selectStudent(nameSearch);
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
                            Group group = new Group();
                            group.getByNumber(numberGroup);

                            StudentBase studentBase = new StudentBase();
                            studentBase.selectGroup(group.getId());
                            break;
                        } case 5 : {
                            StudentBase studentBase = new StudentBase();
                            studentBase.selectStudent();
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
                    Gender male;
                    if (Gender.MAN.getValue().equals(gender)) {
                        male = Gender.MAN;
                    } else {
                        male = Gender.WOMAN;
                    }

                    System.out.println("Введите номер группы");
                    int numberGroup = in.nextInt();
                    in.nextLine();
                    Group group = new Group();
                    group.getByNumber(numberGroup);

                    Student student = new Student(name,
                            LocalDate.of(year,month,day), male, group.getId());
                    StudentBase studentBase = new StudentBase();
                    studentBase.insert(student);
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

                            Student student = new Student();
                            student.get(id);
                            student.setNameStudent(name);
                            StudentBase studentBase = new StudentBase();
                            studentBase.update(student);
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

                            Student student = new Student();
                            student.get(id);
                            student.setBirthdayStudent(LocalDate.of(year, month, day));
                            StudentBase studentBase = new StudentBase();
                            studentBase.update(student);
                            break;
                        } case 3 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Пол (Одной буквой)");
                            String gender = in.nextLine();

                            Gender male;
                            if (Gender.MAN.getValue().equals(gender)) {
                                male = Gender.MAN;
                            } else {
                                male = Gender.WOMAN;
                            }

                            Student student = new Student();
                            student.get(id);
                            student.setGenderStudent(male);
                            StudentBase studentBase = new StudentBase();
                            studentBase.update(student);
                            break;
                        } case 4 : {
                            System.out.println("Введите ID студента");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Новый номер группы");
                            int number = in.nextInt();
                            in.nextLine();

                            Group group = new Group();
                            group.getByNumber(number);

                            Student student = new Student();
                            student.get(id);
                            student.setGroupStudent(group.getId());
                            StudentBase studentBase = new StudentBase();
                            studentBase.update(student);
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
                            Student student = new Student();
                            student.get(id);
                            StudentBase studentBase = new StudentBase();
                            studentBase.delete(student);
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
                    Teacher teacher = new Teacher();
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
                    Gender male;
                    if (Gender.MAN.getValue().equals(gender)) {
                        male = Gender.MAN;
                    } else {
                        male = Gender.WOMAN;
                    }

                    Teacher teacher = new Teacher(name,
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

                            Teacher teacher = new Teacher();
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

                            Teacher teacher = new Teacher();
                            teacher.get(id);
                            teacher.setBirthdayTeacher(LocalDate.of(year, month, day));
                            break;
                        } case 3 : {
                            System.out.println("Введите ID преподавтеля");
                            int id = in.nextInt();
                            in.nextLine();

                            System.out.println("Пол (Одной буквой)");
                            String gender = in.nextLine();

                            Gender male;
                            if (Gender.MAN.getValue().equals(gender)) {
                                male = Gender.MAN;
                            } else {
                                male = Gender.WOMAN;
                            }

                            Teacher teacher = new Teacher();
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
                            Teacher teacher = new Teacher();
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

                        Teacher teacher = new Teacher();

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
                                Group group = new Group();
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

                                Group group1 = new Group();
                                group1.getByNumber(numberOldGroup);
                                numberOldGroup = group1.getId();
                                Group group = new Group();
                                group.getByNumber(numberNewGroup);
                                numberNewGroup = group.getId();

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
                                Group group = new Group();
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
                    Group group = new Group();
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
                            GroupBase groupBase = new GroupBase();
                            groupBase.select();
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    System.out.println("Введите номер группы");
                    int number = in.nextInt();
                    in.nextLine();

                    Group group = new Group(number);
                    GroupBase groupBase = new GroupBase();
                    groupBase.insert(group);
                    break;
                }
                case 3: {
                    System.out.println("Введите номер группы");
                    int number = in.nextInt();
                    in.nextLine();

                    System.out.println("Новый номер группы");
                    int newNumber = in.nextInt();
                    in.nextLine();

                    Group group = new Group();
                    group.getByNumber(number);
                    group.set(newNumber);
                    GroupBase groupBase = new GroupBase();
                    groupBase.update(group);
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
                            Group group = new Group();
                            group.getByNumber(number);
                            GroupBase groupBase = new GroupBase();
                            groupBase.delete(group);
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

                        Group group = new Group();

                        switch (operationWithListTeacher) {
                            case 1 : {
                                System.out.println("Введите номер группы");
                                int number = in.nextInt();
                                in.nextLine();

                                group.getByNumber(number);
                                GroupBase groupBase = new GroupBase();
                                groupBase.selectTeacher(group);
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

                                GroupBase groupBase = new GroupBase();
                                groupBase.insertTeacher(group, id);
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

                                GroupBase groupBase = new GroupBase();
                                groupBase.updateTeacher(group, idOldTeacher, idNewTeacher);
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

                                GroupBase groupBase = new GroupBase();
                                groupBase.deleteTeacher(group, idTeacher);
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
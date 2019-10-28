import objectForStrokeBase.Group;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import workWithBase.classes.GroupBase;
import workWithBase.classes.StudentBase;
import workWithBase.classes.TeacherBase;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static boolean exit = true;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        while (exit) {
            System.out.println("Выберите номер таблицы, с которой хотите работать" +
                    "\n1 - Студенты \t 2 - Группы \t 3 - Преподаватели" +
                    "\nДля выхода из программы введите 0");
                try {
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
                            exit = !exit;
                            break;
                        } default : {
                            System.out.println("Введите номер таблицы из " +
                                    "представленных ниже");
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Некорректный ввод");
                    in.nextLine();
                }
        }
    }

    public static void studentWork() throws SQLException {
        boolean back = true;
        while (back) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t 4 - Удаление" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
                        Student student = new Student();
                        System.out.println("Выберите критерий поиска" +
                                "\n1 - ID \t 2 - Ф.И.О. \t 3 - Дата рождения" +
                                "\t 4 - Группа \t 5 - Все");
                        try {
                            int criterion = in.nextInt();
                            in.nextLine();
                            switch (criterion) {
                                case 1: {
                                    System.out.println("Введите ID студента");

                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();

                                        student.get(id);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите Ф.И.О. студента");
                                    String nameSearch = in.nextLine();

                                    StudentBase studentBase = new StudentBase();
                                    List<Student> students =
                                            studentBase.selectStudent(nameSearch);
                                    for(Student e : students){
                                        System.out.println(e);
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.println("Введите дату рождения студента " +
                                            "через пробел");
                                    try {
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();

                                        List<Student> students =
                                                student.get(day, month, year);
                                        for(Student e : students){
                                            System.out.println(e);
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (DateTimeException e) {
                                        System.out.println("Неккоректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 4: {
                                    System.out.println("Введите номер группы");

                                    try {
                                        int numberGroup = in.nextInt();
                                        in.nextLine();
                                        Group group = new Group();
                                        group.getByNumber(numberGroup);

                                        StudentBase studentBase = new StudentBase();
                                        List<Student> students =
                                                studentBase.selectGroup(group.getId());
                                        for(Student e : students){
                                            System.out.println(e);
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неккоректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 5: {
                                    StudentBase studentBase = new StudentBase();
                                    List<Student> students =
                                            studentBase.selectStudent();
                                    for(Student e : students){
                                        System.out.println(e);
                                    }
                                    break;
                                } default : {
                                    System.out.println("Вами был выбран" +
                                            " неверный критерий");
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите данные о студенте");

                        try {
                            System.out.println("Ф.И.О.");
                            String name = in.nextLine();

                            System.out.println("Дату рождения через пробел");
                            int day = in.nextInt();
                            int month = in.nextInt();
                            int year = in.nextInt();
                            in.nextLine();
                            LocalDate date = LocalDate.of(year, month, day);

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
                                    date, male, group.getId());
                            StudentBase studentBase = new StudentBase();
                            studentBase.insert(student);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                        } catch (SQLException e) {
                            System.out.println("Группы с данным номером не существует");
                        } catch (DateTimeException e) {
                            System.out.println("Некорректный ввод");
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Введите номер поля для изменения записи о студенте" +
                                "\n1 - Ф.И.О. \t 2 - Дата рождения \t 3 - Пол \t 4 - Группа" +
                                "\n Введите 0, чтобы вернуться");
                        try {
                            int numberAtribut = in.nextInt();
                            in.nextLine();
                            switch (numberAtribut) {
                                case 1: {
                                    System.out.println("Введите ID студента");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Student student = new Student();
                                        student.get(id);

                                        System.out.println("Новое Ф.И.О.");
                                        String name = in.nextLine();

                                        student.setNameStudent(name);
                                        StudentBase studentBase = new StudentBase();
                                        studentBase.update(student);
                                    } catch (SQLException e) {
                                        System.out.println("Студента с данным ID " +
                                                "не существует");
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите ID студента");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Student student = new Student();
                                        student.get(id);

                                        System.out.println("Новая дата рождения");
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();
                                        LocalDate date = LocalDate.of(year, month, day);

                                        student.setBirthdayStudent(date);
                                        StudentBase studentBase = new StudentBase();
                                        studentBase.update(student);
                                    } catch (SQLException e) {
                                        System.out.println("Студента с данным ID " +
                                                "не существует");
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (DateTimeException e){
                                        System.out.println("Некорректный ввод");
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.println("Введите ID студента");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();

                                        System.out.println("Пол (Одной буквой)");
                                        String gender = in.nextLine();
                                        Student student = new Student();
                                        student.get(id);

                                        Gender male;
                                        if (Gender.MAN.getValue().equals(gender)) {
                                            male = Gender.MAN;
                                        } else {
                                            male = Gender.WOMAN;
                                        }

                                        student.setGenderStudent(male);
                                        StudentBase studentBase = new StudentBase();
                                        studentBase.update(student);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Студента с данным ID " +
                                                "не существует");
                                    }
                                    break;
                                }
                                case 4: {
                                    System.out.println("Введите ID студента");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Student student = new Student();
                                        student.get(id);

                                        System.out.println("Новый номер группы");
                                        int number = in.nextInt();
                                        in.nextLine();
                                        Group group = new Group();
                                        group.getByNumber(number);

                                        student.setGroupStudent(group.getId());
                                        StudentBase studentBase = new StudentBase();
                                        studentBase.update(student);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Студента или группы" +
                                                " с данным ID " +
                                                "не существует");
                                    }
                                    break;
                                }
                                default : {
                                    System.out.println("Введён неверный номер атрибута");
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Введите ID студента");
                        try {
                            int id = in.nextInt();
                            in.nextLine();
                            Student student = new Student();
                            student.get(id);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этом студенте\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    StudentBase studentBase = new StudentBase();
                                    studentBase.delete(student);
                                    break;
                                }
                                case "N": {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (SQLException e) {
                            System.out.println("Студента с данным ID " +
                                    "не существует");
                        }
                        break;
                    }
                    case 0: {
                        back = !back;
                        break;
                    } default : {
                        System.out.println("Введите номер операции " +
                                "из представленных ниже");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод");
                in.nextLine();
            }
        }
    }

    public static void teacherWork() throws SQLException {
        boolean back = true;
        while (back) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Группы преподвателя" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
                        Teacher teacher = new Teacher();
                        System.out.println("Выберите критерий поиска" +
                                "\n1 - ID \t 2 - Ф.И.О. \t 3 - Дата рождения" +
                                "\t 4 - Все");
                        try {
                            int criterion = in.nextInt();
                            in.nextLine();
                            switch (criterion) {
                                case 1: {
                                    System.out.println("Введите ID преподавателя");

                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();

                                        teacher.get(id);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Преподавателя " +
                                                "с данным ID е существует");
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите Ф.И.О. преподавателя");
                                    String nameSearch = in.nextLine();
                                    TeacherBase teacherBase = new TeacherBase();
                                    List<Teacher> teachers =
                                            teacherBase.selectTeacher(nameSearch);
                                    for(Teacher e : teachers){
                                        System.out.println(e);
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.println("Введите дату рождения " +
                                            "преподавателя через пробел");

                                    try {
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();

                                        List<Teacher> teachers =
                                                teacher.get(day, month, year);
                                        for(Teacher e : teachers){
                                            System.out.println(e);
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 4: {
                                    TeacherBase teacherBase = new TeacherBase();
                                    List<Teacher> teachers =
                                        teacherBase.selectTeacher();
                                    for(Teacher e : teachers){
                                        System.out.println(e);
                                    }
                                    break;
                                } default : {
                                    System.out.println("Введён номер " +
                                            "не существующего критерия");
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите данные о преподавателе");

                        try {
                            System.out.println("Ф.И.О.");
                            String name = in.nextLine();

                            System.out.println("Дату рождения через пробел");
                            int day = in.nextInt();
                            int month = in.nextInt();
                            int year = in.nextInt();
                            in.nextLine();
                            LocalDate date = LocalDate.of(year, month, day);

                            System.out.println("Пол одной буквой");
                            String gender = in.nextLine();
                            Gender male;
                            if (Gender.MAN.getValue().equals(gender)) {
                                male = Gender.MAN;
                            } else {
                                male = Gender.WOMAN;
                            }

                            Teacher teacher = new Teacher(name,
                                    date, male);
                            TeacherBase teacherBase = new TeacherBase();
                            teacherBase.insert(teacher);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (DateTimeException e) {
                            System.out.println("Некорректный ввод");
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Введите номер поля для изменения записи о преподавтеле" +
                                "\n1 - Ф.И.О. \t 2 - Дата рождения \t 3 - Пол" +
                                "\n Введите 0, чтобы вернуться");
                        try {
                            int numberAtribut = in.nextInt();
                            in.nextLine();
                            switch (numberAtribut) {
                                case 1: {
                                    System.out.println("Введите ID преподавтеля");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Teacher teacher = new Teacher();
                                        teacher.get(id);

                                        System.out.println("Новое Ф.И.О.");
                                        String name = in.nextLine();

                                        teacher.setNameTeacher(name);
                                        TeacherBase teacherBase = new TeacherBase();
                                        teacherBase.update(teacher);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Преподавателя " +
                                                "с данным ID не существует");
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите ID преподавтеля");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Teacher teacher = new Teacher();
                                        teacher.get(id);

                                        System.out.println("Новая дата рождения");
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();
                                        LocalDate date = LocalDate.of(year, month, day);

                                        teacher.setBirthdayTeacher(date);
                                        TeacherBase teacherBase = new TeacherBase();
                                        teacherBase.update(teacher);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Преподавателя " +
                                                "с данным ID не существует");
                                    } catch (DateTimeException e) {
                                        System.out.println("Некорректный ввод");
                                    }
                                    break;
                                }
                                case 3: {
                                    System.out.println("Введите ID преподавтеля");
                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();
                                        Teacher teacher = new Teacher();
                                        teacher.get(id);

                                        System.out.println("Пол (Одной буквой)");
                                        String gender = in.nextLine();

                                        Gender male;
                                        if (Gender.MAN.getValue().equals(gender)) {
                                            male = Gender.MAN;
                                        } else {
                                            male = Gender.WOMAN;
                                        }

                                        teacher.setGenderTeacher(male);
                                        TeacherBase teacherBase = new TeacherBase();
                                        teacherBase.update(teacher);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Преподавателя " +
                                                "с данным ID не существует");
                                    }
                                    break;
                                }
                                default : {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Введите ID преподавателя");
                        try {
                            int id = in.nextInt();
                            in.nextLine();
                            Teacher teacher = new Teacher();
                            teacher.get(id);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этом преподавателе\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    TeacherBase teacherBase = new TeacherBase();
                                    teacherBase.delete(teacher);
                                    break;
                                }
                                case "N": {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (SQLException e) {
                            System.out.println("Преподавателя " +
                                    "с данным ID не существует");
                        }
                        break;
                    }
                    case 5: {
                        boolean backMenuGroupTeacher = true;
                        while (backMenuGroupTeacher) {
                            System.out.println("Выберите операцию для дальнейшей работы" +
                                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                    "4 - Удаление" +
                                    "\nДля выхода из таблицы введите 0");
                            try {
                                int operationWithListGroup = in.nextInt();
                                in.nextLine();

                                Teacher teacher = new Teacher();

                                switch (operationWithListGroup) {
                                    case 1 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
                                            int id = in.nextInt();
                                            in.nextLine();

                                            teacher.get(id);
                                            TeacherBase teacherBase = new TeacherBase();
                                            teacherBase.selectGroups(teacher);
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Преподавателя " +
                                                    "с данным ID не существует");
                                        }
                                        break;
                                    }
                                    case 2 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
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

                                            TeacherBase teacherBase = new TeacherBase();
                                            teacherBase.insertGroup(teacher, group.getId());
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Преподавателя или группы " +
                                                    "с данным ID не существует");
                                        }
                                        break;
                                    }
                                    case 3 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
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


                                            TeacherBase teacherBase = new TeacherBase();
                                            teacherBase.updateGroup(teacher, numberOldGroup, numberNewGroup);
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Преподавателя или группы " +
                                                    "с данным ID не существует");
                                        }
                                        break;
                                    }
                                    case 4 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
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

                                            TeacherBase teacherBase = new TeacherBase();
                                            teacherBase.deleteGroup(teacher, group.getId());
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Преподавателя или группы " +
                                                    "с данным ID не существует");
                                        }
                                        break;
                                    }
                                    case 0 : {
                                        backMenuGroupTeacher = !backMenuGroupTeacher;
                                        break;
                                    } default : {
                                        System.out.println("Введите номер операции " +
                                                "из представленных ниже");
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Некорректный ввод");
                                in.nextLine();
                            }
                        }
                    }
                    case 0: {
                        back = false;
                        break;
                    }
                    default: {
                        System.out.println("Введите номер операции" +
                                " из приведённых ниже");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод");
                in.nextLine();
            }
        }
    }

    public static void groupWork() throws SQLException {
        boolean back = true;
        while (back) {
            System.out.println("Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Прпеодаватели группы" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
                        Group group = new Group();
                        System.out.println("Выберите критерий поиска" +
                                "\n1 - ID \t 2 - Номер группы \t 3 - Все");
                        try {
                            int criterion = in.nextInt();
                            in.nextLine();
                            switch (criterion) {
                                case 1: {
                                    System.out.println("Введите ID группы");

                                    try {
                                        int id = in.nextInt();
                                        in.nextLine();

                                        group.getById(id);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Группы с данным ID не существует");
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите номер группы");

                                    try {
                                        int number = in.nextInt();
                                        in.nextLine();

                                        group.getByNumber(number);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Группы с данным номером не существует");
                                    }
                                    break;
                                }
                                case 3: {
                                    GroupBase groupBase = new GroupBase();
                                    List<Group> groups = groupBase.select();
                                    for(Group e : groups) {
                                        System.out.println(e);
                                    }
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Введите номер группы");
                        try {
                            int number = in.nextInt();
                            in.nextLine();

                            Group group = new Group(number);
                            GroupBase groupBase = new GroupBase();
                            groupBase.insert(group);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Введите номер группы");
                        try {
                            int number = in.nextInt();
                            in.nextLine();
                            Group group = new Group();
                            group.getByNumber(number);

                            System.out.println("Новый номер группы");
                            int newNumber = in.nextInt();
                            in.nextLine();

                            group.set(newNumber);
                            GroupBase groupBase = new GroupBase();
                            groupBase.update(group);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (SQLException e) {
                            System.out.println("Группы с данным номером не существует");
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Введите номер группы");
                        try {
                            int number = in.nextInt();
                            in.nextLine();
                            Group group = new Group();
                            group.getByNumber(number);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этой группе\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    GroupBase groupBase = new GroupBase();
                                    groupBase.delete(group);
                                    break;
                                }
                                case "N": {
                                    break;
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (SQLException e) {
                            System.out.println("Группы с данным номером не существует");
                        }
                        break;
                    }
                    case 5: {
                        boolean backMenuTeacherGroup = true;
                        while (backMenuTeacherGroup) {
                            System.out.println("Выберите операцию для дальнейшей работы" +
                                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                    "4 - Удаление" +
                                    "\nДля выхода из таблицы введите 0");
                            try {
                                int operationWithListTeacher = in.nextInt();
                                in.nextLine();

                                Group group = new Group();

                                switch (operationWithListTeacher) {
                                    case 1: {
                                        System.out.println("Введите номер группы");
                                        try {
                                            int number = in.nextInt();
                                            in.nextLine();

                                            group.getByNumber(number);
                                            GroupBase groupBase = new GroupBase();
                                            groupBase.selectTeacher(group);
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Группы с данным номером не существует");
                                        }
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("Введите номер группы");
                                        try {
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
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Группы или преподавателя " +
                                                    "с данным номером или ID не существует");
                                        }
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("Введите номер группы");

                                        try {
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
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Группы или преподавателя " +
                                                    "с данным номером или ID не существует");
                                        }
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("Введите номер группы");
                                        try {
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
                                        } catch (InputMismatchException e) {
                                            System.out.println("Некорректный ввод");
                                            in.nextLine();
                                        } catch (SQLException e) {
                                            System.out.println("Группы или преподавателя " +
                                                    "с данным номером или ID не существует");
                                        }
                                        break;
                                    }
                                    case 0: {
                                        backMenuTeacherGroup = !backMenuTeacherGroup;
                                        break;
                                    } default : {
                                        break;
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Некорректный ввод");
                                in.nextLine();
                            }
                        }
                    }
                    case 0: {
                        back = !back;
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод");
                in.nextLine();
            }
        }
    }
}
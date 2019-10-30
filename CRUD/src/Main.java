import objectForStrokeBase.Group;
import objectForStrokeBase.Gender;
import objectForStrokeBase.Student;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroapDAO;
import workWithBase.daoClasses.StudentDAO;
import workWithBase.daoClasses.TeacherDAO;

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
            System.out.println("\nMAIN MENU\n" +
                    "Выберите номер таблицы, с которой хотите работать" +
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
            System.out.println("\nSTUDENTS\n" +
                    "Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t 4 - Удаление" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
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

                                        Student student =
                                                new StudentDAO().selectStudent(id);
                                        System.out.println(student);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Студента " +
                                                "с данным ID не существует");
                                    }
                                    break;
                                }
                                case 2: {
                                    System.out.println("Введите Ф.И.О. студента");
                                    String nameSearch = in.nextLine();

                                    StudentDAO studentBase = new StudentDAO();

                                    List<Student> students =
                                            studentBase.selectStudent(nameSearch);
                                    if(students.size() > 0) {
                                        for (Student e : students) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("Нет записей");
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
                                                new StudentDAO().selectStudent(
                                                        LocalDate.of(year, month, day)
                                                );
                                        if(students.size() > 0) {
                                            for (Student e : students) {
                                                System.out.println(e);
                                            }
                                        } else {
                                            System.out.println("Нет записей");
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
                                        Group group =
                                                new GroapDAO().selectGroupByNumber(numberGroup);

                                        StudentDAO studentBase = new StudentDAO();

                                        List<Student> students =
                                                studentBase.selectGroup(group.getId());
                                        if(students.size() > 0) {
                                            for (Student e : students) {
                                                System.out.println(e);
                                            }
                                        } else {
                                            System.out.println("Нет записей");
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Неккоректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Возможно вы ввели неверный номер группы");
                                    }
                                    break;
                                }
                                case 5: {
                                    StudentDAO studentBase = new StudentDAO();

                                    List<Student> students =
                                            studentBase.selectStudent();
                                    if(students.size() > 0) {
                                        for (Student e : students) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("Нет записей");
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

                            Group group =
                                    new GroapDAO().selectGroupByNumber(numberGroup);

                            Student student = new Student(name,
                                    date, male, group);

                            StudentDAO studentBase = new StudentDAO();

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
                                        Student student =
                                                new StudentDAO().selectStudent(id);

                                        System.out.println("Новое Ф.И.О.");
                                        String name = in.nextLine();

                                        student.setNameStudent(name);

                                        StudentDAO studentBase = new StudentDAO();

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
                                        Student student =
                                                new StudentDAO().selectStudent(id);

                                        System.out.println("Новая дата рождения");
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();
                                        LocalDate date = LocalDate.of(year, month, day);

                                        student.setBirthdayStudent(date);

                                        StudentDAO studentBase = new StudentDAO();

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
                                        Student student =
                                                new StudentDAO().selectStudent(id);
                                        Gender male;
                                        if (Gender.MAN.getValue().equals(gender)) {
                                            male = Gender.MAN;
                                        } else {
                                            male = Gender.WOMAN;
                                        }

                                        student.setGenderStudent(male);

                                        StudentDAO studentBase = new StudentDAO();

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

                                        Student student =
                                                new StudentDAO().selectStudent(id);

                                        System.out.println("Новый номер группы");
                                        int number = in.nextInt();
                                        in.nextLine();

                                        Group group =
                                                new GroapDAO().selectGroupByNumber(number);

                                        student.setGroupStudent(group.getId());

                                        StudentDAO studentBase = new StudentDAO();

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
                            Student student =
                                    new StudentDAO().selectStudent(id);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этом студенте\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    StudentDAO studentBase = new StudentDAO();
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
            System.out.println("\nTEACHERS\n" +
                    "Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Группы преподвателя" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
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

                                        Teacher teacher =
                                                new TeacherDAO().selectTeacher(id);

                                        System.out.println(teacher);
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
                                    TeacherDAO teacherBase = new TeacherDAO();

                                    List<Teacher> teachers =
                                            teacherBase.selectTeacher(nameSearch);
                                    if(teachers.size() > 0) {
                                        for (Teacher e : teachers) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("Нет записей");
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
                                                new TeacherDAO().selectTeacher(
                                                        LocalDate.of(year, month, day)
                                                );

                                        if(teachers.size() > 0) {
                                            for (Teacher e : teachers) {
                                                System.out.println(e);
                                            }
                                        } else {
                                            System.out.println("Нет записей");
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    }
                                    break;
                                }
                                case 4: {
                                    TeacherDAO teacherBase = new TeacherDAO();

                                    List<Teacher> teachers =
                                        teacherBase.selectTeacher();

                                    if(teachers.size() > 0) {
                                        for (Teacher e : teachers) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("Нет записей");
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

                            TeacherDAO teacherBase = new TeacherDAO();

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
                                        Teacher teacher =
                                                new TeacherDAO().selectTeacher(id);

                                        System.out.println("Новое Ф.И.О.");
                                        String name = in.nextLine();

                                        teacher.setNameTeacher(name);

                                        TeacherDAO teacherBase = new TeacherDAO();

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

                                        Teacher teacher =
                                                new TeacherDAO().selectTeacher(id);

                                        System.out.println("Новая дата рождения");
                                        int day = in.nextInt();
                                        int month = in.nextInt();
                                        int year = in.nextInt();
                                        in.nextLine();
                                        LocalDate date = LocalDate.of(year, month, day);

                                        teacher.setBirthdayTeacher(date);

                                        TeacherDAO teacherBase = new TeacherDAO();

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

                                        Teacher teacher =
                                                new TeacherDAO().selectTeacher(id);

                                        System.out.println("Пол (Одной буквой)");
                                        String gender = in.nextLine();

                                        Gender male;
                                        if (Gender.MAN.getValue().equals(gender)) {
                                            male = Gender.MAN;
                                        } else {
                                            male = Gender.WOMAN;
                                        }

                                        teacher.setGenderTeacher(male);

                                        TeacherDAO teacherBase = new TeacherDAO();

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

                            Teacher teacher =
                                    new TeacherDAO().selectTeacher(id);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этом преподавателе\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    TeacherDAO teacherBase = new TeacherDAO();
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
                            System.out.println("\nGROUPS OF TEACHERS\n" +
                                    "Выберите операцию для дальнейшей работы" +
                                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                    "4 - Удаление" +
                                    "\nДля выхода из таблицы введите 0");
                            try {
                                int operationWithListGroup = in.nextInt();
                                in.nextLine();

                                switch (operationWithListGroup) {
                                    case 1 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
                                            int id = in.nextInt();
                                            in.nextLine();

                                            Teacher teacher =
                                                    new TeacherDAO().selectTeacher(id);

                                            TeacherDAO teacherBase = new TeacherDAO();

                                            List<Group> groups =
                                                teacherBase.selectGroups(teacher);
                                            if(groups.size() > 0) {
                                                for (Group e : groups) {
                                                    System.out.println(e);
                                                }
                                            } else {
                                                System.out.println("Нет записей");
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
                                    case 2 : {
                                        System.out.println("Введите ID преподавателя");
                                        try {
                                            int id = in.nextInt();
                                            in.nextLine();

                                            Teacher teacher =
                                                    new TeacherDAO().selectTeacher(id);

                                            System.out.println("Введите номер группы," +
                                                    " который вы хотите присвоить" +
                                                    " преподавателю");
                                            int number = in.nextInt();
                                            in.nextLine();
                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            TeacherDAO teacherBase = new TeacherDAO();

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

                                            Teacher teacher =
                                                    new TeacherDAO().selectTeacher(id);

                                            System.out.println("Введите номер группы," +
                                                    " который вы хотите изменить" +
                                                    " преподаватель");
                                            int numberOldGroup = in.nextInt();
                                            in.nextLine();

                                            System.out.println("Введите новый номер группы");
                                            int numberNewGroup = in.nextInt();
                                            in.nextLine();

                                            Group oldGroup =
                                                    new GroapDAO().selectGroupByNumber(numberOldGroup);
                                            numberOldGroup = oldGroup.getId();

                                            Group newGroup =
                                                    new GroapDAO().selectGroupByNumber(numberNewGroup);
                                            numberNewGroup = newGroup.getId();


                                            TeacherDAO teacherBase = new TeacherDAO();

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

                                            Teacher teacher =
                                                    new TeacherDAO().selectTeacher(id);

                                            System.out.println("Введите номер группы," +
                                                    " который вы хотите удалить" +
                                                    " преподавателю");
                                            int number = in.nextInt();
                                            in.nextLine();
                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            TeacherDAO teacherBase = new TeacherDAO();

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
                        break;
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
            System.out.println("\nGROUPS\n" +
                    "Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Прпеодаватели группы" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
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

                                        Group group = new GroapDAO().selectGroupById(id);

                                        System.out.println(group);
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

                                        Group group =
                                                new GroapDAO().selectGroupByNumber(number);

                                        System.out.println(group);
                                    } catch (InputMismatchException e) {
                                        System.out.println("Некорректный ввод");
                                        in.nextLine();
                                    } catch (SQLException e) {
                                        System.out.println("Группы с данным номером не существует");
                                    }
                                    break;
                                }
                                case 3: {
                                    GroapDAO groupBase = new GroapDAO();

                                    List<Group> groups = groupBase.select();

                                    if(groups.size() > 0) {
                                        for (Group e : groups) {
                                            System.out.println(e);
                                        }
                                    } else {
                                        System.out.println("Нет записей");
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

                            GroapDAO groupBase = new GroapDAO();

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

                            Group group =
                                    new GroapDAO().selectGroupByNumber(number);

                            System.out.println("Новый номер группы");
                            int newNumber = in.nextInt();
                            in.nextLine();

                            group.set(newNumber);

                            GroapDAO groupBase = new GroapDAO();

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

                            Group group =
                                    new GroapDAO().selectGroupByNumber(number);

                            System.out.println("Вы уверены, что хотите удалить запись " +
                                    "об этой группе\n Y/N");
                            String answer = in.nextLine();
                            switch (answer) {
                                case "Y": {
                                    GroapDAO groupBase = new GroapDAO();
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
                            System.out.println("\nTEACHERS OF GROUPS\n" +
                                    "Выберите операцию для дальнейшей работы" +
                                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                                    "4 - Удаление" +
                                    "\nДля выхода из таблицы введите 0");
                            try {
                                int operationWithListTeacher = in.nextInt();
                                in.nextLine();

                                switch (operationWithListTeacher) {
                                    case 1: {
                                        System.out.println("Введите номер группы");
                                        try {
                                            int number = in.nextInt();
                                            in.nextLine();

                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            GroapDAO groupBase = new GroapDAO();

                                            List<Teacher> teachers =
                                                    groupBase.selectTeacher(group);
                                            if(teachers.size() > 0) {
                                                for (Teacher e : teachers) {
                                                    System.out.println(e);
                                                }
                                            } else {
                                                System.out.println("Нет записей");
                                            }
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

                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            System.out.println("Введите ID реподавателя," +
                                                    " которого вы хотите присвоить" +
                                                    " группе");
                                            int id = in.nextInt();
                                            in.nextLine();

                                            GroapDAO groupBase = new GroapDAO();

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

                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            System.out.println("Введите ID преподавателя," +
                                                    " которого вы хотите заменить" +
                                                    " группе");
                                            int idOldTeacher = in.nextInt();
                                            in.nextLine();

                                            System.out.println("Введите ID нового преподавателя");
                                            int idNewTeacher = in.nextInt();
                                            in.nextLine();

                                            GroapDAO groupBase = new GroapDAO();

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

                                            Group group =
                                                    new GroapDAO().selectGroupByNumber(number);

                                            System.out.println("Введите ID преподавателя," +
                                                    " которого вы хотите удалить" +
                                                    " группе");
                                            int idTeacher = in.nextInt();
                                            in.nextLine();

                                            GroapDAO groupBase = new GroapDAO();

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
                        break;
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
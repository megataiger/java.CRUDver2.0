package menuForTables;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Algorithm for work with CRUD-operations
 * table "teacher"
 */

public class TeacherMenu {
    private Scanner in;
    private Checkers check;

    public TeacherMenu(Scanner in) {
        this.in = in;
        check = new Checkers(in);
    }

    public void teacherWork() {
        boolean back = true;
        while (back) {
            System.out.println("\nTEACHERS\n" +
                    "Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Группы преподавателя" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
                        selectTeacher();
                        break;
                    }
                    case 2: {
                        insertTeacher();
                        break;
                    }
                    case 3: {
                        updateTeacher();
                        break;
                    }
                    case 4: {
                        deleteTeacher();
                        break;
                    }
                    case 5: {
                        workWithGroupsOfTeacher();
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

    public void selectTeacher() {
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
                                new TeacherDAO().findById(id);

                        System.out.println(teacher);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
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
                            teacherBase.findByName(nameSearch);
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
                            "преподавателя через пробел DD MM YYYY");

                    LocalDate birthday = null;
                    while (birthday == null) {
                        birthday = check.checkCorrectDate();
                    }

                    List<Teacher> teachers =
                            new TeacherDAO().findByDate(
                                    birthday
                            );

                    if(teachers.size() > 0) {
                        for (Teacher e : teachers) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Нет записей");
                    }
                    break;
                }
                case 4: {
                    TeacherDAO teacherBase = new TeacherDAO();

                    List<Teacher> teachers =
                            teacherBase.getAll();

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
    }

    public void insertTeacher() {
        System.out.println("Введите данные о преподавателе");
        System.out.println("Ф.И.О.");
        String name = in.nextLine();

        LocalDate date = null;
        while (date == null) {
            date = check.checkCorrectDate();
        }

        Gender gender = null;
        while (gender == null) {
            gender = check.checkCorrectGender();
        }

        Teacher teacher = new Teacher(name,
                date, gender);

        TeacherDAO teacherBase = new TeacherDAO();

        teacherBase.save(teacher);
    }

    public void updateTeacher() {
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
                                new TeacherDAO().findById(id);

                        System.out.println("Новое Ф.И.О.");
                        String name = in.nextLine();

                        teacher.setNameTeacher(name);

                        TeacherDAO teacherBase = new TeacherDAO();

                        teacherBase.update(teacher);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
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
                                new TeacherDAO().findById(id);

                        LocalDate date = null;
                        while (date == null) {
                            date = check.checkCorrectDate();
                        }

                        teacher.setBirthdayTeacher(date);

                        TeacherDAO teacherBase = new TeacherDAO();

                        teacherBase.update(teacher);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
                        System.out.println("Преподавателя " +
                                "с данным ID не существует");
                    }
                    break;
                }
                case 3: {
                    System.out.println("Введите ID преподавтеля");
                    try {
                        int id = in.nextInt();
                        in.nextLine();

                        Teacher teacher =
                                new TeacherDAO().findById(id);

                        Gender gender = null;
                        while (gender == null) {
                            gender = check.checkCorrectGender();
                        }

                        teacher.setGenderTeacher(gender);

                        TeacherDAO teacherBase = new TeacherDAO();

                        teacherBase.update(teacher);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
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
    }

    public void deleteTeacher() {
        System.out.println("Введите ID преподавателя");
        try {
            int id = in.nextInt();
            in.nextLine();

            Teacher teacher =
                    new TeacherDAO().findById(id);

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
        } catch (PersistenceException e) {
            System.out.println("Преподавателя " +
                    "с данным ID не существует");
        }
    }

    public void workWithGroupsOfTeacher() {
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
                                    new TeacherDAO().findById(id);

                            List<Group> groups =
                                    teacher.getGroups();
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
                        } catch (PersistenceException e) {
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
                                    new TeacherDAO().findById(id);

                            System.out.println("Введите номер группы," +
                                    " который вы хотите присвоить" +
                                    " преподавателю");
                            int number = in.nextInt();
                            in.nextLine();
                            Group group =
                                    new GroupDAO().selectGroupByNumber(number);
                            teacher.addGroup(group);

                            TeacherDAO teach = new TeacherDAO();
                            teach.update(teacher);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
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
                                    new TeacherDAO().findById(id);

                            System.out.println("Введите номер группы," +
                                    " который вы хотите изменить" +
                                    " преподаватель");
                            int numberOldGroup = in.nextInt();
                            in.nextLine();

                            System.out.println("Введите новый номер группы");
                            int numberNewGroup = in.nextInt();
                            in.nextLine();

                            Group oldGroup =
                                    new GroupDAO().selectGroupByNumber(numberOldGroup);

                            Group newGroup =
                                    new GroupDAO().selectGroupByNumber(numberNewGroup);

                            teacher.setGroup(oldGroup, newGroup);

                            TeacherDAO teacherBase = new TeacherDAO();

                            teacherBase.update(teacher);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
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
                                    new TeacherDAO().findById(id);

                            System.out.println("Введите номер группы," +
                                    " который вы хотите удалить" +
                                    " преподавателю");
                            int number = in.nextInt();
                            in.nextLine();
                            Group group =
                                    new GroupDAO().selectGroupByNumber(number);

                            teacher.removeGroup(group);

                            TeacherDAO teacherBase = new TeacherDAO();

                            teacherBase.update(teacher);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
                            System.out.println("Преподавателя или группы " +
                                    "с данным ID не существует");
                        }
                        break;
                    }
                    case 0 : {
                        backMenuGroupTeacher = false;
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
}

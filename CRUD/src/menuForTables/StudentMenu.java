package menuForTables;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Algorithm for work with CRUD-operations
 * table "student".
 */

public class StudentMenu {
    private Scanner in;
    private Checkers check;
    private StudentDAO studentDAO = new StudentDAO();

    public StudentMenu(Scanner in) {
        this.in = in;
        check = new Checkers(in);
    }

    public void studentWork() {
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
                        selectStudent();
                        break;
                    }
                    case 2: {
                        insertStudent();
                        break;
                    }
                    case 3: {
                        updateStudent();
                        break;
                    }
                    case 4: {
                        deleteStudent();
                        break;
                    }
                    case 0: {
                        back = false;
                        break;
                    }
                    default: {
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

    private void selectStudent() {
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
                                studentDAO.findById(id);
                        System.out.println(student);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
                        System.out.println("Студента " +
                                "с данным ID не существует");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Введите Ф.И.О. студента");
                    String nameSearch = in.nextLine();

                    List students =
                            studentDAO.findByName(nameSearch);
                    if (students.size() > 0) {
                        for (Object e : students) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Нет записей");
                    }
                    break;
                }
                case 3: {
                    LocalDate birthday = null;
                    while (birthday == null) {
                        birthday = check.checkCorrectDate();
                    }

                    List students =
                            studentDAO.findByDate(birthday);
                    if (students.size() > 0) {
                        for (Object e : students) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Нет записей");
                    }
                    break;
                }
                case 4: {
                    Group group = null;
                    while (group == null) {
                        group = check.checkGroup();
                    }

                    List students =
                            studentDAO.findByGroup(group);
                    if (students.size() > 0) {
                        for (Object e : students) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Нет записей");
                    }
                    break;
                }
                case 5: {
                    List students =
                            studentDAO.getAll();
                    if (students.size() > 0) {
                        for (Object e : students) {
                            System.out.println(e);
                        }
                    } else {
                        System.out.println("Нет записей");
                    }
                    break;
                }
                default: {
                    System.out.println("Вами был выбран" +
                            " неверный критерий");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        }
    }

    private void insertStudent() {
        System.out.println("Введите данные о студенте");

        try {
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

            Group group = null;
            while (group == null) {
                group = check.checkGroup();
            }

            Student student = new Student(name,
                    date, gender, group);

            studentDAO.save(student);
        } catch (PersistenceException e) {
            System.out.println("Группы с данным номером не существует");
        }
    }

    private void updateStudent() {
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
                                studentDAO.findById(id);

                        System.out.println("Новое Ф.И.О.");
                        String name = in.nextLine();

                        student.setNameStudent(name);

                        studentDAO.update(student);
                    } catch (PersistenceException e) {
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
                                studentDAO.findById(id);

                        LocalDate date = null;
                        while (date == null) {
                            date = check.checkCorrectDate();
                        }

                        student.setBirthdayStudent(date);

                        studentDAO.update(student);
                    } catch (PersistenceException e) {
                        System.out.println("Студента с данным ID " +
                                "не существует");
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    }
                    break;
                }
                case 3: {
                    System.out.println("Введите ID студента");
                    try {
                        int id = in.nextInt();
                        in.nextLine();

                        Student student =
                                studentDAO.findById(id);
                        Gender gender = null;
                        while (gender == null) {
                            gender = check.checkCorrectGender();
                        }

                        student.setGenderStudent(gender);

                        studentDAO.update(student);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
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
                                studentDAO.findById(id);

                        Group group = null;
                        while (group == null) {
                            group = check.checkGroup();
                        }

                        student.setGroupStudent(group);

                        studentDAO.update(student);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
                        System.out.println("Студента или группы" +
                                " с данным ID " +
                                "не существует");
                    }
                    break;
                }
                default: {
                    System.out.println("Введён неверный номер атрибута");
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        }
    }

    private void deleteStudent() {
        System.out.println("Введите ID студента");
        try {
            int id = in.nextInt();
            in.nextLine();
            Student student =
                    studentDAO.findById(id);

            System.out.println("Вы уверены, что хотите удалить запись " +
                    "об этом студенте\n Y/N?");
            String answer = in.nextLine().toUpperCase();
            switch (answer) {
                case "Y": {
                    studentDAO.delete(student);
                    break;
                }
                case "N": {
                    break;
                }
                default: {
                    System.out.println("Вы ввели неверный ответ");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        } catch (PersistenceException e) {
            System.out.println("Студента с данным ID " +
                    "не существует");
        }
    }
}

package menuForTables;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.persistence.PersistenceException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Algorithm for work with CRUD-operations
 * table "group"
 */

public class GroupMenu {
    private Scanner in;
    private GroupDAO groupDAO = new GroupDAO();

    public GroupMenu(Scanner in) {
        this.in = in;
    }

    public void groupWork() {
        boolean back = true;
        while (back) {
            System.out.println("\nGROUPS\n" +
                    "Выберите операцию для дальнейшей работы" +
                    "\n 1 - Поиск \t 2 - Вставка \t 3 - Изменение \t " +
                    "4 - Удаление \t 5 - Преподаватели группы" +
                    "\nДля выхода из таблицы введите 0");
            try {
                int operation = in.nextInt();
                in.nextLine();
                switch (operation) {
                    case 1: {
                        selectGroup();
                        break;
                    }
                    case 2: {
                        insertGroup();
                        break;
                    }
                    case 3: {
                        updateGroup();
                        break;
                    }
                    case 4: {
                        deleteGroup();
                        break;
                    }
                    case 5: {
                        workWithTeachersOfGroup();
                        break;
                    }
                    case 0: {
                        back = false;
                        groupDAO.close();
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод");
                in.nextLine();
            }
        }
    }

    private void selectGroup() {
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

                        Group group = groupDAO.findById(id);

                        System.out.println(group);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
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
                                groupDAO.selectGroupByNumber(number);

                        System.out.println(group);
                    } catch (InputMismatchException e) {
                        System.out.println("Некорректный ввод");
                        in.nextLine();
                    } catch (PersistenceException e) {
                        System.out.println("Группы с данным номером не существует");
                    }
                    break;
                }
                case 3: {
                    List groups = groupDAO.getAll();

                    if (groups.size() > 0) {
                        for (Object e : groups) {
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
    }

    private void insertGroup() {
        System.out.println("Введите номер группы");
        try {
            int number = in.nextInt();
            in.nextLine();

            Group group = new Group(number);

            groupDAO.save(group);
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        }
    }

    private void updateGroup() {
        System.out.println("Введите номер группы");
        try {
            int number = in.nextInt();
            in.nextLine();

            Group group =
                    groupDAO.selectGroupByNumber(number);

            System.out.println("Новый номер группы");
            int newNumber = in.nextInt();
            in.nextLine();

            group.set(newNumber);

            groupDAO.update(group);
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        } catch (PersistenceException e) {
            System.out.println("Группы с данным номером не существует");
        }
    }

    private void deleteGroup() {
        System.out.println("Введите номер группы");
        try {
            int number = in.nextInt();
            in.nextLine();

            Group group =
                    groupDAO.selectGroupByNumber(number);

            System.out.println("Вы уверены, что хотите удалить запись " +
                    "об этой группе\n Y/N?");
            String answer = in.nextLine().toUpperCase();
            switch (answer) {
                case "Y": {
                    groupDAO.delete(group);
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
            System.out.println("Группы с данным номером не существует");
        }
    }

    private void workWithTeachersOfGroup() {
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
                                    groupDAO.selectGroupByNumber(number);

                            List<Teacher> teachers =
                                    group.getTeachers();
                            if (teachers.size() > 0) {
                                for (Teacher e : teachers) {
                                    System.out.println(e);
                                }
                            } else {
                                System.out.println("Нет записей");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
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
                                    groupDAO.selectGroupByNumber(number);

                            System.out.println("Введите ID реподавателя," +
                                    " которого вы хотите присвоить" +
                                    " группе");
                            int id = in.nextInt();
                            in.nextLine();
                            Teacher teacher = new TeacherDAO().findById(id);
                            group.addTeacher(teacher);

                            groupDAO.update(group);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
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
                                    groupDAO.selectGroupByNumber(number);

                            System.out.println("Введите ID преподавателя," +
                                    " которого вы хотите заменить" +
                                    " группе");
                            int idOldTeacher = in.nextInt();
                            in.nextLine();
                            Teacher oldTeacher = new TeacherDAO().findById(idOldTeacher);

                            System.out.println("Введите ID нового преподавателя");
                            int idNewTeacher = in.nextInt();
                            in.nextLine();
                            Teacher newTeacher = new TeacherDAO().findById(idNewTeacher);

                            group.setTeacher(oldTeacher, newTeacher);

                            groupDAO.update(group);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
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
                                    groupDAO.selectGroupByNumber(number);

                            System.out.println("Введите ID преподавателя," +
                                    " которого вы хотите удалить" +
                                    " группе");
                            int idTeacher = in.nextInt();
                            in.nextLine();
                            Teacher teacher = new TeacherDAO().findById(idTeacher);
                            group.removeTeacher(teacher);

                            groupDAO.update(group);
                        } catch (InputMismatchException e) {
                            System.out.println("Некорректный ввод");
                            in.nextLine();
                        } catch (PersistenceException e) {
                            System.out.println("Группы или преподавателя " +
                                    "с данным номером или ID не существует");
                        }
                        break;
                    }
                    case 0: {
                        backMenuTeacherGroup = false;
                        break;
                    }
                    default: {
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

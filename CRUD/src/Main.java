/**
 * @author Nemolyaev Ilya Vladislavovich
 * @version 4.0
 * This project can work with SQL data base
 * "student-group-teacher". You can make
 * all CRUD-operations with this.
 */

import menuForTables.GroupMenu;
import menuForTables.StudentMenu;
import menuForTables.TeacherMenu;
import workWithBase.connectWithBase.FactoryForDAO;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class output in console
 * main menu, where you can work with base
 */
public class Main {

    private static boolean exit = true;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        FactoryForDAO startConnect = new FactoryForDAO();
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
                        StudentMenu studentMenu = new StudentMenu(in);
                        studentMenu.studentWork();
                        break;
                    }
                    case 2: {
                         GroupMenu groupMenu = new GroupMenu(in);
                         groupMenu.groupWork();
                        break;
                    }
                    case 3: {
                          TeacherMenu teacherMenu = new TeacherMenu(in);
                          teacherMenu.teacherWork();
                        break;
                    }
                    case 0: {
                        exit = !exit;
                        break;
                    }
                    default: {
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
}
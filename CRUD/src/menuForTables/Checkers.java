package menuForTables;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.persistence.PersistenceException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Checkers {
    private Scanner in;

    public Checkers (Scanner in) {
        this.in = in;
    }

    public LocalDate checkCorrectDate() {
        LocalDate date = null;
        System.out.println("Введите дату рождения студента " +
                "через пробел DD MM YYYY");
        try {
            int day = in.nextInt();
            int month = in.nextInt();
            int year = in.nextInt();
            in.nextLine();
            date = LocalDate.of(year, month, day);
        } catch (InputMismatchException | DateTimeException e) {
            in.nextLine();
            System.out.println("Некорректный ввод");
        } finally {
            return date;
        }
    }

    public Gender checkCorrectGender() {
        System.out.println("Пол MAN/WOMAN");
        String gender = in.nextLine();
        Gender male;
        if (Gender.MAN.toString().equals(gender)) {
            male = Gender.MAN;
        } else if (Gender.WOMAN.toString().equals(gender)) {
            male = Gender.WOMAN;
        } else {
            male = null;
            System.out.println("Пожалуйста введите одно из " +
                    "значений приведённых в образце выше");
        }
        return male;
    }

    public Group checkGroup() {
        Group group = null;
        try {
            System.out.println("Введите номер группы");
            int numberGroup = in.nextInt();
            in.nextLine();
            group =
                    new GroupDAO().selectGroupByNumber(numberGroup);
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            in.nextLine();
        } catch (PersistenceException e) {
            System.out.println("Группы с данным номером не существует");
        } finally {
            return group;
        }
    }
}

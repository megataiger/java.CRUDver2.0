import objectForStrokeBase.Student;
import workWithBase.daoClasses.StudentDAO;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.findById(63);
        LocalDate date = student.getDate();
        System.out.println(student.getDate());


    }
}

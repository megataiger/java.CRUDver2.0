package servlets.student;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;


public class insertStudent extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        GroupDAO groupDAO = new GroupDAO();

        Group group = null;

        Gender gender;

        String name = request.getParameter("nameStudent");

        if (request.getParameter("genderStudent").equals(Gender.MAN.toString())) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }

        if (!request.getParameter("groupStudent").equals("-")) {
            int number = Integer.parseInt(request.getParameter("groupStudent"));
            group = groupDAO.selectGroupByNumber(number);
        }

        LocalDate date = LocalDate.parse(request.getParameter("birthdayStudent"));

        Student student = new Student(name,
                date,
                gender, group);

        StudentDAO studentDAO = new StudentDAO();

        studentDAO.save(student);

        studentDAO.close();
        groupDAO.close();
    }
}

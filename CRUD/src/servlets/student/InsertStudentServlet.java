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


public class InsertStudentServlet extends HttpServlet {

    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        GroupDAO groupDAO = new GroupDAO();

        Group group = null;

        Gender gender = null;

        String name = request.getParameter("nameStudent");

        for (Gender e : Gender.values()) {
            if (request.getParameter("genderStudent").equals(e.toString())) {
                gender = e;
            }
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

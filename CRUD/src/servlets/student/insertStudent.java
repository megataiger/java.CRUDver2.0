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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gender gender;
        String name = request.getParameter("studentName");
        if (request.getParameter("gender").equals(Gender.MAN.toString())) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }
        int number = Integer.parseInt(request.getParameter("group"));
        LocalDate date = LocalDate.parse(request.getParameter("birthday"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);
        Student student = new Student(name,
                date,
                gender, group);
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.save(student);
        response.sendRedirect("http://10.0.16.10:8080/TestProject_war_exploded/basic.jsp");
    }
}

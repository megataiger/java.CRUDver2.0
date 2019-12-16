package servlets.teacher;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class insertTeacher extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("teacherName");
        LocalDate date = LocalDate.parse(request.getParameter("birthday"));
        Gender gender;
        if (request.getParameter("gender").equals(Gender.MAN.toString())) {
            gender = Gender.MAN;
        } else {
            gender = Gender.WOMAN;
        }

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = new Teacher(name, date, gender);
        teacherDAO.save(teacher);
        teacherDAO.close();
    }
}

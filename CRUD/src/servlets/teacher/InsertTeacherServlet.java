package servlets.teacher;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class InsertTeacherServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String name = request.getParameter("nameTeacher");

        LocalDate date = LocalDate.parse(request.getParameter("birthday"));

        Gender gender = null;

        for (Gender e : Gender.values()) {
            if (request.getParameter("gender").equals(e.toString())) {
                gender = e;
            }
        }

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = new Teacher(name, date, gender);
        teacherDAO.save(teacher);
        teacherDAO.close();
    }
}

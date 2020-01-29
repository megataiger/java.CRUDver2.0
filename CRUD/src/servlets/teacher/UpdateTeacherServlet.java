package servlets.teacher;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateTeacherServlet extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("idTeacher"));

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(id);

        if (request.getParameter("nameTeacher") != null) {
            String name = request.getParameter("nameTeacher");
            teacher.setNameTeacher(name);
        }

        if (request.getParameter("newBirthday") != null) {
            LocalDate date = LocalDate.parse(request.getParameter("newBirthday"));
            teacher.setBirthdayTeacher(date);
        }

        if (request.getParameter("newGender") != null) {
            String string = request.getParameter("newGender");
            for (Gender e : Gender.values()) {
                if (string.equals(e.toString())) {
                    teacher.setGenderTeacher(e);
                }
            }
        }

        teacherDAO.update(teacher);
        teacherDAO.close();
    }
}

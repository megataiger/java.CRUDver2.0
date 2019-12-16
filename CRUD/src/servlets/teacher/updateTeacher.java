package servlets.teacher;

import objectForStrokeBase.Gender;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class updateTeacher extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(id);

        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            teacher.setNameTeacher(name);
            teacherDAO.update(teacher);
            teacherDAO.close();
        } else if (request.getParameter("date") != null) {
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            teacher.setBirthdayTeacher(date);
            teacherDAO.update(teacher);
            teacherDAO.close();
        } else if (request.getParameter("gender") != null) {
            String string = request.getParameter("gender");
            if(string.equals(Gender.MAN.toString())) {
                teacher.setGenderTeacher(Gender.MAN);
                teacherDAO.update(teacher);
                teacherDAO.close();
            } else {
                teacher.setGenderTeacher(Gender.WOMAN);
                teacherDAO.update(teacher);
                teacherDAO.close();
            }
        }
    }
}

package servlets.teacher;

import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteTeacher extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("Utf-8");

        int id = Integer.parseInt(request.getParameter("idTeacher"));

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(id);

        teacherDAO.delete(teacher);
        teacherDAO.close();
    }
}

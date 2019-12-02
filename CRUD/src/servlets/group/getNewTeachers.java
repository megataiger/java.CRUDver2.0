package servlets.group;

import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getNewTeachers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        TeacherDAO teacherDAO = new TeacherDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        List<Object[]> teachers = teacherDAO.findByWithoutConWithGroup(id);
        PrintWriter writer = response.getWriter();
        for (Object[] e : teachers) {
            writer.println("<option value=\"" + e[0] + "\">" + e[1] + "</option>");
        }
        teacherDAO.close();
    }
}

package servlets.group;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getTeachers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.findById(id);
        PrintWriter writer = response.getWriter();
        for (Teacher e : group.getTeachers()) {
            writer.println("<tr><td>" + e.getName() + "</td></tr>");
        }
        groupDAO.close();
    }
}

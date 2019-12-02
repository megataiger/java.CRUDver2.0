package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class deleteGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.findById(id);
        groupDAO.delete(group);
        PrintWriter writer = response.getWriter();
        writer.println("GOOD");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("num"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.findById(id);
        groupDAO.delete(group);
        response.sendRedirect("group.jsp");
    }
}

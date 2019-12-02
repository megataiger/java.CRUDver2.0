package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class updateGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        int number = Integer.parseInt(request.getParameter("number"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.findById(id);
        group.set(number);
        groupDAO.update(group);
        PrintWriter writer = response.getWriter();
        writer.println("GODD!");
    }
}

package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class selectGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        StringBuilder table = new StringBuilder();
        GroupDAO groupDAO = new GroupDAO();
        List<Group> groups = groupDAO.getAll();
        for (Group e : groups) {
            table.append("<tr>\n");
            table.append("<td class=\"id\">" + e.getId() + "</td>");
            table.append("<td class=\"number\">\n" + e.getNumber() + "</td>");
            table.append("<td><a href=\"delGroup?num=" + e.getId() + "\">Удалить</a></td>\n");
            table.append("</tr>");
        }
        PrintWriter writer = response.getWriter();
        writer.println(table);
    }
}

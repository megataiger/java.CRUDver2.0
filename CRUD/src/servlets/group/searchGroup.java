package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class searchGroup extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int number = Integer.parseInt(request.getParameter("number"));
        GroupDAO groupDAO = new GroupDAO();
        PrintWriter writer = response.getWriter();
        StringBuilder table = new StringBuilder();
        try {
            Group group = groupDAO.selectGroupByNumber(number);
            table.append("<tr>\n");
            table.append("<td class=\"id\">" + group.getId() + "</td>");
            table.append("<td class=\"number\">\n" + group.getNumber() + "</td>");
            table.append("<td><a href=\"delGroup?num=" + group.getId() + "\">Удалить</a></td>\n");
            table.append("</tr>");
            writer.println(table);
        } catch (Exception e) {
            writer.println("Результатов нет");
        }
    }
}

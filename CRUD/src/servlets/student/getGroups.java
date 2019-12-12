package servlets.student;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getGroups extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        GroupDAO groupDAO = new GroupDAO();
        List<Group> groups = groupDAO.getAll();

        StringBuilder options = new StringBuilder();

        if(request.getParameter("number") == null) {
            options.append("<option value=\"-\" selected=\"selected\">-</option>");
            for(Group e : groups) {
                int i = e.getNumber();
                options.append("<option value=\"" + i +"\">" + i + "</option>");
            }
        } else {
            int number;
            try {
                number = Integer.parseInt(request.getParameter("number"));
            } catch (Exception e) {
                number = 0;
                options.append("<option value=\"-\" selected=\"selected\">-</option>");
            }

            for(Group e : groups) {
                if (e.getNumber() == number) {
                    int i = e.getNumber();
                    options.append("<option value=\"" + i +"\" selected=\"selected\">" + i + "</option>");
                } else {
                    int i = e.getNumber();
                    options.append("<option value=\"" + i +"\">" + i + "</option>");
                }
            }
        }

        PrintWriter writer = response.getWriter();
        writer.println(options);
    }
}

package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class selectAllGroup extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        StringBuilder result = new StringBuilder();

        GroupDAO groupDAO = new GroupDAO();

        if (request.getParameter("numberGroup") == null) {

            List<Group> groups = groupDAO.getAll();

            writer.println(
                    constructResult(result, groups)
            );

        } else {
            int number = Integer.parseInt(request.getParameter("numberGroup"));

            List<Object[]> groups = groupDAO.searchGroup(number);

            writer.println(
                    constructResultForSearch(result, groups)
            );

        }

        groupDAO.close();
    }

    private StringBuilder constructResult
            (StringBuilder string, List<Group> resultList) {
        for (Group e : resultList) {
            string.append("<tr>\n");
            string.append("<td class=\"id\">");
            string.append(e.getId());
            string.append("</td>\n");
            string.append("<td class=\"number\">");
            string.append(e.getNumber());
            string.append("</td>\n");
            string.append("<td><a class=\"students\" href=\"\">Студенты</a></td>\n");
            string.append("<td><a class=\"teachers\" href=\"\">Преподаватели</a></td>\n");
            string.append("<td><a class=\"del\" href=\"\"><img title='Удалить' src=\"bascet.png\"></a></td>\n");
            string.append("</tr>");
        }

        return string;
    }

    private StringBuilder constructResultForSearch
            (StringBuilder string, List<Object[]> resultList) {
        for (Object[] e : resultList) {
            string.append("<tr>\n");
            string.append("<td class=\"id\">");
            string.append(e[0]);
            string.append("</td>\n");
            string.append("<td class=\"number\">");
            string.append(e[1]);
            string.append("</td>\n");
            string.append("<td><a class=\"students\" href=\"\">Студенты</a></td>\n");
            string.append("<td><a class=\"teachers\" href=\"\">Преподаватели</a></td>\n");
            string.append("<td><a class=\"del\" href=\"\"><img title='Удалить' src=\"bascet.png\"></a></td>\n");
            string.append("</tr>");
        }

        return string;
    }
}

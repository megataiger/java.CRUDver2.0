package servlets.teacher;

import workWithBase.daoClasses.GroupDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getNewGroups extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        GroupDAO groupDAO = new GroupDAO();

        StringBuilder result = new StringBuilder();

        if (request.getParameter("number") == null) {
            result = getResult(result, groupDAO.findByWithoutConWithTeacher(id));
        } else {
            String number = request.getParameter("number");
            result = getResult(result, groupDAO.findByWithoutConWithTeacher(id, number));
        }

        PrintWriter writer = response.getWriter();
        writer.println(result);

        groupDAO.close();
    }

    private StringBuilder getResult(StringBuilder string, List<Object> resultList) {
        if (resultList.size() == 0) {
            string.append("<tr><td>Нет результатов</td></tr>");
        } else {
            for (Object e : resultList) {
                string.append("<tr>\n");
                string.append("<td>");
                string.append(e);
                string.append("</td>\n");
                string.append("<td><a class=\"addGroup\" href=\"");
                string.append(e);
                string.append("\"><img title='Добавить' src=\"plus.png\"></a></td>\n");
                string.append("</tr>\n");
            }
        }

        return string;
    }
}

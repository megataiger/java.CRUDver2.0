package servlets.teacher;

import objectForStrokeBase.Group;
import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getGroup extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        TeacherDAO teacherDAO = new TeacherDAO();
        Teacher teacher = teacherDAO.findById(id);

        StringBuilder result = new StringBuilder();

        if (request.getParameter("number") == null) {
            result = getResult(result, teacher.getGroups());
        } else {
            GroupDAO groupDAO = new GroupDAO();
            String number = request.getParameter("number");
            result = getResultForSearch(result, groupDAO.findByConWithTeacher(id, number));
        }
        PrintWriter writer = response.getWriter();
        writer.println(result);

        teacherDAO.close();
    }

    private StringBuilder getResult(StringBuilder string, List<Group> resultList) {
        if (resultList.size() == 0) {
            string.append("<tr><td>Нет результатов</td></tr>");
        } else {
            for (Group e : resultList) {
                string.append("<tr>\n");
                string.append("<td>");
                string.append(e.getNumber());
                string.append("</td>\n");
                string.append("<td>");
                string.append("<a class='deleteGroup'" +
                        " href=''><img title='Удалить' src=\"bascet.png\"></a>");
                string.append("</td>\n");
                string.append("</tr>\n");
            }
        }

        return string;
    }

    private StringBuilder getResultForSearch(StringBuilder string, List<Object> resultList) {
        if (resultList.size() == 0) {
            string.append("<tr><td>Нет результатов</td></tr>");
        } else {
            for (Object e : resultList) {
                string.append("<tr>\n");
                string.append("<td>");
                string.append(e);
                string.append("</td>\n");
                string.append("<td><a class=\"deleteGroup\" href=\"");
                string.append(e);
                string.append("\"><img title='Удалить' src=\"bascet.png\"></a></td>\n");
                string.append("</tr>\n");
            }
        }

        return string;
    }
}

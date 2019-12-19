package servlets.group;

import objectForStrokeBase.Group;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getNewTeachers extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
                        throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int number = Integer.parseInt(request.getParameter("numberGroup"));

        GroupDAO groupDAO = new GroupDAO();
        TeacherDAO teacherDAO = new TeacherDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        int id = group.getId();

        groupDAO.close();

        PrintWriter writer = response.getWriter();
        StringBuilder result = new StringBuilder();

        if (request.getParameter("nameTeacher") == null) {
            List<Object[]> teachers = teacherDAO.findByWithoutConWithGroup(id);
            writer.println(
                    constructResult(result, teachers)
            );
        } else {
            String name = request.getParameter("nameTeacher");
            List<Object[]> teachers =
                    teacherDAO.findByWithoutConWithGroup(id, name);
            if (teachers.size() != 0) {
                writer.println(
                        constructResult(result, teachers)
                );
            } else {
                writer.println("<tr>\n<td>Преподваетеля с данным именем нет в списке" +
                        ", либо он уже добавлен</td>\n</tr>\n");
            }
        }
        teacherDAO.close();
    }

    private StringBuilder constructResult
            (StringBuilder string, List<Object[]> resultList) {

        for (Object[] e : resultList) {
            string.append("<tr>\n");
            string.append("<td>");
            string.append(e[1]);
            string.append("</td>\n");
            string.append("<td>");
            string.append(e[2]);
            string.append("</td>\n");
            string.append("<td><a class=\"addTeacher\" href=\"");
            string.append(e[0]);
            string.append("\"><img title='Добавить' src=\"plus.png\"></a></td>\n");
            string.append("</tr>\n");
        }

        return string;
    }
}
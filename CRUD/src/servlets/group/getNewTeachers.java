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

        int number = Integer.parseInt(request.getParameter("number"));

        GroupDAO groupDAO = new GroupDAO();
        TeacherDAO teacherDAO = new TeacherDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        int id = group.getId();

        groupDAO.close();

        PrintWriter writer = response.getWriter();
        StringBuilder result = new StringBuilder();

        if (request.getParameter("name") == null) {
            List<Object[]> teachers = teacherDAO.findByWithoutConWithGroup(id);
            writer.println(
                    constructResult(result, teachers)
            );
        } else {
            String name = request.getParameter("name");
            List<Object[]> teachers =
                    teacherDAO.findByWithoutConWithGroup(id, name);
            writer.println(
                    constructResult(result, teachers)
            );
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
            string.append("<td><a class=\"add\" href=\"");
            string.append(e[0]);
            string.append("\"><img src=\"plus.png\"></a></td>\n");
            string.append("</tr>\n");
        }

        return string;
    }
}

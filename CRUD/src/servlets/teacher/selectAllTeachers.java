package servlets.teacher;

import objectForStrokeBase.Teacher;
import workWithBase.daoClasses.TeacherDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class selectAllTeachers extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        StringBuilder result = new StringBuilder();

        TeacherDAO teacherDAO = new TeacherDAO();

        PrintWriter writer = response.getWriter();

        writer.println(getResult(result, teacherDAO.getAll()));

    }

    private StringBuilder getResult
            (StringBuilder string, List<Teacher> resultList) {

        string.append("<tr>\n");
        string.append("<th>Ф.И.О.</th>\n");
        string.append("<th>Дата пождения</th>\n");
        string.append("<th>Пол</th>\n");
        string.append("<th>Действие</th>\n");
        string.append("</tr>");

        for (Teacher e : resultList) {
            string.append("<tr>\n<td>");
            string.append(e.getId());
            string.append("</td>\n<td>");
            string.append(e.getName());
            string.append("</td>\n<td>");
            string.append(e.getDate());
            string.append("</td>\n<td>");
            string.append(e.getGender());
            string.append("</td>\n<td><img src=\"bascet.png\"></td>\n");
            string.append("</tr>");
        }

        return string;
    }
}

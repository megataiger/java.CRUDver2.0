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

        if (request.getParameter("nameTeacher") == null) {
            List<Teacher> teachers = teacherDAO.getAll();
            writer.println(getResult(result, teachers));
        } else {

            StringBuilder query = new StringBuilder();
            query.append("FROM Teacher WHERE LOWER(name) LIKE ");

            if (request.getParameter("nameTeacher").equals("")) {
                String name = "'%" + request.getParameter("nameTeacher") + "%'";
                query.append(name);
            } else {
                String name = "'%" + request.getParameter("nameTeacher") + "%'";
                query.append(name);
            }

            if (!request.getParameter("dateTeacher").equals("")) {
                String date = "'%" + request.getParameter("dateTeacher") + "%'";
                query.append("AND LOWER(birthday) LIKE ");
                query.append(date);
                query.append(" ");
            }

            if (!request.getParameter("genderTeacher").equals("-")) {
                String gender = request.getParameter("genderTeacher");
                query.append("AND gender = '");
                query.append(gender);
                query.append("' ");
            }

            String filter = query.toString();

            List<Teacher> teachers = teacherDAO.findByFilter(filter);
            writer.println(getResult(result, teachers));
        }
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
            string.append("<tr>\n<td class='id'>");
            string.append(e.getId());
            string.append("</td>\n<td class='name'>");
            string.append(e.getName());
            string.append("</td>\n<td class='date'>");
            string.append(e.getDate());
            string.append("</td>\n<td class='gender'>");
            string.append(e.getGender());
            string.append("</td>\n<td><a class=\"del\" href=\"\"><img title='Удалить' src=\"bascet.png\"></a>" +
                    "<a class=\"list\" href=\"\"><img src=\"list.png\" title='Список групп'></a></td>\n");
            string.append("</tr>");
        }

        return string;
    }
}

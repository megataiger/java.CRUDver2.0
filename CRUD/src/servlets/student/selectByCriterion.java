package servlets.student;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class selectByCriterion extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        StudentDAO studentDAO = new StudentDAO();

        if (request.getParameter("nameStudent") == null) {
            List<Student> students = studentDAO.getAll();
            getResult(students, writer);
        } else {

            StringBuilder query = new StringBuilder();
            query.append("FROM Student WHERE LOWER(name) LIKE ");

            if (request.getParameter("nameStudent").equals("")) {
                String name = "'%" + request.getParameter("nameStudent") + "%'";
                query.append(name);
            } else {
                String name = "'%" + request.getParameter("nameStudent") + "%'";
                query.append(name);
            }

            if (!request.getParameter("dateStudent").equals("")) {
                String date = "'%" + request.getParameter("dateStudent") + "%'";
                query.append("AND LOWER(birthday) LIKE ");
                query.append(date);
                query.append(" ");
            }

            if (!request.getParameter("genderStudent").equals("-")) {
                String gender = request.getParameter("genderStudent");
                query.append("AND gender = '");
                query.append(gender);
                query.append("' ");
            }
            if (!request.getParameter("groupStudent").equals("-")) {
                int groupStudent = Integer.parseInt(request.getParameter("groupStudent"));
                GroupDAO groupDAO = new GroupDAO();
                Group group = groupDAO.selectGroupByNumber(groupStudent);
                query.append("AND group_id = ");
                query.append(group.getId());
            }

            String filter = query.toString();

            List<Student> students = studentDAO.findByFilter(filter);
            getResult(students, writer);
        }

        studentDAO.close();
    }

    private void getResult(List<Student> list, PrintWriter writer) {
        if (list.size() == 0) {
            writer.println("<tr><th>Совпадений нет</th></tr>");
        } else {
            StringBuilder string = new StringBuilder();
            string.append("<tr>\n");
            string.append("<th>Ф.И.О.</th>\n");
            string.append("<th>Дата пождения</th>\n");
            string.append("<th>Пол</th>\n");
            string.append("<th>Группа</th>\n");
            string.append("<th>Действие</th>\n");
            string.append("</tr>");
            for (Student e : list) {
                string.append("<tr>");
                string.append("<td class=\"id\">");
                string.append(e.getId());
                string.append("</td>");
                string.append("<td class=\"open\">");
                string.append(e.getName());
                string.append("</td>");
                string.append("<td class=\"date\">");
                string.append(e.getDate());
                string.append("</td>");
                string.append("<td class=\"gender\">");
                string.append(e.getGender());
                string.append("</td>");
                if (e.getGroup() != null) {
                    string.append("<td class=\"group\">");
                    string.append(e.getGroup().getNumber());
                    string.append("</td>");
                } else {
                    string.append("<td class=\"group\"> - </td>");
                }
                string.append("<td><a id='del' href=\"");
                string.append(e.getId());
                string.append("\"><img src=\"bascet.png\"></td>");
                string.append("</tr>");
            }

            writer.println(string);
        }
    }
}

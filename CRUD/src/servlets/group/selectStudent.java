package servlets.group;

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

public class selectStudent extends HttpServlet {
    @Override
    protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        if (request.getParameter("name") == null) {
            int groupNumber =
                    Integer.parseInt(request.getParameter("groupNumber"));

            GroupDAO groupDAO = new GroupDAO();
            Group group = groupDAO.selectGroupByNumber(groupNumber);

            StudentDAO studentDAO = new StudentDAO();

            StringBuilder result = new StringBuilder();

            List<Student> students = studentDAO.findByGroup(group);

            if (students.size() == 0) {
                writer.println("<tr><td>В этой группе нет студентов"
                        + "</td></tr>");
            } else {
                writer.println(
                        constructResult(result, students)
                );
            }

            groupDAO.close();
            studentDAO.close();
        } else {
            int groupNumber =
                    Integer.parseInt(request.getParameter("groupNumber"));

            String name = request.getParameter("name");

            GroupDAO groupDAO = new GroupDAO();
            Group group = groupDAO.selectGroupByNumber(groupNumber);

            StudentDAO studentDAO = new StudentDAO();

            StringBuilder result = new StringBuilder();

            List<Student> students =
                    studentDAO.findByGroupAndName(name, group);

            if (students.size() == 0) {
                writer.println("<tr><td>В этой группе нет студентов" +
                        " с таким именем" +
                        "</td></tr>");
            } else {
                writer.println(
                        constructResult(result, students)
                );
            }

            groupDAO.close();
            studentDAO.close();
        }
    }

    private StringBuilder constructResult
            (StringBuilder string, List<Student> resultList) {
        for (Student student : resultList) {
            string.append("<tr>\n");
            string.append("<td>");
            string.append(student.getName());
            string.append("</td>\n");
            string.append("<td>");
            string.append(student.getDate());
            string.append("</td>\n");
            string.append("</tr>");
        }

        return string;
    }
}

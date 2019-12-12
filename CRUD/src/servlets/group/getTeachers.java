package servlets.group;

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

public class getTeachers extends HttpServlet {
    @Override
    protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
                        throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int number = Integer.parseInt(request.getParameter("number"));
        GroupDAO groupDAO = new GroupDAO();
        Group group = groupDAO.selectGroupByNumber(number);

        PrintWriter writer = response.getWriter();

        StringBuilder result = new StringBuilder();

        if (request.getParameter("name") == null) {
            List<Teacher> teachers = group.getTeachers();

            if (teachers.size() != 0) {
                writer.println(
                        constructResultByTeachers(result, teachers)
                );
            } else {
                writer.println("<tr>\n<td>У данной группы нет преподавателей</td>\n</tr>\n");
            }
        } else {
            TeacherDAO teacherDAO = new TeacherDAO();
            String name = request.getParameter("name");

            List<Object[]> teachers =
                    teacherDAO.findByWithConGroup(group.getId(), name);

            try {
                if (teachers.size() > 0) {
                    writer.println(
                            constructResult(result, teachers)
                    );
                } else {
                    writer.println("<tr>\n<td>Преподвателя с данным именем нет в списке" +
                            ", либо он ещё не добавлен</td>\n</tr>\n");
                }
            } catch (Exception e) {
                writer.println("Потеряна связь с сервером");
            }
            teacherDAO.close();
        }
        groupDAO.close();
    }

        private StringBuilder constructResultByTeachers
                (StringBuilder string, List<Teacher> resultList) {
            for (Teacher e : resultList) {
                string.append("<tr>\n");
                string.append("<td>");
                string.append(e.getName());
                string.append("</td>\n");
                string.append("<td>");
                string.append(e.getDate());
                string.append("</td>\n");
                string.append("<td class=\"");
                string.append(e.getId());
                string.append("\"><a class=\"del\" href=\"");
                string.append(e.getId());
                string.append("\"><img src=\"bascet.png\"></a></td>\n");
                string.append("</tr>");
            }

            return string;
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
                string.append("<td class=\"");
                string.append(e[0]);
                string.append("\"><a class=\"del\" href=\"");
                string.append(e[0]);
                string.append("\"><img src=\"bascet.png\"></a></td>\n");
                string.append("</tr>");
            }

            return string;
        }
    }

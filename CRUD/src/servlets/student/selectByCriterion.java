package servlets.student;

import objectForStrokeBase.Group;
import objectForStrokeBase.Student;
import workWithBase.daoClasses.GroupDAO;
import workWithBase.daoClasses.StudentDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class selectByCriterion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        responce.setCharacterEncoding("UTF-8");
        String criterion = request.getParameter("crit");
        String query = request.getParameter("field");
        PrintWriter writer = responce.getWriter();
        StudentDAO studentDAO = new StudentDAO();
        if (criterion.equals("name")) {
            List<Student> students = studentDAO.findByName(query);
            if (students.size() > 0) {
                writer.println(getResult(students));
            } else {
                writer.println("Совпадений нет");
            }
        } else if (criterion.equals("date")) {
            List<Student> students = studentDAO.findByDate(LocalDate.parse(query));
            if (students.size() > 0) {
                writer.println(getResult(students));
            } else {
                writer.println("Совпадений нет");
            }
        } else if (criterion.equals("group")) {
            GroupDAO groupDAO = new GroupDAO();
            Group group = groupDAO.selectGroupByNumber(Integer.parseInt(query));
            List<Student> students = studentDAO.findByGroup(group);
            if (students.size() > 0) {
                writer.println(getResult(students));
            } else {
                writer.println("Совпадений нет");
            }
        } else {
            writer.println("Нет результатов");
        }
    }

    public String getResult(List<Student> list) {
        StringBuffer stringBuffer = new StringBuffer();
        String adress = "http://10.0.16.10:8080/TestProject_war_exploded/del?number=";
        stringBuffer.append("<tr>\n" +
                "<td>Ф.И.О.</td>\n" +
                "<td>Дата пождения</td>\n" +
                "<td>Пол</td>\n" +
                "<td>Группа</td>\n" +
                "<td>Действие</td>\n" +
                "</tr>");
        for(Student e : list) {
            stringBuffer.append("<tr>");
            stringBuffer.append("<td class=\"id\">" + e.getId() + "</td>");
            stringBuffer.append("<td class=\"open\">" + e.getName() + "</td>");
            stringBuffer.append("<td class=\"date\">" + e.getDate() + "</td>");
            stringBuffer.append("<td class=\"gender\">" + e.getGender() + "</td>");
            if (e.getGroup() != null) {
                stringBuffer.append("<td class=\"group\">" + e.getGroup().getNumber() + "</td>");
            } else {
                stringBuffer.append("<td class=\"group\"> - </td>");
            }
            stringBuffer.append("<td><a href=\"" + adress + e.getId()
                    + "\">Удалить</td>");
            stringBuffer.append("</tr>");
        }
        String result = stringBuffer.toString();
        return result;
        }
}

<%@ page import="workWithBase.daoClasses.StudentDAO" %>
<%@ page import="objectForStrokeBase.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="workWithBase.daoClasses.GroupDAO" %>
<%@ page import="objectForStrokeBase.Group" %>
<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 21.11.2019
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Студенты</title>
        <link rel="stylesheet" href="style.css">
        <script type="text/javascript" src="jquery-3.4.1.js"></script>
        <script type="text/javascript" src="script.js"></script>
    </head>
    <body>
    <div>
        <h4 align="center"><a href="index.jsp">Главная</a></h4>
        <div class="search">
            Выберите критерий для поиска и введите запрос:
            <select name="field">
                <option value="name">Ф.И.О</option>
                <option value="date">Дата рождения</option>
                <option value="group">Группы</option>
            </select>
            <input type="text" name="query">
            <button>Поиск</button>
        </div>
        <div class="data">
            <table border="2pt">
                <tr>
                    <td>Ф.И.О.</td>
                    <td>Дата пождения</td>
                    <td>Пол</td>
                    <td>Группа</td>
                    <td>Действие</td>
                </tr>
                <%
                    String adress = "del?number=";
                    StudentDAO studentDAO = new StudentDAO();
                    List list = studentDAO.getAll();
                    List<Student> students = list;
                    for (Student e: students) {
                        out.println("<tr>");
                        out.println("<td class=\"id\">" + e.getId() + "</td>");
                        out.println("<td class=\"open\">" + e.getName() + "</td>");
                        out.println("<td class=\"date\">" +  e.getDate() + "</td>");
                        out.println("<td class=\"gender\">" + e.getGender() + "</td>");
                        if (e.getGroup() != null) {
                            out.println("<td class=\"group\">" + e.getGroup().getNumber() + "</td>");
                        } else {
                            out.println("<td class=\"group\"> - </td>");
                        }
                        out.println("<td><a href=\"" + adress + e.getId()
                                + "\"><img src=\"bascet.png\"></td>");
                        out.println("</tr>");
                    }
                %>
            </table>
            <form action="ins" method="POST">
                Имя студента: <input type="text" name="studentName" /><br><br>
                Дата рождения: <input type="date" name="birthday" /><br><br>
                Пол: <input type="radio" name="gender" value="WOMAN" checked />Woman
                <input type="radio" name="gender" value="MAN" />Man<br><br>
                Группа:
                <select name="group">
                    <%
                        GroupDAO groupDAO = new GroupDAO();
                        List<Group> groups = groupDAO.getAll();
                        StringBuffer options = new StringBuffer();
                        for(Group e : groups) {
                                options.append("<option value=\"" + e.getNumber()
                                        + "\">" + e.getNumber() + "</option>");
                        }
                        out.println(options);
                    %>
                </select>
                <br><br>
                <input type="submit" value="Submit" />
            </form>
    </div>
    </div>
    </body>
</html>
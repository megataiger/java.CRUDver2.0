<%@ page import="workWithBase.daoClasses.GroupDAO" %>
<%@ page import="objectForStrokeBase.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="workWithBase.daoClasses.TeacherDAO" %>
<%@ page import="objectForStrokeBase.Teacher" %><%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 29.11.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Группы</title>
    <link rel="stylesheet" href="styleGroup.css">
    <script type="text/javascript" src="jquery-3.4.1.js"></script>
    <script type="text/javascript" src="scriptForGroup.js"></script>
</head>
<body>
<div class="main_div">
<div>
    <div>
        <h4 align="center"><a href="index.jsp">Главная</a></h4>
    </div>
    <div>
        Поиск: <br>
        <input class="search" type="text">
    </div>
    <br>
    <div>
        Добавить новую группу: <br>
        <input class="add" type="text">
    </div>
</div>
<br>
<table border="2px">
    <tbody>
        <%
            GroupDAO groupDAO = new GroupDAO();
            List<Group> groups = groupDAO.getAll();
            StringBuffer body = new StringBuffer();
            for(Group e : groups) {
                body.append("<tr>\n");
                body.append("<td class=\"id\">" + e.getId() + "</td>");
                body.append("<td class=\"number\">\n" + e.getNumber() + "</td>");
                body.append("<td><a class=\"teachers\" href=\"\">Преподаватели</a></td>\n");
                body.append("<td><a class=\"del\" href=\"\">Удалить</a></td>\n");
                body.append("</tr>");
            }
            out.println(body.toString());
        %>
    </tbody>
</table>
    <div class="menu">
        <h4>Просмотр</h4>
        <h4>Добавление</h4>
        <h4>Удаление</h4>
        <br>
        <div class="addTeacher">
            <select>
            </select>
        </div>
        <br>
        <div class="findTeacher">
            Поиск:<br>
            <input type="text">
            <br>
        </div>
        <table>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>

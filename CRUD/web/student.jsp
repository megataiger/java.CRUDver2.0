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
    <div id="mainDiv">
        <h4 align="center"><a href="index.jsp">Главная</a></h4>
        <div class="search">
            <form id="st" action="" method="post">
                <label for="nameStudent">Ф.И.О</label>
                <input id="nameStudent" type="text" name="nameStudent">
                <label for="dateStudent">Дата рождения</label>
                <input id="dateStudent" name="dateStudent" type="date">
                <label for="genderStudent">Пол</label>
                <select id="genderStudent" name="genderStudent">
                    <option value="-">-</option>
                    <option value="WOMAN">WOMAN</option>
                    <option value="MAN">MAN</option>
                </select>
                <label for="groupStudent">Группа</label>
                <select id="groupStudent" class="allGroup" name="groupStudent">

                </select>
                <input id="search" type="submit" value="Поиск" />
            </form>
        </div>
        <div class="data">
            <table id="students">
                <tbody>

                </tbody>
            </table>
            <form action="" id="addStudent" method="POST">
                Имя студента: <input type="text" name="studentName" /><br><br>
                Дата рождения: <input type="date" name="birthday" /><br><br>
                Пол: <input type="radio" name="gender" value="WOMAN" checked />Woman
                <input type="radio" name="gender" value="MAN" />Man<br><br>
                Группа:
                <select class="allGroup" name="group">

                </select>
                <br><br>
                <input type="submit" value="Добавить" />
            </form>
    </div>
    </div>
    </body>
</html>
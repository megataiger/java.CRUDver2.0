<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 12.12.2019
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Преподаватели</title>
    <link rel="stylesheet" href="style.css">
    <script type="text/javascript" src="jquery-3.4.1.js"></script>
    <script type="text/javascript" src="scriptForTeacher.js"></script>
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
            <input id="search" type="submit" value="Поиск" />
        </form>
    </div>
    <div class="data">
        <table id="teachers">
            <tbody>

            </tbody>
        </table>
        <div id="bar">
            <form action="" id="addTeacher" method="POST">
                Имя преподавателя: <input type="text" name="teacherName" /><br><br>
                Дата рождения: <input type="date" name="birthday" /><br><br>
                Пол: <input type="radio" name="gender" value="WOMAN" checked />Woman
                <input type="radio" name="gender" value="MAN" />Man<br><br>
                <br><br>
                <input type="submit" value="Добавить" />
            </form>
            <div id="groups">
                <h3 id="name"></h3>

            </div>
        </div>
    </div>
</div>
</body>
</html>

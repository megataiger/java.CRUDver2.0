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
    <link rel="stylesheet" href="styleTeacher.css">
    <script type="text/javascript" src="jquery-3.4.1.js"></script>
    <script type="text/javascript" src="scriptForTeacher.js"></script>
</head>
<div id="mainDiv">
    <h4 align="center"><a href="index.jsp">Главная</a></h4>
    <div class="search">
        <form id="findTeachers" action="" method="post">
            <label for="nameTeacher">Ф.И.О</label>
            <input id="nameTeacher" type="text" name="nameTeacher">
            <label for="dateTeacher">Дата рождения</label>
            <input id="dateTeacher" name="dateTeacher" type="date">
            <label for="genderTeacher">Пол</label>
            <select id="genderTeacher" name="genderTeacher">
                <option value="-">-</option>
                <option value="WOMAN">WOMAN</option>
                <option value="MAN">MAN</option>
            </select>
            <input id="search" type="submit" value="Поиск" />
        </form>
    </div>
    <div id="data">
        <table id="teachers">
            <tbody>
            </tbody>
        </table>
        <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
            а затем подтвердите изменения, нажав Ввод или просто выйдите из режима редактирования,
            нажав в пустаю часть экрана</div>
    </div>
    <div id="bar">
        <div id="groups">
            <h3 id="name"></h3>
            <button id="viewGroup">Просмотр</button>
            <button id="addGroup">Добавление</button>
            <input id="searchGroup" type="text">
            <table id="tableGroup">
            </table>
        </div>
        <form action="" id="addTeacher" method="POST">
            <label for="teacherName">Имя преподавателя: </label>
            <input type="text" id="teacherName" name="teacherName" /><br><br>
            <label for="date">Дата рождения: </label>
            <input type="date" id="date" name="birthday" /><br><br>
            <label for="gender">Пол: </label>
            <input type="radio" name="gender" id="gender" value="WOMAN" checked />Woman
            <input type="radio" name="gender" value="MAN" />Man<br><br>
            <br><br>
            <input type="submit" value="Добавить" />
        </form>
    </div>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 12.12.2019
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Преподаватели</title>
    <link rel="shortcut icon" href="<c:url value="/resources/image/favicon.png"/>" type="image/png"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/styleForTeacher.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/DataTables-1.10.20/css/jquery.dataTables.css"/>"/>

    <script type="text/javascript" src="<c:url value="/resources/javaScript/jquery-3.4.1.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/javaScript/scriptForTeacher.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/DataTables-1.10.20/js/jquery.dataTables.js"/>"></script>

</head>
<body>
<div id="mainDiv">
    <div class="menu">
        <a href="<c:url value="/javaBase/students"/>" class="index">Студенты</a>
        <a href="<c:url value="/javaBase/groups"/>" class="index">Группы</a>
        <a href="<c:url value="/javaBase/teachers"/>" class="index">Преподаватели</a>
    </div>
    <div id="data">
        <table id="teachers" class="display">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
        <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
            а затем подтвердите изменения, нажав Ввод
        </div>
    </div>
    <div id="menuGroupsAndAddTeacher">
        <h3>Добавить нового преподавателя</h3>
        <form action="" id="addTeacher" method="POST">
            <label for="nameNewTeacher">Имя преподавателя: </label>
            <input type="text" id="nameNewTeacher" name="name"/><br><br>
            <label for="birthdayNewTeacher">Дата рождения: </label>
            <input type="date" id="birthdayNewTeacher" name="date"/><br><br>
            <label for="genderNewTeacher">Пол: </label>
            <input type="radio" name="gender" id="genderNewTeacher" value="WOMAN" checked/>Женский
            <input type="radio" name="gender" value="MAN"/>Мужской<br><br>
            <br><br>
            <input type="submit" value="Добавить"/>
        </form>
    </div>
    <div id="groups">
        <h3 id="nameChooseTeacher"></h3>
        <img id="close" src="<c:url value="/resources/image/close.png"/>">
        <div id="buttons">
            <button id="viewGroup">Просмотр</button>
            <button id="addGroup">Добавление</button>
        </div>
        <table id="tableGroup" class="display">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>

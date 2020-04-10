<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 29.11.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Группы</title>
    <link rel="shortcut icon" href="<c:url value="/resources/image/favicon.png"/>" type="image/png">
    <link rel="stylesheet" href="<c:url value="/resources/css/styleGroup.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/DataTables-1.10.20/css/jquery.dataTables.css"/>">

    <script type="text/javascript" src="<c:url value="/resources/javaScript/jquery-3.4.1.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/javaScript/scriptForGroup.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/DataTables-1.10.20/js/jquery.dataTables.js"/>"></script>
</head>
<body>
<div class="main_div">
    <div>
        <div class="menu">
            <a href="<c:url value="/javaBase/students"/>" class="index">Студенты</a>
            <a href="<c:url value="/javaBase/groups"/>" class="index">Группы</a>
            <a href="<c:url value="/javaBase/teachers"/>" class="index">Преподаватели</a>
        </div>
        <div>
            <label for="addGroup">Добавить новую группу:</label>
            <input id="addGroup" type="text" size="5">
        </div>
    </div>
    <br>
    <table id="groups" class="display">
        <thead>

        </thead>
        <tbody>

        </tbody>
    </table>
    <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
        а затем подтвердите изменения
    </div>
    <hr>
    <h3 id="numberGroup"></h3>
    <div id="menuTeachersOfGroup">
        <div id="optionsOfMenu">
            <button id="viewTeachers">Просмотр</button>
            <button id="addTeachers">Добавление</button>
        </div>
        <table id="teachersOfGroup" class="display">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
    <div id="menuStudentsOfGroup">
        <table id="studentsOfGroup" class="display">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>

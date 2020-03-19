<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 21.11.2019
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Студенты</title>
    <link rel="shortcut icon" href="/resources/image/favicon.png" type="image/png">
    <link rel="stylesheet" href="<c:url value="/resources/css/styleForStudent.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/DataTables-1.10.20/css/jquery.dataTables.css" />"/>

    <script type="text/javascript" src="<c:url value="/resources/javaScript/jquery-3.4.1.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/javaScript/scriptForStudent.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/DataTables-1.10.20/js/jquery.dataTables.js" />"></script>
</head>
<body>
<div id="mainDiv">
    <h4 align="center"><a href="/javaBase/">Главная</a></h4>
    <div id="data">
        <table id="students">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
        <div id="promptToSearch">
            <table id="prompt"></table>
        </div>
        <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
            а затем подтвердите изменения, нажав Ввод
        </div>
    </div>
    <form action="" id="addStudent" method="POST">
        <h3>Добавление нового студента</h3>
        <label for="inputNameStudent">Ф.И.О</label>
        <input type="text" id="inputNameStudent" name="name"/><br><br>
        <label for="inputBirthdayStudent">Дата рождения</label>
        <input type="date" id="inputBirthdayStudent" name="date"/><br><br>
        <label for="inputGenderStudent">Пол</label>
        <input type="radio" id="inputGenderStudent" name="gender" value="WOMAN" checked/>Женский
        <input type="radio" name="gender" value="MAN"/>Мужской<br><br>
        <label for="plus">Группа</label>
        <img id="plus" src="/resources/image/plus1.png">
        <br><br>
        <input type="submit" value="Добавить"/>
    </form>
</div>
<div id="myModal">
        <table id="addGroup">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
</div>
<div id="myOverlay"></div>
</body>
</html>
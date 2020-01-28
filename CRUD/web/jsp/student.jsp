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
        <link rel="stylesheet" href="../css/styleForStudent.css">
        <link rel="stylesheet" type="text/css" href="../DataTables-1.10.20/css/jquery.dataTables.css"/>

        <script type="text/javascript" src="../javaScript/jquery-3.4.1.js"></script>
        <script type="text/javascript" src="../javaScript/scriptForStudent.js"></script>
        <script type="text/javascript" src="../DataTables-1.10.20/js/jquery.dataTables.js"></script>
    </head>
    <body>
    <div id="mainDiv">
        <h4 align="center"><a href="../index.jsp">Главная</a></h4>
        <div id="data">
            <table id="students">
                <thead>

                </thead>
                <tbody>

                </tbody>
            </table>
            <div id="promptToSearch"><table id="prompt"></table></div>
            <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
                а затем подтвердите изменения, нажав Ввод</div>
        </div>
        <form action="" id="addStudent" method="POST">
            <h3>Добавление нового студента</h3>
            <label for="inputNameStudent">Ф.И.О</label>
            <input type="text" id="inputNameStudent" name="nameStudent" /><br><br>
            <label for="inputBirthdayStudent">Дата рождения</label>
            <input type="date" id="inputBirthdayStudent" name="birthdayStudent" /><br><br>
            <label for="inputGenderStudent">Пол</label>
            <input type="radio" id="inputGenderStudent" name="genderStudent" value="WOMAN" checked />Женский
            <input type="radio" name="genderStudent" value="MAN" />Мужской<br><br>
            <label for="inputGroupStudent">Группа</label>
            <input id="inputGroupStudent" class="allGroup" name="groupStudent">
            <br><br>
            <input type="submit" value="Добавить" />
        </form>
        <div id="promptToAdd"><table id="addGroup"></table></div>
    </div>
    </body>
</html>
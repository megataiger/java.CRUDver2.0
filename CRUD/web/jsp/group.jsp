<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 29.11.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Группы</title>
    <link rel="stylesheet" href="../css/styleGroup.css">
    <link rel="stylesheet" type="text/css" href="../DataTables-1.10.20/css/jquery.dataTables.css"/>

    <script type="text/javascript" src="../javaScript/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="../javaScript/scriptForGroup.js"></script>
    <script type="text/javascript" src="../DataTables-1.10.20/js/jquery.dataTables.js"></script>
</head>
<body>
<div class="main_div">
    <div>
        <div>
            <h4><a href="../index.jsp">Главная</a></h4>
        </div>
        <div>
            <label for="addGroup">Добавить новую группу:</label>
            <input id="addGroup" type="text" size="5">
        </div>
    </div>
    <br>
    <table id="groups">
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
        <table id="teachersOfGroup">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
    <div id="menuStudentsOfGroup">
        <table id="studentsOfGroup">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</body>
</html>

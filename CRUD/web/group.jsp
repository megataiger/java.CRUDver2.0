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
        <link rel="stylesheet" href="styleGroup.css">
        <script type="text/javascript" src="jquery-3.4.1.js"></script>
        <script type="text/javascript" src="scriptForGroup.js"></script>
    </head>
        <div class="main_div">
            <div>
                <div>
                    <h4><a href="index.jsp">Главная</a></h4>
                </div>
                <div>
                    <label for="addGroup">Добавить новую группу:</label>
                    <input id="addGroup" type="text">
                </div>
                <div>
                    <label for="searchGroup">Поиск:</label>
                    <input id="searchGroup" type="text">
                </div>
            </div>
            <br>
            <table>
                <tbody id="groups">

                </tbody>
            </table>
            <div>Для редактирования ячеек таблицы просто нажмите на саму ячейку,
                а затем подтвердите изменения</div>
            <h3 id="numberGroup"></h3>
            <div id="menuTeachersOfGroup">
                <div id="optionsOfMenu">
                    <button id="viewTeachers">Просмотр</button>
                    <button id="addTeachers">Добавление</button>
                </div>
                <div id="searchTeachersForGroup">

                </div>
                <table>
                    <tbody id="teachers">

                    </tbody>
                </table>
            </div>
            <div id="menuStudentsOfGroup">
                <input id="searchStudent" type="text" placeholder="Найти студента">
                <table id="studentsOfGroup">
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
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
    <body>
        <div class="main_div">
            <div>
                <div>
                    <h4><a href="index.jsp">Главная</a></h4>
                </div>
                <div>
                    Поиск: <br>
                    <input id="search" type="text">
                </div>
                <br>
                <div>
                    Добавить новую группу: <br>
                    <input id="addGroup" type="text">
                </div>
            </div>
            <br>
            <table>
                <tbody id="groups">

                </tbody>
            </table>
            <div id="newMenu">
                <h3 id="num"></h3>
                <div>
                    <button id="view">Просмотр</button>
                    <button id="add">Добавление</button>
                </div>
                <div id="divSearch">

                </div>
                <table>
                    <tbody id="teachers">

                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>

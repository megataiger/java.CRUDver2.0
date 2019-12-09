<%@ page import="workWithBase.connectWithBase.FactoryForDAO" %><%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 21.11.2019
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  FactoryForDAO factory = new FactoryForDAO();
%>
<html>
  <head>
    <title>Веб-приложение</title>
    <style>
      body {
        text-align: center;
        vertical-align: center;
      }
    </style>
  </head>
  <body>
  <h1><a href="student.jsp">Студенты</a></h1>
  <br>
  <h1><a href="group.jsp">Группы</a></h1>
  </body>
</html>

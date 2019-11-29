<%@ page import="workWithBase.daoClasses.GroupDAO" %>
<%@ page import="objectForStrokeBase.Group" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 29.11.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Группы</title>
    <link rel="stylesheet" href="styleGroup.css">
</head>
<body>
<%
    GroupDAO groupDAO = new GroupDAO();
    List<Group> groups = groupDAO.getAll();
    StringBuffer body = new StringBuffer();
    for(Group e : groups) {
        body.append("<div>\n");
        body.append(e.getNumber());
        body.append("<br>\n");
        body.append("Студенты");
        body.append("<br>\n");
        body.append("Преподаватели");
        body.append("<br>\n");
        body.append("<p><img src=\"bascet.png\" align=\"right\" alt=\"Удалить\"></p>\n");
        body.append("</div>\n");
    }
    out.println(body.toString());
%>
</body>
</html>

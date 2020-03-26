<%--
  Created by IntelliJ IDEA.
  User: NemolyaevIV
  Date: 21.11.2019
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Веб-приложение</title>
    <link rel="shortcut icon" href="<c:url value="/resources/image/favicon.png"/>" type="image/png">
    <style>
        .parent {
            width: 100%;
            height: 100%;
            position: fixed;
            top: 0;
            left: 0;
            overflow: auto;
        }

        .block {
            width: 300px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
        a {
            text-decoration: none;
            outline: none;
            display: inline-block;
            width: 200px;
            height: 45px;
            line-height: 45px;
            border-radius: 45px;
            margin: 10px 20px;
            font-family: 'Montserrat', sans-serif;
            font-size: 11px;
            text-transform: uppercase;
            text-align: center;
            letter-spacing: 3px;
            font-weight: 600;
            color: #524f4e;
            background: white;
            box-shadow: 0 8px 15px rgba(0,0,0,.1);
            transition: .3s;
        }
        a:hover {
            background: steelblue;
            box-shadow: 0 15px 20px rgba(29, 98, 229, 0.4);
            color: white;
            transform: translateY(-7px);
        }
        body {
            text-align: center;
            vertical-align: center;
        }
    </style>
</head>
<body>
<div class="parent">
<div class="block">
<a href="<c:url value="/javaBase/students"/>">Студенты</a>
<a href="<c:url value="/javaBase/groups"/>">Группы</a>
<a href="<c:url value="/javaBase/teachers"/>">Преподаватели</a>
</div>
</div>
</body>
</html>

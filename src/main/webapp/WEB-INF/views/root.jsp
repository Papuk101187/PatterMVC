<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html;charset=cp1251" %>
<%@ page pageEncoding="cp1251" %>
<meta charset="utf-8">
<html>
<head>

    <h1> Пользователи : </h1>

    <title>Title</title>


</head>


<body>
<body bgcolor="#A0BEC4">


<ul>

    <c:forEach var="users" items="${users}">

        <li>


            <a
                    href="<c:url value="/users/${users.id}"/>">
                <table border="1">
                    <td><b>${users.fio}</b></td>
                </table>
            </a>


            <table border="1">

                <form action="<c:url value="/delete/${users.id}"/>" method="post">
                    <input type="hidden" value="${users.id}" name="id">
                    <td><input type="submit" value="delete"></td>
                </form>
            </table>


        </li>


    </c:forEach>

</ul>


<form action="<c:url value="add"/>" method="post">

    username: <input type="text" name="name"> <br>
    password: <input type="password" name="password"> <br>
    date: <input type="text" name="date"> <br>


    name_application: <input type="text" name="name_application"> <br>
    description_application: <input type="text" name="description_application"> <br>
    <input type="submit">


</form>


</body>

</html>

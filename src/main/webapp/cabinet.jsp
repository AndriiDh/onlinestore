<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Cabinet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <%@include file="header.jspf"%>
    <div class="container">
        <h3>Cabinet</h3>
        <hr/>
        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
            <button type="submit" name="Show users">Show users</button>
            <button type="submit" name="Add product">Add products</button>
        </c:if>
        <c:if test="${sessionScope.user.role eq Role.CUSTOMER}">
            <button type="submit" name="Edit profile">Edit</button>
            <a href='orders?userId=<c:out value="${sessionScope.user.id}"/>'>
                <button type="submit" name="Orders">Show orders</button>
            </a>
        </c:if>
    </div>
</body>
</html>

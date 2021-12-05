<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="order.label.items"/></title>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<main>
    <%@include file="header.jspf" %>
    <div class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="order.label.id"/></th>
                <th scope="col"><fmt:message key="order.label.status"/></th>
                <th scope="col"><fmt:message key="order.label.price"/></th>
                <th scope="col"><fmt:message key="order.label.items"/></th>
                <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                    <th scope="col"><fmt:message key="order.label.change-status"/></th>
                </c:if>
            </tr>
            </thead>
        </table>
    </div>
</main>
</body>
</html>

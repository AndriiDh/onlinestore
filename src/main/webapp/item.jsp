<%@ page contentType="text/html;charset=UTF-8" import="org.onlinestore.entity.Role" %>
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
    <table class="table table-light">
    <thead>
        <tr>
        <th scope="col"><fmt:message key="cart.label.name"/></th>
        <th scope="col"><fmt:message key="cart.label.category"/></th>
        <th scope="col"><fmt:message key="cart.label.price"/></th>
        <th scope="col"><fmt:message key="cart.label.quantity"/></th>
        </tr>
    </thead>
<tbody class="text-truncate">
    <c:forEach var="item" items="${requestScope.items.entrySet()}">
        <tr>
        <td><c:out value="${item.key.title}"/></td>
        <td><c:out value="${item.key.category.name}"/></td>
        <td><c:out value="${item.key.price}"/></td>
        <td><c:out value="${item.value}"/></td>
        </tr>
    </c:forEach>
        <a href="orders?userId=${requestScope.userId}">Back</a>
        </tbody>
        </table>
    </div>
</main>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="order.label"/></title>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<main>
    <%@include file="header.jspf" %>
    <div class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="order.label.id"/></th>
                <th scope="col"><fmt:message key="order.label.name"/></th>
                <th scope="col"><fmt:message key="order.label.category"/></th>
                <th scope="col"><fmt:message key="order.label.amount"/></th>
                <th scope="col"><fmt:message key="order.label.date"/></th>
                <th scope="col"><fmt:message key="order.label.item-price"/></th>
                <th scope="col"><fmt:message key="order.label.status"/></th>
                <th scope="col"><fmt:message key="order.label.price"/></th>
            </tr>
            </thead>
            <tbody class="text-truncate">
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                <td><c:out value="${order.id}"/></td>
                <c:forEach items="${order.items.entrySet()}" var="item">
                        <td><c:out value="${item.key.title}"/></td>
                        <td><c:out value="${item.key.category.name}"/></td>
                        <td><c:out value="${item.value}"/></td>
                        <td>DATE</td>
                        <td><c:out value="${item.key.price.multiply(item.value)}"/></td>
                </c:forEach>
                <td><c:out value="${order.status}"/></td>
                <td><c:out value="${order.priceOfOrder}"/></td>
            </c:forEach>
            </tr>
            </tbody>
        </table>
    </div>
</main>
<%@include file="footer.jspf"%>
</body>
</html>
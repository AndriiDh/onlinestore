<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
    <c:forEach items="${sessionScope.orders}" var="order">
        <h3><c:out value="${order.id}"/> </h3>
        <h3><c:out value="${order.status}"/> </h3>
        <h3><c:out value="${order.comment}"/> </h3>
        <c:forEach items="${order.items}" var="item">
            <h4><c:out value="${item.title} + ${item.price}"/> </h4>
        </c:forEach>
    </c:forEach>
</body>
</html>

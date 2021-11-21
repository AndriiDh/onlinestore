<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
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
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Category</th>
                <th scope="col">Amount</th>
                <th scope="col">Date</th>
                <th scope="col">Item price</th>
                <th scope="col">Status</th>
                <th scope="col">Price</th>
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
</body>
</html>
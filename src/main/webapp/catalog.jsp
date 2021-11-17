<%@ page contentType="text/html;charset=UTF-8" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>OnlineStore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<main>
    <%@ include file="header.jspf" %>
    <div class="container">
        <div class="card-header my-3">All Products</div>
        <div class="row">
            <c:forEach items="${requestScope.items}" var="item">
            <div class="col-md-3">
                <div class="card w-100" style="width: 18rem;">
                    <img class="card-img-top" src="<c:out value="img/${item.image}"/>" alt="Card image cap">
                    <div class="card-body">
                        <h4 class="card-title"><c:out value="${item.title}"/></h4>
                        <h5 class="card-text"><c:out value="${item.category}"/></h5>
                        <p class="card-text"><c:out value="${item.description}"/> </p>
                        <c:if test="${item.amount != 0}">
                        <a href="addToCart?id=<c:out value="${item.id}"/>" class="btn btn-primary">Add to Cart</a>
                        </c:if>
                        <c:if test="${item.amount == 0}">
                            <a class= "btn btn-default disabled">Out of Stock</a>
                        </c:if>
                        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                            <a href="#" class="btn btn-outline-dark">Edit</a>
                        </c:if>
                    </div>
                </div>
            </div>
                </c:forEach>
        </div>
    </div>
</main>
</body>
</html>

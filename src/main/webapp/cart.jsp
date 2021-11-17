<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="container">
    <div class="d-flex py-3">
        <h3>Total Price : <c:out value="${sessionScope.total}"/></h3>
    </div>
    <table class="table table-light">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Quantity</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${sessionScope.user eq null}">
            <c:forEach var="item" items="${sessionScope.cart}">
                <tr>
                    <td><c:out value="${item.title}"/></td>
                    <td><c:out value="${item.category.name}"/></td>
                    <td><c:out value="${item.price}"/></td>
                    <td>
                        <form action="" method="post" class="form-inline">
                            <input type="hidden" name="id" value="1" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="btn btn-sm btn-incre" href="#"><i
                                        class="fas fa-plus-square"></i></a>
                                <label>
                                    <input type="text" name="quantity" class="form-control" value="1" readonly>
                                </label>
                                <a class="btn btn-sm btn-decre" href="#">
                                    <i class="fas fa-minus-square"></i>
                                </a>
                            </div>
                        </form>
                    </td>
                    <td><a class="btn btn-sm btn-sm btn-dark"
                           href="cartProcessing?edit=delete&id=<c:out value="${item.id}"/>">Remove</a></td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <c:forEach var="item" items="${sessionScope.user.cart}">
                <tr>
                    <td><c:out value="${item.title}"/></td>
                    <td><c:out value="${item.category.name}"/></td>
                    <td><c:out value="${item.price}"/></td>
                    <td>
                        <form action="cartProcessing?delete=<c:out value="${item.id}"/>" method="post"
                              class="form-inline">
                            <input type="hidden" name="id" value="1" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="btn btn-sm btn-incre" href="#"><i
                                        class="fas fa-plus-square"></i></a>
                                <label>
                                    <input type="text" name="quantity" class="form-control" value="1" readonly>
                                </label>
                                <a class="btn btn-sm btn-decre" href="#">
                                    <i class="fas fa-minus-square"></i>
                                </a>
                            </div>
                        </form>
                    </td>
                    <td><a class="btn btn-sm btn-sm btn-dark"
                           href="cartProcessing?edit=delete&id=<c:out value="${item.id}"/>">Remove</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <c:if test="${sessionScope.user != null && not empty sessionScope.user.cart}">
        <a class="btn btn-lg btn-outline-dark" href="buy.jsp">Buy</a>
    </c:if>
</div>

</body>
</html>

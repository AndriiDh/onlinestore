<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="cart.label"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta2/css/all.min.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="container">
    <div class="d-flex py-3">
        <h3><fmt:message key="cart.label.total-price"/> <c:out value="${sessionScope.total}"/></h3>
    </div>
    <table class="table table-light">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="cart.label.name"/></th>
            <th scope="col"><fmt:message key="cart.label.category"/></th>
            <th scope="col"><fmt:message key="cart.label.price"/></th>
            <th scope="col"><fmt:message key="cart.label.quantity"/></th>
            <th scope="col"><fmt:message key="cart.label.cancel"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${sessionScope.user eq null}">
            <c:forEach var="item" items="${sessionScope.cart.entrySet()}">
                <tr>
                    <td><c:out value="${item.key.title}"/></td>
                    <td><c:out value="${item.key.category.name}"/></td>
                    <td><c:out value="${item.key.price}"/></td>
                    <td>
                        <form class="form-inline">
                            <input type="hidden" name="id" value="1" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="btn btn-sm btn-decrease"
                                   href="cartProcessing?action=decrease&id=<c:out value="${item.key.id}"/>"><i
                                        class="fas fa-minus-square"></i></a>
                                <label>
                                    <input type="text" name="quantity" class="form-control" value="<c:out value="${item.value}"/>" readonly>
                                </label>
                                <a class="btn btn-sm btn-increase"
                                   href="cartProcessing?action=increase&id=<c:out value="${item.key.id}"/>">
                                    <i class="fas fa-plus-square"></i>
                                </a>
                            </div>
                        </form>
                    </td>
                    <td><a class="btn btn-sm btn-sm btn-dark"
                           href="cartProcessing?action=delete&id=<c:out value="${item.key.id}"/>"><fmt:message key="cart.label.remove"/></a></td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <c:forEach var="item" items="${sessionScope.user.cart.entrySet()}">
                <tr>
                    <td><c:out value="${item.key.title}"/></td>
                    <td><c:out value="${item.key.category.name}"/></td>
                    <td><c:out value="${item.key.price}"/></td>
                    <td>
                        <form action="cartProcessing?delete=<c:out value="${item.key.id}"/>" method="post"
                              class="form-inline">
                            <input type="hidden" name="id" value="1" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="btn btn-sm btn-decrease" href="cartProcessing?action=decrease&id=<c:out value="${item.key.id}"/>"><i
                                        class="fas fa-minus-square"></i></a>
                                <label>
                                    <input type="text" name="quantity" class="form-control" value="<c:out value="${item.value}"/> " readonly>
                                </label>
                                <a class="btn btn-sm btn-increase" href="cartProcessing?action=increase&id=<c:out value="${item.key.id}"/>">
                                    <i class="fas fa-plus-square"></i>
                                </a>
                            </div>
                        </form>
                    </td>
                    <td><a class="btn btn-sm btn-sm btn-dark"
                           href="cartProcessing?action=delete&id=<c:out value="${item.key.id}"/>"><fmt:message key="cart.label.remove"/></a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <c:if test="${sessionScope.user != null && not empty sessionScope.user.cart}">
        <a class="btn btn-lg btn-outline-dark" href="buy.jsp"><fmt:message key="cart.label.buy"/></a>
    </c:if>
</div>
<%@include file="footer.jspf"%>
</body>
</html>

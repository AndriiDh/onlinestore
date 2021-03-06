<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="order.label"/></title>
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
            <tbody class="text-truncate">
            <br/>
            <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <td><c:out value="${order.id}"/></td>
                <td><c:out value="${order.status}"/></td>
                <td><c:out value="${order.priceOfOrder}"/></td>
                <td>
                    <input type="hidden" value="${order.items}" name="items">
                    <a class="text-center btn btn-lg btn-primary mt-3" href="order-management?action=show-items&id=${order.id}&userId=${requestScope.userId}">
                    <fmt:message key="order.label.items"/></a>
                </td>
                <td>
                    <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                        <form method="post" action="order-management">
                        <select id="status" class="form-select" name="status">
                            <option value="registered"><fmt:message key="order.label.status.registered"/></option>
                            <option value="payed"><fmt:message key="order.label.status.payed"/></option>
                            <option value="canceled"><fmt:message key="order.label.status.canceled"/></option>
                        </select>
                            <input name="id" type="hidden" value="${order.id}">
                            <button name="action" value="change-status" class="w-100 btn btn-lg btn-primary" type="submit">
                                <fmt:message key="order.label.change-status"/>
                            </button>
                        </form>
                    </c:if>
                </td>
                </c:forEach>
            </tr>
            </tbody>
        </table>
    </div>
</main>
<%@include file="footer.jspf" %>
</body>
</html>
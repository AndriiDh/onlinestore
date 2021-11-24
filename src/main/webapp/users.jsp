<%@ page contentType="text/html;charset=UTF-8" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<main>
    <%@include file="header.jspf" %>
    <div class="py-5 bg-light">
        <div class="container">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Role</th>
                    <th scope="col">Banned</th>
                    <th scope="col">Orders</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.users_list}" var="user">
                    <tr>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                        <td>
                            <form action="UserManagement" method="post">
                                <input name="id" value="${user.id}" hidden>
                                <c:if test="${not user.banned}">
                                    <button name="action" value="ban" type="submit" class="btn btn-danger w-auto">
                                        Ban
                                    </button>
                                </c:if>
                                <c:if test="${user.banned}">
                                    <button name="action" value="unban" type="submit" class="btn btn-danger w-auto">
                                        Unban
                                    </button>
                                </c:if>
                                <c:if test="${user.role eq Role.CUSTOMER}">
                                    <button name="action" value="make-manager" type="submit" class="btn btn-primary w-auto">
                                        Make Manager
                                    </button>
                                </c:if>
                                <c:if test="${user.role eq Role.MANAGER}">
                                    <button name="action" value="make-customer" type="submit" class="btn btn-primary w-auto">
                                        Make Customer
                                    </button>
                                </c:if>
                            </form>
                            </c:if>
                        </td>
                        <td>
                            <form action="user-orders" method="get">
                                <input name="id" value="${user.id}" hidden>
                                <input class="btn btn-primary" type="submit" value="Orders">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>

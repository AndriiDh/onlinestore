<%@ page contentType="text/html;charset=UTF-8" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="user.label"/></title>
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
                    <th scope="col"><fmt:message key="user.label.first-name"/></th>
                    <th scope="col"><fmt:message key="user.label.last-name"/></th>
                    <th scope="col"><fmt:message key="user.label.email"/></th>
                    <th scope="col"><fmt:message key="user.label.role"/></th>
                    <th scope="col"><fmt:message key="user.label.banned"/></th>
                    <th scope="col"><fmt:message key="user.label.orders"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.users_list}" var="user">
                    <c:if test="${user.role != Role.ADMIN}">
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
                                            <fmt:message key="user.label.ban"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.banned}">
                                        <button name="action" value="unban" type="submit" class="btn btn-danger w-auto">
                                            <fmt:message key="user.label.unban"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role eq Role.CUSTOMER}">
                                        <button name="action" value="make-manager" type="submit"
                                                class="btn btn-primary w-auto">
                                            <fmt:message key="user.label.make-manager"/>
                                        </button>
                                    </c:if>
                                    <c:if test="${user.role eq Role.MANAGER}">
                                        <button name="action" value="make-customer" type="submit"
                                                class="btn btn-primary w-auto">
                                            <fmt:message key="user.label.make-customer"/></button>
                                    </c:if>
                                </form>
                                </c:if>
                            </td>
                            <td>
                                <form action="orders" method="get">
                                    <input name="userId" value="${user.id}" hidden>
                                    <input class="btn btn-primary" type="submit" value=<fmt:message
                                            key="user.label.orders"/>>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>

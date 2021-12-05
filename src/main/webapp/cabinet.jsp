<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="cabinet.label"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <%@include file="header.jspf"%>
    <div class="container">
        <h3><fmt:message key="cabinet.label"/></h3>
        <hr/>
        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
            <a class="btn btn-sm btn-light" type="submit" href="users"><fmt:message key="cabinet.label.users"/></a>
            <a class="btn btn-sm btn-light" type="submit" href="itemManagement?action=new"><fmt:message key="cabinet.label.add-product"/></a>
        </c:if>
            <button class="btn btn-sm btn-light" type="submit" name="Edit profile"><fmt:message key="cabinet.label.edit"/></button>
            <a href='orders?userId=<c:out value="${sessionScope.user.id}"/>'>
                <button class="btn btn-sm btn-light" type="submit" name="Orders"><fmt:message key="cabinet.label.show-orders"/></button>
            </a>
    </div>
    <%@include file="footer.jspf"%>
</body>
</html>

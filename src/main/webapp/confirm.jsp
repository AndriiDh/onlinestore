<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <link href="css/confirm.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <title><fmt:message key="confirm.label"/></title>
</head>
<body>
<main class="align-content-center">
    <div class="container">
            <h2><fmt:message key="confirm.label.sure"/></h2>
            <form method="post" action="delete-servlet">
                <input id="id" name="id" value="${requestScope.id}" hidden>
                <button class="btn btn-outline-dark"><fmt:message key="confirm.label.delete"/></button>
            </form>
            <a class="btn btn-outline-danger" href="catalog"><fmt:message key="confirm.label.cancel"/></a>
    </div>
</main>
</body>
</html>

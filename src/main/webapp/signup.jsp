<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="signup.label"/></title>
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="text-center">
<main class="form-login">
    <div class="container">
        <form class="needs-validation" action="signup" method="post">
            <h2 class="h3 mb-3 fw-normal"><fmt:message key="signup.label"/></h2>
            <div class="form-floating">
                <input name="login" class="form-control" type="text" id="login" required>
                <label for="login"><fmt:message key="login.label"/></label>
            </div>
            <div class="form-floating">
                <input name="first_name" class="form-control" type="text" id="first_name" required>
                <label for="first_name"><fmt:message key="signup.label.first-name"/></label>
            </div>
            <div class="form-floating">
                <input name="last_name" class="form-control" type="text" id="last_name" required>
                <label for="last_name"><fmt:message key="signup.label.last-name"/></label>
            </div>
            <div class="form-floating">
                <input name="password" class="form-control" type="password" id="password" required>
                <label for="password"><fmt:message key="signup.label.password"/></label>
            </div>
            <div class="form-floating">
                <input name="email" class="form-control" type="email" id="email" required>
                <label for="email"><fmt:message key="signup.label.email"/></label>
            </div>
            <div class="form-floating">
                <input name="phone" class="form-control" type="tel" id="phone">
                <label for="phone"><fmt:message key="signup.label.phone-number"/></label>
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="signup.label"/></button>
        </form>
    </div>
</main>
</body>
</html>
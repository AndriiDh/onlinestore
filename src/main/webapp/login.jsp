<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="utf-8">
    <title><fmt:message key="login.label"/></title>
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
    <body class="text-center">
        <main class="form-login">
            <form class="needs-validation" action="login" method="post">
                <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.label"/></h1>
                <c:if test="${sessionScope['invalid-login']}">
                    <div class="form-floating">
                        <input name="login" type="text" class="form-control is-invalid" id="login" placeholder="name@example.com">
                        <label for="login"><fmt:message key="login.label"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="login.label.wrong-login"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not sessionScope['invalid-login']}">
                    <div class="form-floating">
                        <input name="login" type="text" class="form-control" id="login" placeholder="name@example.com">
                        <label for="login"><fmt:message key="login.label"/></label>
                    </div>
                </c:if>
                <c:if test="${sessionScope['invalid-password']}">
                    <div class="form-floating">
                        <input name="password" type="password" class="form-control is-invalid" id="password"
                               placeholder="Password">
                        <label for="password"><fmt:message key="login.label.password"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="login.label.wrong-password"/>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not sessionScope['invalid-password']}">
                    <div class="form-floating">
                        <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                        <label for="password"><fmt:message key="login.label.password"/></label>
                    </div>
                </c:if>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="login.label"/></button>
            </form>
        </main>
    </body>
</html>

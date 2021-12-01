<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="error.label"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@include file="header.jspf"%>
<main>

    <section class="py-5 text-center container">
        <div class="row py-lg-5">
            <div class="col-lg-6 col-md-8 mx-auto">
                <h1 class="fw-light"><fmt:message key="error.label.oops"/></h1>
                <c:if test="${requestScope.Ban}">
                    <h2 class="fw-light"><fmt:message key="error.label.banned"/></h2>
                </c:if>
                <p class="lead"><fmt:message key="error.label.message"/></p>
                <p>
                    <a href="catalog" class="btn btn-primary my-2"><fmt:message key="error.button.to-the-main"/></a>
                </p>
            </div>
        </div>
    </section>

</main>

</body>
</html>
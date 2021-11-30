<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <c:if test="${not empty requestScope.item}">
        <title><fmt:message key="new-item.label.edit"/></title>
    </c:if>
    <c:if test="${empty requestScope.item}">
        <title><fmt:message key="new-item.label.new"/></title>
    </c:if>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="text-center">
    <main class="text-body">
        <form action="newItemServlet" method="post">
        <c:if test="${not empty requestScope.item}">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="new-item.label.edit-item"/></h1>
        </c:if>
        <c:if test="${empty requestScope.item}">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="new-item.label.new-item"/></h1>
        </c:if>
        <div class="form-floating">
            <input name="title" value="${requestScope.item.title}" type="text" class="form-control" id="title" placeholder="Title">
            <label for="title"><fmt:message key="new-item.label.title"/></label>
        </div>
        <div class="form-floating">
            <input name="price" value="${requestScope.item.price}" type="text" class="form-control" id="price" placeholder="Title">
            <label for="price"><fmt:message key="new-item.label.price"/></label>
        </div>
        <div class="form-floating">
            <select id="category" class="form-select" name="category">
                <c:forEach var="category" items="${requestScope.categories}">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </select>
            <label for="category"><fmt:message key="new-item.label.category"/></label>
        </div>
        <div class="form-floating">
            <input name="image" value="${requestScope.item.image}" type="text" class="form-control" id="image" placeholder="Image">
            <label for="image"><fmt:message key="new-item.label.image"/></label>
        </div>
        <div class="form-floating">
            <input name="amount" value="${requestScope.item.amount}" type="number" class="form-control" id="amount" placeholder="Amount">
            <label for="amount"><fmt:message key="new-item.label.amount"/></label>
        </div>
        <div class="form-floating">
            <input name="description" value="${requestScope.item.description}" type="text" class="form-control" id="description" placeholder="Description">
            <label for="description"><fmt:message key="new-item.label.description"/></label>
        </div>
        <c:if test="${empty requestScope.item}">
            <button name="action" value="new" class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="new-item.label.add"/></button>
        </c:if>
        <c:if test="${not empty requestScope.item}">
            <input name="id" type="hidden" value="${requestScope.item.id}">
            <button name="action" value="new" class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="new-item.label.edit-item"/></button>
        </c:if>
        </form>
    </main>
</body>
</html>

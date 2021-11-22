<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${not empty requestScope.item}">
        <title>Edit</title>
    </c:if>
    <c:if test="${empty requestScope.item}">
        <title>New</title>
    </c:if>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body class="text-center">
    <main class="text-body">
        <form action="newItemServlet" method="post">
        <c:if test="${not empty requestScope.item}">
            <h1 class="h3 mb-3 fw-normal">Edit Item</h1>
        </c:if>
        <c:if test="${empty requestScope.item}">
            <h1 class="h3 mb-3 fw-normal">New Item</h1>
        </c:if>
        <div class="form-floating">
            <input name="title" value="${requestScope.item.title}" type="text" class="form-control" id="title" placeholder="Title">
            <label for="title">Title</label>
        </div>
        <div class="form-floating">
            <input name="price" value="${requestScope.item.price}" type="text" class="form-control" id="price" placeholder="Title">
            <label for="price">Price</label>
        </div>
        <div class="form-floating">
            <input name="category" value="${requestScope.item.category}" type="text" class="form-control" id="category" placeholder="Category">
            <label for="category">Category</label>
        </div>
        <div class="form-floating">
            <input name="image" value="${requestScope.item.image}" type="text" class="form-control" id="image" placeholder="Image">
            <label for="image">Image</label>
        </div>
        <div class="form-floating">
            <input name="amount" value="${requestScope.item.amount}" type="number" class="form-control" id="amount" placeholder="Amount">
            <label for="amount">Amount</label>
        </div>
        <div class="form-floating">
            <input name="description" value="${requestScope.item.description}" type="text" class="form-control" id="description" placeholder="Description">
            <label for="description">Description</label>
        </div>
        <c:if test="${empty requestScope.item}">
            <button name="action" value="new" class="w-100 btn btn-lg btn-primary" type="submit">Add Item</button>
        </c:if>
        <c:if test="${not empty requestScope.item}">
            <input name="id" type="hidden" value="${requestScope.item.id}">
            <button name="action" value="new" class="w-100 btn btn-lg btn-primary" type="submit">Edit Item</button>
        </c:if>
        </form>
    </main>
</body>
</html>

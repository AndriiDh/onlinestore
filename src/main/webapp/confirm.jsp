<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/confirm.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Confirm</title>
</head>
<body>
<main class="align-content-center">
    <div class="container">
            <h2>Are you sure? </h2>
            <form method="post" action="delete-servlet">
                <input id="id" name="id" value="${requestScope.id}" hidden>
                <button class="btn btn-outline-dark">Delete</button>
            </form>
            <a class="btn btn-outline-danger" href="catalog">Cancel</a>
    </div>
</main>
</body>
</html>

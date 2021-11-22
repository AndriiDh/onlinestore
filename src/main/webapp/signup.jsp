<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Sing Up</title>
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="text-center">
<main class="form-login">
    <div class="container">
        <form class="needs-validation" action="signup" method="post">
            <h2 class="h3 mb-3 fw-normal">Sing up</h2>
            <div class="form-floating">
                <input name="login" class="form-control" type="text" id="login" required>
                <label for="login">Login</label>
            </div>
            <div class="form-floating">
                <input name="first_name" class="form-control" type="text" id="first_name" required>
                <label for="first_name">FirstName</label>
            </div>
            <div class="form-floating">
                <input name="last_name" class="form-control" type="text" id="last_name" required>
                <label for="last_name">LastName</label>
            </div>
            <div class="form-floating">
                <input name="password" class="form-control" type="password" id="password" required>
                <label for="password">Password</label>
            </div>
            <div class="form-floating">
                <input name="email" class="form-control" type="email" id="email" required>
                <label for="email">Email</label>
            </div>
            <div class="form-floating">
                <input name="phone" class="form-control" type="tel" id="phone">
                <label for="phone">Phone Number</label>
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit">Sing Up</button>
        </form>
    </div>
</main>
</body>
</html>
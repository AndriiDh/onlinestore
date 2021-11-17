<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 >Sing up</h1>
    <form action="signup" method="post">
        <label for="login">Login</label>
        <input name="login" type="text" id="login" required><br/>
        <label for="first_name">FirstName</label>
        <input name="first_name" type="text" id="first_name" required><br/>
        <label for="last_name">LastName</label>
        <input name="last_name" type="text" id="last_name" required><br/>
        <label for="password">Password</label>
        <input name="password" type="password" id="password" required><br/>
        <!--<input name="password_confirm" type="password" id="password_confirm">
        <label for="password_confirm">Confirm your password</label>
        -->
        <label for="email">Email</label>
        <input name="email" type="email" id="email" required><br/>
        <label for="phone">Phone Number</label>
        <input name="phone" type="tel" id="phone"><br/>
        <br/>
        <button type="submit">Sing Up</button><br/>
    </form>
</div>
</body>
</html>

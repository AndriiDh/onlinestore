<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link href="css/login.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
    <body class="text-center">
        <main class="form-login">
            <form class="needs-validation" action="logInServlet" method="post">
                <h1 class="h3 mb-3 fw-normal">Login</h1>
                <c:if test="${sessionScope['invalid-login']}">
                    <div class="form-floating">
                        <input name="login" type="text" class="form-control is-invalid" id="login" placeholder="name@example.com">
                        <label for="login">Login</label>
                        <div class="invalid-feedback">
                            Wrong login
                        </div>
                    </div>
                </c:if>
                <c:if test="${sessionScope['invalid-password']}">
                    <div class="form-floating">
                        <input name="password" type="password" class="form-control is-invalid" id="password"
                               placeholder="Password">
                        <label for="password">Password</label>
                        <div class="invalid-feedback">
                            Wrong password
                        </div>
                    </div>
                </c:if>
                <c:if test="${not sessionScope['invalid-login']}">
                    <div class="form-floating">
                        <input name="login" type="text" class="form-control" id="login" placeholder="name@example.com">
                        <label for="login">Login</label>
                    </div>
                </c:if>
                <c:if test="${not sessionScope['invalid-password']}">
                    <div class="form-floating">
                        <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                        <label for="password">Password</label>
                    </div>
                </c:if>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Login</button>
            </form>
        </main>
    </body>
</html>

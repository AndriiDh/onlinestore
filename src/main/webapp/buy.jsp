<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<%@ include file="header.jspf" %>
<form name="order" action="buy" method="post">
    <div class="container mt-5">
        <label for="comment">Comment</label>
        <input name="comment" type="text" id="comment"/>
        <button type="submit" class="btn btn-primary">Submit</button>
    </div>
</form>
<%@include file="footer.jspf" %>
</body>
</html>

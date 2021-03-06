<%@ page contentType="text/html;charset=UTF-8" import="org.onlinestore.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="messages"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><fmt:message key="store.label"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<main>
    <%@ include file="header.jspf" %>
    <div class="container">
        <div class="card-header my-3"><fmt:message key="catalog.label.all-products"/></div>
        <form class="row g-3 align-items-center" action="catalog" method="get">
            <div class="col-7">
                <div class="form-floating">
                    <input id="q" name="q" type="text" class="form-control" placeholder="Search...">
                    <label for="q"><fmt:message key="catalog.label.search"/></label>
                </div>
            </div>
            <div class="col-2">
                <div class="form-floating">
                    <select id="search-by" class="form-select" name="search-by" aria-label="Search by">
                        <option value="title"><fmt:message key="catalog.label.title"/></option>
                    </select>
                    <label for="search-by"><fmt:message key="catalog.label.search-by"/></label>
                </div>
            </div>
            <div class="col-2">
                <div class="form-floating">
                    <select id="sort-by" class="form-select" name="sort-by">
                        <option value="title"><fmt:message key="catalog.label.title"/></option>
                        <option value="add_time"><fmt:message key="catalog.label.newest"/></option>
                        <option value="category"><fmt:message key="catalog.label.category"/></option>
                        <option value="price"><fmt:message key="catalog.label.price"/></option>
                    </select>
                    <label for="sort-by"><fmt:message key="catalog.label.sort-by"/></label>
                </div>
            </div>
            <div class="col-1">
                <button class="btn btn-lg btn-outline-secondary w-100" type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                    </svg>
                </button>
            </div>
            <input name="page" value="1" hidden>
        </form>
        <div class="row">
            <c:forEach items="${requestScope.items}" var="item">
                <c:url var="catalogPage" value="addToCart">
                    <c:forEach items="${param}" var="entry">
                        <c:if test="${entry.key} != 'id' ">
                            <c:param name="${entry.key}" value="${entry.value}"/>
                        </c:if>
                    </c:forEach>
                    <c:param name="id" value="${item.id}"/>
                </c:url>
            <div class="col-md-3 mb-1">
                <div class="card w-100" style="width: 18rem;">
                    <div class="container" style="height: 16rem">
                        <img class="card-img-top" style="size: 20px" src="<c:out value="img/${item.image}"/>" alt="Card image cap">
                    </div>
                    <div class="card-body">
                        <h4 class="card-title"><c:out value="${item.title}"/></h4>
                        <h5 class="card-text"><c:out value="${item.category}"/></h5>
                        <p class="card-text"><c:out value="${item.description}"/> </p>
                        <p class="card-link"><fmt:message key="catalog.label.price"/> : <c:out value="${item.price}"/></p>
                        <c:if test="${item.amount != 0}">
                        <a href="<c:out value="${catalogPage}"/>" class="btn btn-primary"><fmt:message key="catalog.label.add-to-cart"/></a>
                        </c:if>
                        <c:if test="${item.amount == 0}">
                            <a class= "btn btn-default disabled"><fmt:message key="catalog.label.out-of-store"/></a>
                        </c:if>
                        <c:if test="${sessionScope.user.role eq Role.ADMIN}">
                            <a href="itemManagement?action=edit&id=${item.id}" class="btn btn-outline-dark"><fmt:message key="catalog.label.edit"/></a>
                            <a href="itemManagement?action=delete&id=${item.id}" class="btn btn-outline-dark"><fmt:message key="catalog.label.delete"/></a>
                        </c:if>
                    </div>
                </div>
            </div>
                </c:forEach>
        </div>
        <nav>
            <ul class="pagination justify-content-center">
                <c:forEach var="i" begin="${1}" end="${requestScope.amount_of_items}">
                    <li class="page-item">
                        <a class="page-link" href="catalog?q=${param.q}&search-by=${param['search-by']}&sort-by=${param['sort-by']}&page=${i}">
                            <c:out value="${i}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</main>
<%@include file="footer.jspf"%>
</body>
</html>

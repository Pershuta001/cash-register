<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.example.cash_register.model.service.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.cash_register.utils.CurrencyConvertor" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="page.product.products.label"/></h1>

<div>
    <a href="?page=1&sort=fromCheep">
        <fmt:message key="fromCheep"/>
    </a>
    <a href="?page=1&sort=fromExpensive">
        <fmt:message key="fromExpensive"/>
    </a>
    <a href="?page=1&sort=byAlphabet">
        <fmt:message key="byAlphabet"/>
    </a>
    <a href="?page=1&sort=byAlphabetReverse">
        <fmt:message key="byAlphabetReverse"/>
    </a>
    <a href="?page=1&sort=byQuantity">
        <fmt:message key="byQuantity"/>
    </a>
    <a href="?page=1&sort=byWeight">
        <fmt:message key="byWeight"/>
    </a>
    <a href="?page=1&sort=byId">
        <fmt:message key="byId"/>
    </a>
</div>

<div>
    <div>

        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
        %>

        <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Amount</th>
            </tr>
            </thead>
            <tbody>

            <%
                for (Product product : products) {
                    out.println("<tr>");
                    out.println("<td> " + product.getId() + "</td>");
                    out.println("<td>" + product.getName() + "</td>");
                    out.println("<td>" + CurrencyConvertor.convertToUSD(product.getPrice()) + "</td>");
                    out.println("<td>" + (product.isByWeight() ? product.getAvailable_weight() + " kg" : product.getAvailable_quantity()) + "</td>");
                    out.println("<td><a href=\"${pageContext.request.contextPath}/app/product/update?id=" + product.getId()
                            + "\">Update</a></td>");
                    out.println("<td><a href=\"${pageContext.request.contextPath}/app/product/delete?page=" + request.getAttribute("page") + "&sort=" + request.getAttribute("sort") + "&id=" + product.getId()
                            + "\">Delete</a></td>");
                    out.println("</tr>");
                }
            %>

            </tbody>
        </table>

        <%
            } else {
                out.println("<p>There are no products yet!</p>");
            }
        %>

    </div>
</div>
<div>
    <%
        Integer currentPage = (Integer) request.getAttribute("page");
        Integer pagesCount = (Integer) request.getAttribute("pagesCount");

        if (currentPage > 1)
            out.println("<a href=\"?page=" + (currentPage - 1) + "&sort=" + request.getAttribute("sort") + "\">Prev</a>");
        if (currentPage < pagesCount)
            out.println("<a href=\"?page=" + ((Integer) request.getAttribute("page") + 1) + "&sort=" + request.getAttribute("sort") + "\">Next</a>");
    %>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

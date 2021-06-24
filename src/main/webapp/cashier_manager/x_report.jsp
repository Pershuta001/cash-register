<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="com.example.cash_register.model.service.ProductService" %>
<%@ page import="com.example.cash_register.utils.CurrencyConvertor" %>
<%@ page import="java.util.*" %>
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
    <div>
        <a href="?page=1&sort=products">
            <fmt:message key="report.product"/>
        </a>
    </div>
    <div>
        <a href="?page=1&sort=cashiers">
            <fmt:message key="report.product2"/>
        </a>
    </div>
</div>
<div>

    <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price(item/kg)</th>
            <th>Amount</th>
            <th>Total price</th>
        </tr>
        </thead>
        <tbody>
        <%
            Map<Product, Double> items = (Map<Product, Double>) request.getAttribute("items");
            if (items != null && !items.isEmpty()) {
                for (Map.Entry<Product, Double> entry : items.entrySet()) {
                    Product product = entry.getKey();
                    out.println("<tr>");
                    out.println("<td> " + product.getId() + "</td>");
                    out.println("<td>" + product.getName() + "</td>");
                    out.println("<td>" + CurrencyConvertor.convertToUSD(product.getPrice()) + "</td>");
                    out.println("<td>" + (product.isByWeight() ? product.getAvailable_weight() + " kg" : product.getAvailable_quantity()) + "</td>");
                    out.println("<td>" + CurrencyConvertor.convertToUSD(product.getPrice() * (product.isByWeight() ? product.getAvailable_weight() : product.getAvailable_quantity())) + "</td>");

                }
            }
        %>
        </tbody>
    </table>

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

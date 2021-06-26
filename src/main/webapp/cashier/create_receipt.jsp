<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.example.cash_register.model.service.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.cash_register.utils.CurrencyConvertor" %>
<%@ page import="com.example.cash_register.model.entity.Receipt" %>
<%@ page import="java.util.Map" %>
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
        <c:if test="${empty requestScope.receipt}">
            <form method="post"
                  action="${pageContext.request.contextPath}/app/receipt/create">
                <button type="submit">
                    <fmt:message key="receipt.create"/>
                </button>
            </form>
        </c:if>
        <c:if test="${not empty requestScope.receipt}">

            <form method="post"
                  action="${pageContext.request.contextPath}/app/receipt/create?receiptId=${requestScope.receipt.id}">
                <div>
                    <label for="name">
                        <fmt:message key="product.nameorid.label"/>
                    </label>
                    <input type="text"
                           id="name"
                           name="name"
                           pattern="[a-zA-Zа-яА-Я0-9]{1,30}"
                           required
                    >
                </div>
                <div>
                    <label for="amount">
                        <fmt:message key="product.amount.label"/>
                    </label>

                    <input type="text"
                           id="amount"
                           name="amount"
                           pattern="[0-9]{1,5}[\.]?[0-9]{0,2}"
                           required
                    >
                </div>
                <div>
                    <button type="submit">
                        <fmt:message key="cashier.add"/>
                    </button>
                </div>
            </form>
            <form method="post"
                  action="${pageContext.request.contextPath}/app/receipt/confirm?receiptId=${requestScope.receipt.id}">
                <button type="submit">
                    <fmt:message key="receipt.close"/>
                </button>
            </form>
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

                    Receipt receipt = (Receipt) request.getAttribute("receipt");

                    for (Map.Entry<Product, Double> product : receipt.getProductsInReceipt().entrySet()) {
                        Product prod = product.getKey();
                        out.println("<tr>");
                        out.println("<td> " + prod.getId() + "</td>");
                        out.println("<td>" + prod.getName() + "</td>");
                        out.println("<td>" + CurrencyConvertor.convertToUSD(prod.getPrice()) + "</td>");
                        out.println("<td>" + product.getValue() + (prod.isByWeight() ? " kg" : "items") + "</td>");

                    }
                %>

                </tbody>
            </table>
        </c:if>
    </div>
    <div>

    </div>
</div>
<div>
    <%--    <%--%>
    <%--        Integer currentPage = (Integer) request.getAttribute("page");--%>
    <%--        Integer pagesCount = (Integer) request.getAttribute("pagesCount");--%>

    <%--        --%>
    <%--        --%>
    <%--        if (currentPage > 1)--%>
    <%--            out.println("<a href=\"?page=" + (currentPage - 1) + "&sort=" + request.getAttribute("sort")--%>
    <%--                    + "\">Prev</a>");--%>
    <%--        if (currentPage < pagesCount)--%>
    <%--            out.println("<a href=\"?page=" + ((Integer) request.getAttribute("page") + 1) +--%>
    <%--                    "&sort=" + request.getAttribute("sort") + "\">Next</a>");--%>
    <%--    %>--%>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

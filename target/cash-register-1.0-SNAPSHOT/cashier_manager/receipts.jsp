<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.example.cash_register.model.service.ProductService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.cash_register.utils.CurrencyConvertor" %>
<%@ page import="com.example.cash_register.model.entity.Receipt" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="page.product.receipts.label"/></h1>

<div>
    <div>

        <%
            List<Receipt> receipts = (List<Receipt>) request.getAttribute("receipts");
            if (receipts != null && !receipts.isEmpty()) {
        %>

        <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Cashier Id</th>
                <th>Date</th>

            </tr>
            </thead>
            <tbody>

            <%
                for (Receipt receipt : receipts) {
                    out.println("<tr>");
                    out.println("<td> " + receipt.getId() + "</td>");
                    out.println("<td>" + receipt.getCashierId() + "</td>");
                   // out.println("<td>" + receipt.getDate()+"</td>");
                    out.println("<td><a href=\"${pageContext.request.contextPath}/app/receipt/update?id=" + receipt.getId()
                            + "\">Update</a></td>");
                    out.println("<td><a href=\"${pageContext.request.contextPath}/app/receipt/delete?page=" + request.getAttribute("page") + "&sort=" + request.getAttribute("sort") + "&id=" + receipt.getId()
                            + "\">Delete</a></td>");
                    out.println("</tr>");
                }
            %>

            </tbody>
        </table>

        <%
            } else {
                out.println("<p>There are no receipts yet!</p>");
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

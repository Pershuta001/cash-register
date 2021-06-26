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
        <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
            <thead>
            <tr>
                <th>
                    <fmt:message key="receipt.id"/>
                </th>
                <th>
                    <fmt:message key="receipt.cashier.id"/>
                </th>
                <th>
                    <fmt:message key="receipt.date"/>
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${requestScope.receipts}" var="receipt">
                <tr>
                    <td>${receipt.id}</td>
                    <td>${receipt.cashierId}</td>
                    <td>${receipt.date} </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/receipt/view?id=${receipt.id}">
                            <fmt:message key="receipt.view"/>
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/receipt/delete?id=${receipt.id}">
                            <fmt:message key="receipt.delete"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>
<div>
    <%--    <%--%>
    <%--        Integer currentPage = (Integer) request.getAttribute("page");--%>
    <%--        Integer pagesCount = (Integer) request.getAttribute("pagesCount");--%>

    <%--        if (currentPage > 1)--%>
    <%--            out.println("<a href=\"?page=" + (currentPage - 1) + "&sort=" + request.getAttribute("sort") + "\">Prev</a>");--%>
    <%--        if (currentPage < pagesCount)--%>
    <%--            out.println("<a href=\"?page=" + ((Integer) request.getAttribute("page") + 1) + "&sort=" + request.getAttribute("sort") + "\">Next</a>");--%>
    <%--    %>--%>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

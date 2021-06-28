<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="product.products"/></h1>

<div>
    <div>
        <a href="?page=1&sort=products">
            <fmt:message key="report.product"/>
        </a>
    </div>
    <div>
        <a href="?page=1&sort=cashiers">
            <fmt:message key="report.cashiers"/>
        </a>
    </div>
</div>
<div>

    <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
        <c:choose>
            <c:when test="${requestScope.sort == 'products'}">
                <thead>
                <tr>
                    <th>
                        <fmt:message key="product.id"/>
                    </th>
                    <th>
                        <fmt:message key="product.name"/>
                    </th>
                    <th>
                        <fmt:message key="product.price"/>
                    </th>
                    <th>
                        <fmt:message key="product.amount"/>
                    </th>
                    <th>
                        <fmt:message key="product.price"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.items}" var="product">
                    <tr>
                        <td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>${product.price} </td>
                        <td>${product.soldAmount} </td>
                        <td>${product.totalPrice} </td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:when>

            <c:when test="${requestScope.sort == 'cashiers'}">
                <thead>
                <tr>
                    <th>
                        <fmt:message key="cashier.id"/>
                    </th>
                    <th>
                        <fmt:message key="cashier.name"/>
                    </th>
                    <th>
                        <fmt:message key="cashier.surname"/>
                    </th>
                    <th>
                        <fmt:message key="cashier.receipts"/>
                    </th>
                    <th>
                        <fmt:message key="total.price"/>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.items}" var="cashier">
                    <tr>
                        <td>${cashier.id}</td>
                        <td>${cashier.name}</td>
                        <td>${cashier.surname} </td>
                        <td>${cashier.numberOfReceipts} </td>
                        <td>${cashier.totalPrice}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:when>
        </c:choose>
    </table>

    <jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

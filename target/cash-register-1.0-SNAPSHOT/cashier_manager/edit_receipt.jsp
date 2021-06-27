<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>
<jsp:useBean id="convertor"
             class="com.example.cash_register.utils.CurrencyConvertor"/>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="receipt.id"/>: ${requestScope.receipt.id}</h1>
<h2><fmt:message key="cashier.id"/>: ${requestScope.receipt.cashierId}</h2>
<h2><fmt:message key="receipt.date"/>: ${requestScope.receipt.date}</h2>
<h2><fmt:message key="receipt.total.price"/>: ${requestScope.receipt.getTotalPrice()}</h2>
<div>
    <div>
        <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
            <thead>
            <tr>
                <th>
                    <c:if test="${requestScope.sort != 'byId'}">
                        <a href="?page=1&sort=byId">
                            <fmt:message key="product.id"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byId'}">
                        <a href="?page=1&sort=byIdReverse">
                            <fmt:message key="product.id"/>
                        </a>
                    </c:if>
                </th>
                <th>
                    <c:if test="${requestScope.sort != 'byAlphabet'}">
                        <a href="?page=1&sort=byAlphabet">
                            <fmt:message key="product.name.label"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAlphabet'}">
                        <a href="?page=1&sort=byAlphabetReverse">
                            <fmt:message key="product.name.label"/>
                        </a>
                    </c:if>
                </th>
                <th>
                    <c:if test="${requestScope.sort != 'fromCheep'}">
                        <a href="?page=1&sort=fromCheep">
                            <fmt:message key="product.price"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'fromCheep'}">
                        <a href="?page=1&sort=fromExpensive">
                            <fmt:message key="product.price"/>
                        </a>
                    </c:if>
                </th>
                <th>
                    <c:if test="${requestScope.sort != 'byAmount'}">
                        <a href="?page=1&sort=byAmount">
                            <fmt:message key="prouct.amount"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAmount'}">
                        <a href="?page=1&sort=byAmountReverse">
                            <fmt:message key="prouct.amount"/>
                        </a>
                    </c:if>
                </th>

                <th>
                    <c:if test="${requestScope.sort != 'byAmount'}">
                        <a href="?page=1&sort=byAmount">
                            <fmt:message key="prouct.cost"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAmount'}">
                        <a href="?page=1&sort=byAmountReverse">
                            <fmt:message key="prouct.cost"/>
                        </a>
                    </c:if>
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${requestScope.receipt.productsInReceipt.entrySet()}" var="product">
                <tr>
                    <td>${product.getKey().id}</td>
                    <td>${product.getKey().name}</td>
                    <td>
                        $${product.getKey().price}
                        <c:if test="${product.getKey().byWeight}">
                            <fmt:message key="product.perKg"/>
                        </c:if>
                        <c:if test="${!product.getKey().byWeight}">
                            <fmt:message key="product.perItem"/>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${product.getKey().byWeight == true}">
                            ${product.getValue()}
                            <fmt:message key="product.kg"/>
                        </c:if>
                        <c:if test="${product.getKey().byWeight == false}">
                            ${product.getValue()}
                            <fmt:message key="product.item"/>
                        </c:if>
                    </td>
                    <td>${convertor.convertToUSD(product.getValue()*product.getKey().price)}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/receipt/product/delete?productId=${product.getKey().id}&receiptId=${requestScope.receipt.id}">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
<div>

</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>


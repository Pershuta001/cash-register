<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>
<jsp:useBean id="convertor"
             class="com.example.cash_register.utils.Convertor"/>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="receipt.id"/>: ${requestScope.receipt.id}</h1>
<h2><fmt:message key="cashier.id"/>: ${requestScope.receipt.cashierId}</h2>
<h2><fmt:message key="receipt.date"/>: ${requestScope.receipt.date}</h2>
<h2><fmt:message key="total.price"/>:
    <my:price value="${requestScope.receipt.getTotalPrice()}" locale="${sessionScope.lang}"/>
</h2>
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
                            <fmt:message key="product.name"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAlphabet'}">
                        <a href="?page=1&sort=byAlphabetReverse">
                            <fmt:message key="product.name"/>
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
                            <fmt:message key="product.amount"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAmount'}">
                        <a href="?page=1&sort=byAmountReverse">
                            <fmt:message key="product.amount"/>
                        </a>
                    </c:if>
                </th>

                <th>
                    <c:if test="${requestScope.sort != 'byAmount'}">
                        <a href="?page=1&sort=byAmount">
                            <fmt:message key="product.amount"/>
                        </a>
                    </c:if>
                    <c:if test="${requestScope.sort == 'byAmount'}">
                        <a href="?page=1&sort=byAmountReverse">
                            <fmt:message key="product.amount"/>
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
                        <my:price value=" ${product.getKey().price}"
                                  locale="${sessionScope.lang}"/>
                        <c:if test="${product.getKey().byWeight}">
                            <fmt:message key="product.perKg"/>
                        </c:if>
                        <c:if test="${!product.getKey().byWeight}">
                            <fmt:message key="product.perItem"/>
                        </c:if>
                    </td>
                    <td>
                            ${convertor.amountFormat(product.getValue(),product.getKey().byWeight)}
                        <c:if test="${product.getKey().byWeight == true}">
                            <fmt:message key="product.kg"/>
                        </c:if>
                        <c:if test="${product.getKey().byWeight == false}">
                            <fmt:message key="product.item"/>
                        </c:if>
                    </td>
                    <td>
                        <my:price value=" ${product.getValue()*product.getKey().price}"
                                  locale="${sessionScope.lang}"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/receipt/product/delete?productId=${product.getKey().id}&receiptId=${requestScope.receipt.id}">
                            <fmt:message key ="receipt.delete"/>
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


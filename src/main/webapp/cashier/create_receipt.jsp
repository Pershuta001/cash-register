<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>
<jsp:useBean id="convertor"
             class="com.example.cash_register.utils.CurrencyConvertor"/>
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
                  action="${pageContext.request.contextPath}/app/receipt/add/product?receiptId=${requestScope.receipt.id}">
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
                <c:if test="${not empty requestScope.nameError}">
                    <div style="color: red">
                            ${requestScope.nameError}
                    </div>
                </c:if>

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
                <c:if test="${not empty requestScope.amountError}">
                    <div style="color: red">
                            ${requestScope.amountError}
                    </div>
                </c:if>
                <div>
                    <button type="submit">
                        <fmt:message key="cashier.add"/>
                    </button>
                </div>
            </form>
            <table cellspacing="2" border="1" cellpadding="5" width="600" id="table">
                <thead>
                <tr>
                    <th><fmt:message key="product.id"/></th>
                    <th><fmt:message key="product.name.label"/></th>
                    <th><fmt:message key="product.price"/></th>
                    <th><fmt:message key="product.amount.label"/></th>
                    <th><fmt:message key="product.cost"/></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${requestScope.receipt.productsInReceipt.entrySet()}" var="product">
                    <tr>
                        <td>${product.getKey().id}</td>
                        <td>${product.getKey().name}</td>
                        <td>
                                ${convertor.convertToUSD(product.getKey().price)}
                            <c:if test="${product.getKey().byWeight}">
                                <fmt:message key="product.perKg"/>
                            </c:if>
                            <c:if test="${!product.getKey().byWeight}">
                                <fmt:message key="product.perItem"/>
                            </c:if>
                        </td>
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/app/receipt/update/product?receiptId=${requestScope.receipt.id}&productId=${product.getKey().id}&oldAmount=${product.getValue()}">
                                <input type="text"
                                       name="amount"
                                        <c:if test="${product.getKey().byWeight == true}">
                                            pattern="[0-9]{1,5}[\.]?[0-9]{0,3}"

                                        </c:if>
                                        <c:if test="${product.getKey().byWeight == false}">
                                            pattern="[0-9]{1,5}"
                                        </c:if>
                                       placeholder="${convertor.amountFormat(product.getValue())}"
                                       required
                                />
                                <button type="submit">
                                    <fmt:message key="update"/>
                                </button>
                            </form>
                        </td>

                        <td>${convertor.convertToUSD(product.getValue()*product.getKey().price)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="col-2">
                    <form method="post"
                          action="${pageContext.request.contextPath}/app/receipt/confirm?receiptId=${requestScope.receipt.id}">
                        <button type="submit">
                            <fmt:message key="receipt.close"/>
                        </button>
                    </form>
                </div>
                <div class="col">
                    <b>
                        <fmt:message key="total.price"/>
                            ${requestScope.receipt.getTotalPrice()}
                    </b>

                </div>
            </div>

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

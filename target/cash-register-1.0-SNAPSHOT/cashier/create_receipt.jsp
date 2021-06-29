<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
%>
<jsp:useBean id="convertor"
             class="com.example.cash_register.utils.Convertor"/>
<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="product.products"/></h1>

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
                        <fmt:message key="product.name.id"/>
                    </label>
                    <input type="text"
                           id="name"
                           name="name"
                           pattern="[a-zA-Zа-яА-Я0-9]{1,30}"
                    <c:if test="${not empty requestScope.name}">
                           value="${requestScope.name}"
                    </c:if>
                    <c:if test="${not empty requestScope.nameError}">
                           style="color: red"
                    </c:if>
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
                        <fmt:message key="product.amount"/>
                    </label>
                    <input type="text"
                           id="amount"
                           name="amount"
                           pattern="^([\d]{1,5}([\.][\d]{1,3})?)$"
                    <c:if test="${not empty requestScope.amount}">
                           value="${requestScope.amount}"
                    </c:if>
                    <c:if test="${not empty requestScope.amountError}">
                           style="color: red"
                    </c:if>
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
            <table cellspacing="2" border="1" cellpadding="5" width="900" id="table">
                <thead>
                <tr>
                    <th><fmt:message key="product.id"/></th>
                    <th><fmt:message key="product.name"/></th>
                    <th><fmt:message key="product.price"/></th>
                    <th><fmt:message key="product.amount"/></th>
                    <th><fmt:message key="product.price"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.receipt.productsInReceipt.entrySet()}" var="product">
                    <tr>
                        <td>${product.getKey().id}</td>
                        <td>${product.getKey().name}</td>
                        <td>
                            <my:price value="${product.getKey().price}" locale="${sessionScope.lang}"/>
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
                                       placeholder="${convertor.amountFormat(product.getValue(),product.getKey().byWeight)}"
                                       required
                                />
                                <button type="submit">
                                    <fmt:message key="update"/>
                                </button>
                            </form>
                        </td>

                        <td>
                            <my:price value="${product.getValue()*product.getKey().price}"
                                      locale="${sessionScope.lang}"/>
                        </td>
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
                        <my:price value="${requestScope.receipt.getTotalPrice()}"
                                  locale="${sessionScope.lang}"/>
                    </b>
                </div>
            </div>
        </c:if>
    </div>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

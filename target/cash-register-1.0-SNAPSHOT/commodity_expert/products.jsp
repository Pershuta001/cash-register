<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="java.util.List" %>
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
<%@include file="/WEB-INF/jspf/header.jspf" %>
<jsp:useBean id="convertor"
             class="com.example.cash_register.utils.Convertor"/>

<h1><fmt:message key="product.products"/></h1>
<div>
    <div>

        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
        %>

        <table cellspacing="2" border="1" cellpadding="5" width="800" id="table">
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
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${requestScope.products}" var="product">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>
                        <my:price value="${product.price}" locale="${sessionScope.lang}"/>
                        <c:if test="${product.byWeight}">
                            <fmt:message key="product.perKg"/>
                        </c:if>
                        <c:if test="${!product.byWeight}">
                            <fmt:message key="product.perItem"/>
                        </c:if>
                    </td>
                    <td>
                        <form method="post"
                              action="${pageContext.request.contextPath}/app/product/update?id=${product.id}&page=${requestScope.page}&pagesCount=${requestScope.pagesCount}&sort=${requestScope.sort}">
                            <input type="text"
                                   name="amount"
                                    <c:if test="${product.byWeight == true}">
                                        pattern="[0-9]{1,5}[\.]?[0-9]{0,3}"
                                        placeholder="${convertor.amountFormat(product.availableWeight, true)}"
                                    </c:if>
                                    <c:if test="${product.byWeight == false}">
                                        pattern="[0-9]{1,5}"
                                        placeholder="${convertor.amountFormat(product.availableQuantity, false)}"
                                    </c:if>
                                   required
                            />
                            <button type="submit">
                                <fmt:message key="update"/>
                            </button>
                        </form>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/product/delete?page=${requestScope.page}&sort=${requestScope.sort}&id=${product.id}">
                            <fmt:message key="receipt.delete"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
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

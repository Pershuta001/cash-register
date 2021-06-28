<%@ page import="com.example.cash_register.model.entity.Product" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="product.create"/></h1>
<form method="post" action="${pageContext.request.contextPath}/app/product/create">
    <div
    >
        <label for="name">
            <fmt:message key="product.name"/>
        </label>
        <input type="text"
               id="name"
               name="name"
               value="${requestScope.name}"
               required
        <c:if test="${not empty requestScope.error}">
               style="color: red"
        </c:if>
        >
    </div>
    <div>
        <label for="price">
            <fmt:message key="product.price"/>(USD)
        </label>
        <input type="text"
               id="price"
               name="price"
               value="${requestScope.price}"
               pattern="[0-9]{1,5}[\.]?[0-9]{0,2}"
               required
        >

        <select name="byWeight" id="byWeight" onchange="changePattern()">
            <option value="/kg"
                    <c:if test="${requestScope.byWeight == '/kg'}">
                        selected
                    </c:if>
            >
                <fmt:message key="product.perKg"/>
            </option>
            <option value="/item"
                    <c:if test="${requestScope.byWeight == '/item'}">
                        selected
                    </c:if>
            >
                <fmt:message key="product.perItem"/>
            </option>
        </select>

    </div>
    <div>
        <label for="amount">
            <fmt:message key="product.amount"/>
        </label>

        <input type="text"
               id="amount"
               name="amount"
               pattern="[0-9]{1,5}[\.]?[0-9]{1,5}"
               value="${requestScope.amount}"
               required
        >
    </div>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="product.create"/>
    </button>
</form>

<div class="error" style="color: red">
    <c:if test="${not empty requestScope.error}">
        <label>
                ${requestScope.error}
        </label>
    </c:if>
</div>

<div class="success" style="color: green">
    <c:if test="${not empty requestScope.success}">
        <label>
                ${requestScope.success}
        </label>
    </c:if>
</div>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>

<script>
    function changePattern() {
        //  d = document.getElementById("select_id").value;
        let select = document.getElementById("byWeight");
        let pattern = select.value === '/kg' ? '[0-9]{1,5}[\.]?[0-9]{1,3}' : '[0-9]{1,5}';
        document.getElementById("amount").setAttribute('pattern', pattern);
    }
</script>
</html>

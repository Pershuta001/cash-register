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

<h1><fmt:message key="page.product.update.label"/></h1>
<form method="post" action="${pageContext.request.contextPath}/app/product/update?id=${requestScope.product.id}">
    <div class="form-group">
        <label for="amount">
            <fmt:message key="product.amount.label"/>
        </label>
        <input type="text"
               id="amount"
               name="amount"
            <%
           Product product = (Product)request.getAttribute("product");
           if(product.isByWeight()){
               out.println("pattern=\"[0-9]{1,5}[\\.]?[0-9]{1,5}\"");
               out.println("placeholder=\""+product.getAvailableWeight()+"\"");
           }
           else{
               out.println("pattern=\"[0-9]{1,5}\"");
               out.println("placeholder=\""+product.getAvailableQuantity()+"\"");
           }
           %>
               required
        >
    </div>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="page.product.update.label"/>
    </button>
</form>
<form method="get" action="${pageContext.request.contextPath}/app/product/all?page=1">
    <button type="submit">
        <fmt:message key="back.label"/>
    </button>
</form>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

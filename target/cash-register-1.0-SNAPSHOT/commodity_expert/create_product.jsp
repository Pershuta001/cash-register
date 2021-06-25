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

<h1><fmt:message key="page.product.create.label"/></h1>
<form method="post" action="${pageContext.request.contextPath}/app/product/create">
    <div class="form-group">
        <label for="name">
            <fmt:message key="product.name.label"/>
        </label>
        <input type="text"
               id="name"
               name="name"
               required
        >
    </div>
    <div class="form-group">
        <label for="price">
            <fmt:message key="product.price"/>(USD)
        </label>
        <input type="text"
               id="price"
               name="price"
               pattern="[0-9]{1,5}[\.]?[0-9]{0,2}"
               required
        >
        <select id="byWeight" name="byWeight">
            <option>
                /kg
            </option>
            <option>
                /item
            </option>
        </select>
    </div>
    <div class="form-group">
        <label for="amount">
            <fmt:message key="product.amount.label"/>
        </label>
        <input type="text"
               id="amount"
               name="amount"
               pattern="[0-9]{1,5}[\.]?[0-9]{1,5}"
               required
        >
    </div>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="product.create.button"/>
    </button>
</form>
<%
    try {
        Product product = (Product) request.getAttribute("crProductId");
        String lang = session.getAttribute("lang") == null ? "en" : (String) session.getAttribute("lang");
        Locale locale = new Locale(lang);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resources", locale);
        if (product != null) {

%>
<script type="text/javascript">
    let myMessage;
    <%
        out.println("myMessage = \""+resourceBundle.getString("productAlert")+' '+
        product.getName()+ ' '+ resourceBundle.getString("productAlert2") + ' ' + product.getId()
         +"\"");
    %>
    alert(myMessage);
</script>
<%
        }
    } catch (Exception e) {
        out.println(e.getMessage());
    }
%>
<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

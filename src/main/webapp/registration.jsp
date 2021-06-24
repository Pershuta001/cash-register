<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">

<%@include file="WEB-INF/jspf/head.jspf" %>

<body>

<%@include file="WEB-INF/jspf/header.jspf" %>

<h1><fmt:message key="page.register.header"/></h1>
<form method="post" action="${pageContext.request.contextPath}/app/registration">
    <div class="form-group">
        <label for="exampleInputEmail1"><fmt:message key="page.register.name"/></label>
        <input type="text"
               class="form-control"
               required
               name="name"
        >
    </div>
    <div class="form-group">
        <label for="exampleInputEmail1"><fmt:message key="page.register.surname"/></label>
        <input type="text"
               class="form-control"
               required
               name="surname"
        >
    </div>
    <div class="form-group">
        <label for="exampleInputEmail1"><fmt:message key="page.login.label"/></label>
        <input type="email"
               class="form-control"
               id="exampleInputEmail1"
               required
               name="login"
        >
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1"><fmt:message key="page.password.label"/></label>
        <input type="password"
               class="form-control"
               id="exampleInputPassword1"
               required
               name="password"
        >
    </div>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="page.register.button"/>
    </button>
</form>

<jsp:include page="WEB-INF/jspf/footer.jspf"/>

</body>
</html>
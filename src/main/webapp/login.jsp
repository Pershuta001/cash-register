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

<h1><fmt:message key="login"/></h1>
<form method="post" action="${pageContext.request.contextPath}/app/login">
    <div class="form-group">
        <label for="exampleInputEmail1"><fmt:message key="login.label"/></label>
        <input type="email"
               class="form-control"
               id="exampleInputEmail1"
               required
               name="login"
        >
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1"><fmt:message key="login.password"/></label>
        <input type="password"
               class="form-control"
               id="exampleInputPassword1"
               required
               name="password"
        >
    </div>
    <c:if test="${not empty requestScope.exception}">
        <div>
            <label style="color:red;">
                    ${requestScope.exception}
            </label>
        </div>
    </c:if>
    <button type="submit" class="btn btn-primary">
        <fmt:message key="login"/>
    </button>
</form>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>

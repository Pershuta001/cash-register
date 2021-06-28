<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglibs.jspf" %>
<%@ page isErrorPage="true" import="java.io.*" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Exceptional Even Occurred!</title>
</head>
<body>
<h1>Opps...</h1>
<h2>
    ${requestScope.exception}
</h2>

<a href="${pageContext.request.contextPath}/">
    <fmt:message key="back"/>
</a>
</body>
</html>

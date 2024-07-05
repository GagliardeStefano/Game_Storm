<%@ page import="Controller.SessionManager" %>
<!-- TAGLIB -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set scope="application" value="${pageContext.request.contextPath}" var="context" />

<!-- META -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS -->
<link rel="stylesheet" href="${context}/css/main.css">
<link rel="stylesheet" type="text/css" href="${context}/css/navbar.css" />
<link rel="stylesheet"  type="text/css" href="${context}/css/footer.css" />

<link rel="icon" type="image/x-icon" href="${context}/images/favicon.png" />

<!-- JAVA -->
<% SessionManager sm = new SessionManager(request, false);
    if (sm.getSession() != null && sm.getAttribute("utente") != null){ %>
        <jsp:useBean id="utente"  scope="session" type="Model.User" />
        <jsp:useBean id="wishlist"  scope="session" type="java.util.List" />
        <jsp:useBean id="ordini" scope="session" type="java.util.Map" />
    <%}%>
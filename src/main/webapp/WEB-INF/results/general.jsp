<%@ page import="Controller.SessionManager" %>
<%@ page import="Model.User" %>
<%@ page import="Model.Enum.TipoUtente" %>
<!-- TAGLIB -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
<%  SessionManager sm = new SessionManager(request, false);
    if (sm.getSession() != null && sm.getAttribute("user") != null && ((User) sm.getAttribute("user")).getTipo() == TipoUtente.Semplice){%>

            <jsp:useBean id="user"  scope="session" type="Model.User" />
            <jsp:useBean id="wishlist"  scope="session" type="java.util.List" />
            <jsp:useBean id="ordini" scope="session" type="java.util.Map" />
            <jsp:useBean id="carte" scope="session" type="java.util.List" />
            <jsp:useBean id="carrello" scope="session" type="Model.Carrello" />
<%  }  %>

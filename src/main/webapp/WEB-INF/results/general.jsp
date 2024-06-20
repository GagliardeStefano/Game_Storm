<!-- IMPORT -->
<%@ page import="Controller.SessionManager" %>
<%@ page import="Model.User" %>

<!-- TAGLIB -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set scope="application" value="${pageContext.request.contextPath}" var="context" />

<!-- META -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS -->
<link rel="stylesheet" href="${context}/css/main.css">
<link rel="stylesheet" type="text/css" href="${context}/css/styleNavbar.css" />
<link rel="stylesheet"  type="text/css" href="${context}/css/footer.css" />

<!-- JAVA -->
<%  SessionManager sessionManager = new SessionManager(request, false);
    User userS = (User) sessionManager.getAttribute("utente");
%>

<script>
    let context = '';

    function getPageContext()
    {
        if(context === '')
            context = window.location.origin + window.location.pathname.substring(0, window.location.pathname.indexOf('/',1));

        return context;
    }
</script>
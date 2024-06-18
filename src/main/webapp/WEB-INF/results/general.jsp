<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set scope="application" value="${pageContext.request.contextPath}" var="context" />

<script>
    let context = '';

    function getPageContext()
    {
        if(context === '')
            context = window.location.origin + window.location.pathname.substring(0, window.location.pathname.indexOf('/',1));

        return context;
    }
</script>
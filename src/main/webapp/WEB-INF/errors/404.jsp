
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <%@ include file="../results/general.jsp"%>
    <link rel="stylesheet" type="text/css" href="${context}/css/error.css">
    <title>GS-404</title>
</head>
<body>
    <%@ include file="../fragments/navbar.jsp"%>

    <div class="container-error">
        <h1>404 - Pagina non trovata</h1>
        <p>La pagina che stai cercando non esiste.</p>
        <p><a href="${context}/index.jsp">Torna alla Home</a></p>
    </div>

    <%@ include file="../fragments/footer.jsp"%>

    <%
        System.out.println("Tipo di errore: "+request.getAttribute("jakarta.servlet.error.exception_type"));
        System.out.println("Messaggio dell'errore: "+request.getAttribute("jakarta.servlet.error.message"));
        System.out.println("Codice di stato: "+request.getAttribute("jakarta.servlet.error.status_code"));
        System.out.println("Exception: "+request.getAttribute("jakarta.servlet.error.exception"));
        System.out.println("URL della richiesta: "+request.getAttribute("jakarta.servlet.error.request_uri"));
    %>
</body>
</html>

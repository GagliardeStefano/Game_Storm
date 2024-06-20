<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS - my account</title>
</head>
<body>
    <%@ include file="../fragments/header.jsp"%>

    <%  if(userS != null){ %>
        <p><%=userS.getCognome()%></p>
        <p><%=userS.getNome()%></p>
        <p><%=userS.getData()%></p>
        <p><%=userS.getPaese()%></p>
        <p><%=userS.getEmail()%></p>
        <p><%=userS.getPassword()%></p>
        <p><%=userS.getPasswordHash()%></p>
    <%  } %>
</body>
</html>

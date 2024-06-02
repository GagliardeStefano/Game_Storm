<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Icone -->
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.2.0/fonts/remixicon.css" rel="stylesheet" />

    <!-- Stili css -->
    <link rel="stylesheet" type="text/css" href="./css/main.css" />
    <link rel="stylesheet" type="text/css" href="./css/styleNavbar.css" />
    <link rel="stylesheet"  type="text/css" href="./css/footer.css" />

</head>
    <body>

        <%@include file="WEB-INF/fragments/header.jsp"%>

        <main style="margin: 500px;" >

        </main>

        <%@ include file="WEB-INF/fragments/footer.jsp"%>
        <script src="./js/navbar.js"></script>
    </body>
</html>
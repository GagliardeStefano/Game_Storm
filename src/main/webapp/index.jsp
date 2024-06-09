<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game Storm</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Icone -->
    <link href="https://cdn.jsdelivr.net/npm/remixicon@4.2.0/fonts/remixicon.css" rel="stylesheet" />

    <!-- Stili css -->
    <link rel="stylesheet" type="text/css" href="./css/main.css" />
    <link rel="stylesheet" type="text/css" href="./css/styleNavbar.css" />
    <link rel="stylesheet" type="text/css" href="./css/styleCarousel.css" />
    <link rel="stylesheet"  type="text/css" href="./css/footer.css" />

</head>
    <body>
        <!-- NAVBAR -->
        <%@include file="WEB-INF/fragments/header.jsp"%>

        <main style="margin-bottom: 500px;" >
            <!-- CAROSELLO -->
            <section class="carousel">
                <!--CARD-->
                <div class="card expanded">
                    <div class="card-content">
                        <a href="#" class="disabled"><img src="./images/giochi/GTA6.jpg" alt="img1"></a>
                        <div class="info">
                            <h3 class="title">GTA 6</h3>
                            <p>0.00€</p>
                        </div>
                    </div>
                </div>
                <!--FINE CARD-->
                <div class="card">
                    <div class="card-content">
                        <a href="#" class="disabled"><img src="./images/giochi/GTA6.jpg" alt="img1"></a>
                        <div class="info">
                            <h3 class="title">GTA 6</h3>
                            <p>0.00€</p>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-content">
                        <a href="#" class="disabled"><img src="./images/giochi/GTA6.jpg" alt="img1"></a>
                        <div class="info">
                            <h3 class="title">GTA 6</h3>
                            <p>0.00€</p>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-content">
                        <a href="#" class="disabled"><img src="./images/giochi/GTA6.jpg" alt="img1"></a>
                        <div class="info">
                            <h3 class="title">GTA 6</h3>
                            <p>0.00€</p>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-content">
                        <a href="#" class="disabled"><img src="./images/giochi/GTA6.jpg" alt="img1"></a>
                        <div class="info">
                            <h3 class="title">GTA 6</h3>
                            <p>0.00€</p>
                        </div>
                    </div>
                </div>
            </section>
        </main>

        <%@ include file="WEB-INF/fragments/footer.jsp"%>

        <!-- SCRIPT -->
        <script src="./js/navbar.js"></script>
        <script src="./js/carousel.js"></script>
    </body>
</html>
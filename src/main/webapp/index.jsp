<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game Storm</title>

    <%@ include file="WEB-INF/results/general.jsp"%>
    
    <!-- Stili css -->
    <link rel="stylesheet" type="text/css" href="${context}/css/carousel.css" />
    <link rel="stylesheet" type="text/css" href="${context}/css/sezioni.css" />
</head>
    <body>
        <!-- NAVBAR -->
        <%@ include file="WEB-INF/fragments/header.jsp"%>

        <!-- CAROSELLO -->
        <section class="carousel">
            <!--CARD-->
            <div class="card expanded">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>
            <!--FINE CARD-->
            <div class="card">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-content">
                    <a href="#" class="disabled"><img src="${context}/images/giochi/GTA6.jpg" alt="img1"></a>
                    <div class="info">
                        <h3 class="title">GTA 6</h3>
                        <p>0.00€</p>
                    </div>
                </div>
            </div>
        </section>

        <section class="sezione trending">
            <div class="titolo trending">
                <a><h2>In tendenza</h2></a>
            </div>
            <div class="card-container trending">
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
            </div>
        </section>
        <section class="sezione preorder">
            <div class="titolo preorder">
                <a><h2>Preordini</h2></a>
            </div>
            <div class="card-container preorder">
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
            </div>
        </section>
        <section class="sezione arrival">
            <div class="titolo arrival">
                <a><h2>Prossime Uscite</h2></a>
            </div>
            <div class="card-container arrival">
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
                <div class="card">
                    <img class="card__image" src="images/giochi/GTA6.jpg" alt="">
                </div>
            </div>
        </section>

        <%@ include file="WEB-INF/fragments/footer.jsp"%>
        <!-- SCRIPT -->
        <script src="${context}/js/carousel.js"></script>
        <script>
            if (window.innerWidth < 430) {
                const script = document.createElement('script');
                script.src = './js/smallScreenCarousel.js';
                document.head.appendChild(script);
            }
        </script>
    </body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game Storm</title>

    <%@ include file="general.jsp"%>

    <!-- Stili css -->
    <link rel="stylesheet" type="text/css" href="${context}/css/carousel.css" />
    <link rel="stylesheet" type="text/css" href="${context}/css/sezioni.css" />
    <link rel="stylesheet" type="text/css" href="${context}/css/carrello.css" />

</head>
<body>
<!-- NAVBAR -->
<%@include file="../fragments/header.jsp"%>
<div class="main-page" id="main-page">
    <div class="cart-page">
        <div class="cart">
            <h1>Carrello</h1>
            <div class="cart-item">
                <div class="img-item">
                    <img src="${context}/images/giochi/GTA6.jpg">
                </div>
                <div class="side-cart">
                    <div class="title-item">
                        <h1>GTA 6</h1>
                    </div>
                    <div class="data">
                        2024-12-12
                    </div>
                    <div class="action-cart">
                        <div class="cestino">
                            <i id="cestino1" class="ri-delete-bin-5-line"></i>
                        </div>
                        <div class="favourite">
                            <i id="heart1" class="ri-heart-fill fill" ></i>
                        </div>
                    </div>

                </div>

                <div class="prezzo">
                    <div class="prezzo-scritta">Prezzo:</div>
                    <div class="prezzo-n">50.00€</div>
                </div>

            </div>
            <div class="cart-item">
                <div class="img-item">
                    <img src="${context}/images/giochi/DarkSouls3.jpg">
                </div>
                <div class="side-cart">
                    <div class="title-item">
                        <h1>GTA 6</h1>
                    </div>
                    <div class="data">
                        2024-12-12
                    </div>
                    <div class="action-cart">
                        <div class="cestino">
                            <i id="cestino2" class="ri-delete-bin-5-line"></i>
                        </div>
                        <div class="favourite">
                            <i id="heart2" class="ri-heart-fill fill" ></i>
                        </div>
                    </div>
            </div>
                <div class="prezzo">
                    <div class="prezzo-scritta">Prezzo:</div>
                    <div class="prezzo-n">50.00€</div>
                </div>
        </div>
    </div>
        <div class="fake-riepilogo">
            <div class="riepilogo">
                <h1>Riepilogo</h1>
                <div class="prezzo-ufficiale">
                    <p>prezzo ufficiale</p>
                    <p>100.00€</p>
                </div>
                <div class="sconto">
                    <p>sconto</p>
                    <p>20%</p>
                </div>
                <div class="prezzo-finale">
                    <p>totale</p>
                    <p>80.00€</p>
                </div>
                <div class="pagamento-button">Vai al pagamento ></div>
            </div>
        </div>

</div>

<%@ include file="../fragments/footer.jsp"%>
<!-- SCRIPT -->
<script src="${context}/js/navbar.js"></script>
<script src="${context}/js/carousel.js"></script>
<script src="${context}/js/sezioni.js"></script>

</body>
</html>
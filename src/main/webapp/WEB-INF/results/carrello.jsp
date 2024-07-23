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
    <%@include file="../fragments/navbar.jsp"%>
    <div class="main-page" id="main-page">
        <div class="cart-page">
            <div class="cart">
                <h1>Carrello</h1>
                <c:if test="${empty carrello.prodotti }">
                    <c:set var="vuoto" value="0" />
                    <div class="empty-cart-message">
                        <h2>Il tuo carrello è vuoto</h2>
                        <p>Aggiungi dei prodotti per iniziare a fare shopping!</p>
                    </div>
                </c:if>
                <c:forEach items="${carrello.prodotti}" var="gioco">
                    <div class="cart-item">
                        <div class="img-item">
                            <a href="${context}/CardManager?id=${gioco.prodotto.id}">
                                <img src="${context}${gioco.prodotto.img}"  alt="${gioco.prodotto.nome}">
                            </a>
                        </div>
                        <div class="side-cart">
                            <div class="title-item">
                                <h1>${gioco.prodotto.nome}</h1>
                            </div>
                            <div class="data">
                                ${gioco.prodotto.dataRilascio}
                            </div>
                            <div class="action-cart">
                                <div role="button" tabindex="0" class="cestino" onclick="removeCartItem(this,${gioco.prodotto.id})">
                                    <i id="cestino1" class="ri-delete-bin-5-line"></i>
                                </div>
                                <c:choose>
                                    <c:when test="${empty user}">
                                        <div class="favourite tooltip">
                                            <i id="mex-heart" class="ri-chat-heart-line"></i>
                                            <span class="tooltiptext" >Accedi per aggiungerlo ai preferiti</span>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="isFavorite" value="false" />
                                        <c:forEach items="${wishlist}" var="preferito">
                                            <c:if test="${preferito.id == gioco.prodotto.id}">
                                                <c:set var="isFavorite" value="true" />
                                            </c:if>
                                        </c:forEach>
                                        <div class="favourite" role="button" tabindex="0" id="${gioco.prodotto.id}" onclick="updateFavourite(${gioco.prodotto.id})">
                                            <c:choose>
                                                <c:when test="${isFavorite}">
                                                    <i id="heart" class="ri-heart-fill fill" ></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i id="heart" class="ri-heart-line"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="prezzo">
                            <div class="prezzo-scritta">Prezzo:</div>
                            <div class="prezzo-n"><fmt:formatNumber value="${gioco.prodotto.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="fake-riepilogo">
                <div class="riepilogo">
                    <h1>Riepilogo</h1>
                    <div class="prezzo-ufficiale">
                        <p>prezzo ufficiale</p>
                        <p><fmt:formatNumber value="${carrello.totale}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                    </div>
                    <div class="sconto">
                        <p>sconto</p>
                        <p><fmt:formatNumber value="${carrello.scontoTotale}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                    </div>
                    <div class="prezzo-finale">
                        <p>totale</p>
                        <p><fmt:formatNumber value="${carrello.prezzoScontatoTotale}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                    </div>
                    <c:choose>
                        <c:when test="${vuoto == 0}">
                            <div class="pagamento-button">Vai al pagamento ></div>
                        </c:when>
                        <c:when test="${empty user}">
                            <a href="${context}/UserManager">
                                <div class="pagamento-button">Vai al pagamento ></div>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${context}/CartManager?action=pagamento">
                                <div class="pagamento-button">Vai al pagamento ></div>
                            </a>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </div>

    <%@ include file="../fragments/footer.jsp"%>
    <!-- SCRIPT -->
    <script src="${context}/js/navbar.js"></script>
    <script src="${context}/js/carousel.js"></script>
    <script src="${context}/js/sezioni.js"></script>
    <script src="${context}/js/game.js"></script>
    <script src="${context}/js/carrello.js"></script>

</body>
</html>
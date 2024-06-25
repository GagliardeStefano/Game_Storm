<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS - my account</title>
    <link rel="stylesheet" href="${context}/css/account.css">
</head>
<body>
    <%@ include file="../fragments/header.jsp"%>


    <c:if test="${empty utente}">
        <% response.sendRedirect("index.jsp"); %>
    </c:if>


    <div class="container-utente">
        <div class="container-info-utente">
            <div class="container-img-utente"> <img class="img-utente" src="${context}/images/socialLogo/gitlab.png" alt="foto" /> </div>
            <div class="dati-main-utente">
                <div class="container-anagrafici">
                    <p class="cognome-utente">${utente.cognome}</p>
                    <p class="nome-utente">${utente.nome}</p>
                </div>

                <div class="container-paese">
                    <p>${utente.paese}</p>
                </div>
            </div>
        </div>
        <div class="container-option">
            <ul class="options">
                <li class="option active" id="scelta-wishlist"><a href="#">Wishlist</a></li>
                <li class="option" id="scelta-ordini"><a href="#">Ordini effettuati</a></li>
                <li class="option" id="scelta-pagamento"><a href="#">Metodi di pagamento</a></li>
                <li class="option" id="scelta-modifica"><a href="#">Modifica dati</a></li>
            </ul>
        </div>
        <div class="container-choice">
            <!-- WISHLIST -->
            <div id="wishlist" class="container-wishlist" style="display: none">
                <div class="actions">
                    <p id="delete-all" class="delete-all">Elimina tutti</p>
                    <p id="add-all" class="add-all">Aggiungi tutti al carrello</p>
                </div>

                <div class="container-preferiti">
                    <div class="container-card">
                        <div class="container-info-card">
                            <div class="cont-img-card">
                                <img src="${context}/images/giochi/GTA6.jpg" alt="img gioco">
                            </div>
                            <div class="info-card">
                                <p class="nome">GTA 6</p>
                                <p class="prezzo">50.00€</p>
                            </div>
                        </div>
                        <div class="actions-card">
                            <a><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                            <a><i title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
                        </div>
                    </div>

                    <div class="container-card">
                        <div class="container-info-card">
                            <div class="cont-img-card">
                                <img src="${context}/images/giochi/GTA6.jpg" alt="img gioco">
                            </div>
                            <div class="info-card">
                                <p class="nome">GTA 6</p>
                                <p class="prezzo">50.00€</p>
                            </div>
                        </div>
                        <div class="actions-card">
                            <a><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                            <a><i title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
                        </div>
                    </div>


                    <div class="container-card">
                        <div class="container-info-card">
                            <div class="cont-img-card">
                                <img src="${context}/images/giochi/GTA6.jpg" alt="img gioco">
                            </div>
                            <div class="info-card">
                                <p class="nome">GTA 6</p>
                                <p class="prezzo">50.00€</p>
                            </div>
                        </div>
                        <div class="actions-card">
                            <a><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                            <a><i title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
                        </div>
                    </div>


                    <div class="container-card">
                        <div class="container-info-card">
                            <div class="cont-img-card">
                                <img src="${context}/images/giochi/GTA6.jpg" alt="img gioco">
                            </div>
                            <div class="info-card">
                                <p class="nome">GTA 6</p>
                                <p class="prezzo">50.00€</p>
                            </div>
                        </div>
                        <div class="actions-card">
                            <a><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                            <a><i title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ORDINI -->
            <div id="ordini-effettuati" style="display: none"></div>

            <!-- METODI PAGAMENTO -->
            <div id="metodi-pagamento" style="display: none"></div>

            <!-- MODIFICA DATI -->
            <div id="modifica-dati" style="display: none"></div>

        </div>
    </div>

    <%@ include file="../fragments/footer.jsp"%>

    <script src="${context}/js/account.js"></script>

</body>
</html>

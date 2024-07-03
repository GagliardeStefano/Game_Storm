<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS-my account</title>
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
                <li class="option active" id="scelta-wishlist"><a>Wishlist</a></li>
                <li class="option" id="scelta-ordini"><a>Ordini effettuati</a></li>
                <li class="option" id="scelta-pagamento"><a>Metodi di pagamento</a></li>
                <li class="option" id="scelta-modifica"><a>Modifica dati</a></li>
            </ul>
        </div>
        <div class="container-choice">
            <!-- WISHLIST -->
            <div id="wishlist" class="container-wishlist" style="display: none">

                <c:choose>
                    <c:when test="${empty wishlist}">
                        <p>Non hai ancora inserito giochi tra i preferiti</p>
                    </c:when>
                    <c:otherwise>

                        <div class="actions">
                            <p onclick="deleteAllFromDb()" id="delete-all" class="delete-all">Elimina tutti</p>
                            <p id="add-all" class="add-all">Aggiungi tutti al carrello</p>
                        </div>


                        <div class="container-preferiti">
                            <c:forEach var="gioco" items="${wishlist}">
                                <div class="container-card">
                                    <div class="container-info-card">
                                        <div class="cont-img-card">
                                            <img src="${context}${gioco.getImg()}" alt="img gioco">
                                        </div>
                                        <div class="info-card">
                                            <p class="nome">${gioco.getNome()}</p>
                                            <p class="prezzo">${gioco.getPrezzoScontato()}€</p>
                                        </div>
                                    </div>
                                    <div class="actions-card">
                                        <a><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                                        <a><i onclick="deleteFromDb(${gioco.getId()}, this)" title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- ORDINI -->
            <div id="ordini-effettuati" style="display: none">
                <div class="mese">
                    <h2>Mese 1</h2>
                    <div class="container-ordini">
                        <div class="ordine">
                            <div class="container-info">
                                <div class="info-main">
                                    <div class="data">
                                        <h3>Data di acquisto</h3>
                                        <p>23/06/2024</p>
                                    </div>
                                    <div class="totale">
                                        <h3>Totale</h3>
                                        <p>135.00€</p>
                                    </div>
                                </div>
                            </div>

                            <div class="container-games">
                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game more">
                                    <p>+3</p>
                                </div>
                            </div>

                            <div class="container-games hidden">
                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo">45€</p>
                                    </div>
                                </div>
                            </div>
                            <span class="mostra-meno hidden">mostra meno</span>
                        </div>
                        <div class="ordine">
                            <div class="container-info">
                                <div class="info-main">
                                    <div class="data">
                                        <h3>Data di acquisto</h3>
                                        <p>23/06/2024</p>
                                    </div>
                                    <div class="totale">
                                        <h3>Totale</h3>
                                        <p>135.00€</p>
                                    </div>
                                </div>
                            </div>

                            <div class="container-games">
                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <h4 class="titolo">Titolo</h4>
                                        <div class="key hidden">
                                            <p>key: a4d-fdh-1s5</p>
                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                        </div>
                                        <p class="prezzo hidden">45€</p>
                                    </div>
                                </div>

                                <div class="game more">
                                    <p>+</p>
                                </div>
                            </div>

                            <div class="container-games hidden"></div>
                            <span class="mostra-meno hidden">mostra meno</span>
                        </div>

                    </div>
                </div>
                <div class="mese">
                    <h2>Mese 1</h2>
                    <div class="container-ordini">
                        <div class="ordine">
                            <div class="container-info">
                                <div class="info-main">
                                    <div class="data">
                                        <h3>Data di acquisto</h3>
                                        <p>23/06/2024</p>
                                    </div>
                                    <div class="totale">
                                        <h3>Totale</h3>
                                        <p>135.00€</p>
                                    </div>
                                </div>
                            </div>

                            <div class="container-games">
                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game more">
                                    <p>+3</p>
                                </div>
                            </div>
                        </div>
                        <div class="ordine">
                            <div class="container-info">
                                <div class="info-main">
                                    <div class="data">
                                        <h3>Data di acquisto</h3>
                                        <p>23/06/2024</p>
                                    </div>
                                    <div class="totale">
                                        <h3>Totale</h3>
                                        <p>135.00€</p>
                                    </div>
                                </div>
                            </div>

                            <div class="container-games">
                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game">
                                    <img src="${context}/images/giochi/GTA6.jpg" alt="locandina">
                                    <div class="info">
                                        <p>Titolo</p>
                                    </div>
                                </div>

                                <div class="game more">
                                    <p>+</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- METODI PAGAMENTO -->
            <div id="metodi-pagamento" style="display: none"></div>

            <!-- MODIFICA DATI -->
            <div id="modifica-dati" style="display: none"></div>

        </div>
    </div>


    <%@ include file="../fragments/footer.jsp"%>

    <script src="${context}/js/navbar.js"></script>
    <script src="${context}/js/account.js"></script>

</body>
</html>

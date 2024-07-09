<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS-my account</title>
    <link rel="stylesheet" href="${context}/css/account.css">
</head>
<body>


    <c:if test="${empty utente}">
        <% response.sendRedirect("index.jsp"); %>

    </c:if>

    <%@ include file="../fragments/header.jsp"%>

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
                            <p onclick="addAllToCart()" id="add-all" class="add-all">Aggiungi tutti al carrello</p>
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

                <c:choose>
                    <c:when test="${empty ordini}">
                        <p>Non hai ancora effettauto degli ordini</p>
                    </c:when>
                    <c:otherwise>


                        <c:forEach items="${ordini}" var="entry">
                            <div class="mese">
                                <h2>${entry.key}</h2>
                                <div class="container-ordini">
                                    <c:forEach items="${entry.value}" var="ordine">
                                    <div class="ordine">
                                        <div class="container-info">
                                            <div class="info-main">
                                                <div class="data">
                                                    <h3>Data di acquisto</h3>
                                                    <p>${ordine.data}</p>
                                                </div>
                                                <div class="totale">
                                                    <h3>Totale</h3>
                                                    <p>${ordine.totale}€</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="container-games">
                                            <c:set var="totalProdotti" value="${fn:length(ordine.prodotti)}" />

                                            <c:forEach items="${ordine.prodotti}" var="prodottoComposto" begin="0" end="${totalProdotti > 3 ? 2 : totalProdotti - 1}">

                                                <div class="game">
                                                    <a href="${prodottoComposto.prodotto.id}"><img src="${context}${prodottoComposto.prodotto.img}" alt="locandina"></a>
                                                    <div class="info">
                                                        <h4 class="titolo">${prodottoComposto.prodotto.nome}</h4>
                                                        <div class="key hidden">
                                                            <p>key: ${prodottoComposto.key}</p>
                                                            <i class="ri-file-copy-2-line" title="copia"></i>
                                                        </div>
                                                        <p class="prezzo hidden">${prodottoComposto.prezzo}€</p>
                                                    </div>
                                                </div>

                                            </c:forEach>

                                            <div onclick="ShowMoreGames(this)" data-order-id="${ordine.data}" class="game more">
                                                <p title="mostra di più">+</p>
                                            </div>

                                        </div>

                                        <span class="mostra-meno hidden">mostra meno</span>
                                    </div>
                                    </c:forEach>

                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>

            <!-- METODI PAGAMENTO -->
            <div id="metodi-pagamento" style="display: none">
                <div class="container-card">

                    <c:choose>
                        <c:when test="${empty carte}">
                            <p>Non hai ancora salvato delle carte di credito</p>
                        </c:when>
                        <c:otherwise>

                            <% if (request.getAttribute("errori") != null){ %>
                                <jsp:useBean id="errori" scope="request" type="java.util.List" />
                            <% } %>

                            <c:forEach items="${carte}" var="carta">
                                <div class="carta">
                                    <div class="info-container">
                                        <div class="info">
                                            <img src="${context}/images/carteCredito/${carta.tipo}.png" alt="carta"/>

                                            <div class="container-main-info">
                                                <div class="info-main">
                                                    <p>${carta.tipo}</p>
                                                    <p id="info-numero">Termina con .... ${carta.numero.split(" ")[3]}</p>
                                                </div>
                                                <div  class="data_scadenza"><p id="info-data">Data di scadenza: ${carta.data_scadenza}</p></div>

                                                <form onsubmit="return checkValueAndSubmit(event, ${carta.id})" id="${carta.id}" method="post" class="hidden">
                                                    <div>
                                                        <label for="nome">Nome: </label>
                                                        <input id="nome" type="text" name="nome" value="${carta.nome}">
                                                        <span id="error-nome" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, ' nome')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="cognome">Cognome: </label>
                                                        <input id="cognome" type="text" name="cognome" value="${carta.cognome}">
                                                        <span id="error-cognome" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="numero">Numero carta: </label>
                                                        <input id="numero" maxlength="19" placeholder="nnnn nnnn nnnn nnnn" type="text" name="numero" value="${carta.numero}">
                                                        <span id="error-numero" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'numero')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="data">Data di scadenza: </label>
                                                        <input id="data" maxlength="5" placeholder="MM/AA" type="text" name="data" value="${carta.data_scadenza}">
                                                        <span id="error-data" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'data')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="cvv">cvv: </label>
                                                        <input id="cvv" maxlength="3" type="text" name="cvv" value="${carta.cvv}">
                                                        <span id="error-cvv" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'CVV')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div><input type="submit" value="Salva"></div>

                                                </form>
                                            </div>
                                        </div>

                                        <div class="actions">
                                            <div onclick="modificaCarta(${carta.id})" class="modifica"><i title="modifca carta" class="ri-edit-2-line"></i></div>
                                            <div class="elimina"><i title="Elimina dalla lista" class="ri-delete-bin-5-line"></i></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- MODIFICA DATI -->
            <div id="modifica-dati" style="display: none"></div>

        </div>
    </div>


    <%@ include file="../fragments/footer.jsp"%>

    <script src="${context}/js/navbar.js"></script>
    <script src="${context}/js/account.js"></script>

</body>
</html>

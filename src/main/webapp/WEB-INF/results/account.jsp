<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS-my account</title>
    <link rel="stylesheet" href="${context}/css/account.css">
</head>
<body>


    <c:if test="${empty user}">
        <% response.sendRedirect("index.jsp"); %>
    </c:if>

    <%@ include file="../fragments/navbar.jsp"%>

    <div class="container-utente">
        <div class="container-info-utente">
            <div role="button" title="cambia avatar" aria-label="cambiaAvatar" tabindex="0" onclick="changeAvatar()" class="container-img-utente"><img class="img-utente" src="${context}${user.foto}" alt="account-avatar" id="account-avatar" /></div>

            <div class="avatar-selection" id="avatar-selection">

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar0.png">
                    <img src="${context}/images/avatar/avatar0.png" alt="Avatar 0">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar1.png">
                    <img src="${context}/images/avatar/avatar1.png" alt="Avatar 1">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar2.png">
                    <img src="${context}/images/avatar/avatar2.png" alt="Avatar 2">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar3.png">
                    <img src="${context}/images/avatar/avatar3.png" alt="Avatar 3">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar4.png">
                    <img src="${context}/images/avatar/avatar4.png" alt="Avatar 4">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar5.png">
                    <img src="${context}/images/avatar/avatar5.png" alt="Avatar 5">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar6.png">
                    <img src="${context}/images/avatar/avatar6.png" alt="Avatar 6">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar7.png">
                    <img src="${context}/images/avatar/avatar7.png" alt="Avatar 7">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar8.png">
                    <img src="${context}/images/avatar/avatar8.png" alt="Avatar 8">
                </div>

                <div role="button" tabindex="0" class="avatar-option" data-avatar="avatar9.png">
                    <img src="${context}/images/avatar/avatar9.png" alt="Avatar 9">
                </div>

            </div>


            <div class="dati-main-utente">
                <div class="container-anagrafici">
                    <p class="cognome-utente">${user.cognome}</p>
                    <p class="nome-utente">${user.nome}</p>
                </div>

                <div class="container-regione">
                    <p>${user.regione}</p>
                </div>

                <a role="link" href="${context}/UpdateUser?from=logout">
                    <div class="logout">
                        <p>Logout</p>
                    </div>
                </a>
            </div>
        </div>
        <div class="container-option">
            <ul class="options">
                <li class="option active" id="scelta-wishlist"><a tabindex="0" role="button">Wishlist</a></li>
                <li class="option" id="scelta-ordini"><a tabindex="0" role="button">Ordini effettuati</a></li>
                <li class="option" id="scelta-pagamento"><a tabindex="0" role="button">Metodi di pagamento</a></li>
                <li class="option" id="scelta-modifica"><a tabindex="0" role="button">Modifica dati</a></li>
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
                            <p tabindex="0"  role="button" aria-label="EliminaTutti" onclick="deleteAllFromDb()" id="delete-all" class="delete-all">Rimuovi tutti</p>
                            <p tabindex="0"  role="button" aria-label="AggiungiTutti" onclick="addAllToCart()" id="add-all" class="add-all">Aggiungi tutti al carrello</p>
                        </div>


                        <div class="container-preferiti">
                            <c:forEach var="gioco" items="${wishlist}">
                                <div class="container-card">
                                    <div class="container-info-card">
                                        <div class="cont-img-card">
                                            <a tabindex="0" role="link" href="${context}/CardManager?id=${gioco.getId()}"><img src="${context}${gioco.getImg()}" alt="${gioco.getNome()}"></a>
                                        </div>
                                        <div class="info-card">
                                            <p class="nome">${gioco.getNome()}</p>
                                            <p class="prezzo"><fmt:formatNumber value="${gioco.getPrezzoScontato()}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                        </div>
                                    </div>
                                    <div class="actions-card">
                                        <a tabindex="0" id="${gioco.getId()}" role="button" aria-label="aggiungi" onclick="addToCart(${gioco.getId()})"><i title="Aggiungi al carrello" class="ri-shopping-cart-2-line"></i></a>
                                        <a tabindex="0" role="button" aria-label="elimina" onclick="deleteFromDb(${gioco.getId()}, this)" ><i title="Elimina dai preferiti" class="ri-delete-bin-5-line"></i></a>
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
                                                    <p><fmt:formatNumber value="${ordine.totale}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="container-games">
                                            <c:set var="totalProdotti" value="${fn:length(ordine.prodotti)}" />

                                            <c:forEach items="${ordine.prodotti}" var="prodottoComposto" begin="0" end="${totalProdotti > 3 ? 2 : totalProdotti - 1}">

                                                <div class="game">
                                                    <a tabindex="0" role="link" href="${context}/CardManager?id=${prodottoComposto.prodotto.id}"><img src="${context}${prodottoComposto.prodotto.img}" alt="${prodottoComposto.prodotto.nome}"></a>
                                                    <div class="info">
                                                        <h4 class="titolo">${prodottoComposto.prodotto.nome}</h4>
                                                        <div class="key hidden">
                                                            <p>key: ${prodottoComposto.key}</p>
                                                            <i role="button" tabindex="0" onclick="CopyKeyOnClipboard('${prodottoComposto.key}', this)"  class="ri-file-copy-2-line" title="copia"></i>
                                                        </div>
                                                        <p class="prezzo hidden"><fmt:formatNumber value="${prodottoComposto.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                                    </div>
                                                </div>

                                            </c:forEach>

                                            <div tabindex="0" onclick="ShowMoreGames(this)" role="button" data-order-id="${ordine.data}" class="game more">
                                                <p title="mostra di più">+</p>
                                            </div>

                                        </div>

                                        <span tabindex="0" onclick="showLess(this)" role="button" class="mostra-meno hidden">mostra meno</span>
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
                                <div id="carta${carta.id}" class="carta">
                                    <div class="info-container">
                                        <div class="info">

                                           <!-- <img src="${context}/images/carteCredito/${carta.tipo}.png" alt="Carta di credito"/> -->

                                            <div class="container-main-info">
                                                <div class="info-main">
                                                    <p id="info-numero">Termina con .... ${carta.numero.split(" ")[3]}</p>
                                                    <p id="info-cvv">CVV: ${carta.cvv}</p>
                                                </div>
                                                <div  class="data_scadenza"><p id="info-data">Data di scadenza: ${carta.data_scadenza}</p></div>

                                                <form onsubmit="return checkValueAndSubmit(event, ${carta.id})" id="form-carta${carta.id}" method="post" class="hidden">
                                                    <div>
                                                        <label for="nome${carta.id}">Nome: </label>
                                                        <input tabindex="0" id="nome${carta.id}" type="text" name="nome" value="${carta.nome}">
                                                        <span id="error-nome" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, ' nome')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="cognome${carta.id}">Cognome: </label>
                                                        <input tabindex="0" id="cognome${carta.id}" type="text" name="cognome" value="${carta.cognome}">
                                                        <span id="error-cognome" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="numero${carta.id}">Numero carta: </label>
                                                        <input tabindex="0" id="numero${carta.id}" maxlength="19" placeholder="nnnn nnnn nnnn nnnn" type="text" name="numero" value="${carta.numero}">
                                                        <span id="error-numero" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'numero')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="data${carta.id}">Data di scadenza: </label>
                                                        <input tabindex="0" id="data${carta.id}" maxlength="5" placeholder="MM/AA" type="text" name="data" value="${carta.data_scadenza}">
                                                        <span id="error-data" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'data')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div>
                                                        <label for="cvv${carta.id}">cvv: </label>
                                                        <input tabindex="0" id="cvv${carta.id}" maxlength="3" type="text" name="cvv" value="${carta.cvv}">
                                                        <span id="error-cvv" class="error-input">
                                                            <c:forEach items="${errori}" var="errore">
                                                                <c:if test="${fn:containsIgnoreCase(errore, 'CVV')}" >
                                                                    ${errore}
                                                                </c:if>
                                                            </c:forEach>
                                                        </span>
                                                    </div>

                                                    <div><input tabindex="0" type="submit" value="Salva"></div>

                                                </form>
                                            </div>
                                        </div>

                                        <div class="actions">
                                            <div tabindex="0" onclick="modificaCarta(${carta.id})" class="modifica"><i title="Modifca carta" class="ri-edit-2-line"></i></div>
                                            <div tabindex="0" onclick="deleteCarta(${carta.id})" class="elimina"><i title="Elimina dalla lista" class="ri-delete-bin-5-line"></i></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- MODIFICA DATI -->
            <div id="modifica-dati" style="display: none">
                <p tabindex="0" onclick="abilitaModifica(this)" class="modifica">Abilita Modifica</p>
                <form action="UpdateUser?from=modifica" method="post" onsubmit="return validateUpdateForm(event, this)" class="disabled">
                    <div class="container-input-row">
                        <div class="container-input">
                            <label for="nome-utente">Nome:</label>
                            <input tabindex="0" id="nome-utente" type="text" name="nome" value="${user.nome}" class="input" disabled>
                            <span id="nome-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, ' nome')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>

                        <div class="container-input">
                            <label for="cognome-utente">Cognome:</label>
                            <input tabindex="0" id="cognome-utente" type="text" name="cognome" value="${user.cognome}" class="input" disabled>
                            <span id="cognome-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>
                    </div>

                    <div class="container-input-row">
                        <div class="container-input">
                            <label for="email-utente">Email:</label>
                            <input tabindex="0" id="email-utente" type="email" name="email" value="${user.email}" class="input" disabled>
                            <span id="email-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'email')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>

                        <div class="container-input">
                            <label for="pass-utente">Cambia password:</label>
                            <input tabindex="0" id="pass-utente" type="password" name="new-pass" placeholder="Inserisci se vuoi cambiare" class="input" disabled>
                            <span id="pass-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, '8')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>
                    </div>

                    <div class="container-input-row">
                        <div class="container-input">
                            <label for="regione-utente">Regione:</label>
                            <select tabindex="0" id="regione-utente"  name="regione" class="input" disabled>
                                <option tabindex="0" value="US" <c:if test="${user.regione == 'US'}">selected</c:if>>America</option>
                                <option tabindex="0" value="EU" <c:if test="${user.regione == 'EU'}">selected</c:if>>Europa</option>
                                <option tabindex="0" value="AS" <c:if test="${user.regione == 'AS'}">selected</c:if>>Asia</option>
                            </select>
                            <span id="regione-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'regione')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>

                        <div class="container-input">
                            <label for="data-utente">Data di nascita:</label>
                            <input tabindex="0" id="data-utente" type="date" name="data" value="${user.data}" class="input" disabled>
                            <span id="data-utente-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'data')}" >
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                        </div>
                    </div>

                    <p class="success">Modifiche salvate</p>

                    <input tabindex="0" class="input" type="submit" value="Salva" disabled>
                </form>
            </div>
        </div>
    </div>


    <%@ include file="../fragments/footer.jsp"%>

    <script src="${context}/js/navbar.js"></script>
    <script src="${context}/js/account.js"></script>
    <script src="${context}/js/carrello.js"></script>

</body>
</html>

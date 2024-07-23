<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%@ include file="general.jsp"%>

    <link rel="stylesheet" type="text/css" href="${context}/css/admin.css" />

    <title>GS-Admin</title>
</head>
<body>

<div class="page">

    <div class="actions">
        <a role="link" href="${context}/index.jsp"><img class="logo" src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a>
        <a role="button" tabindex="0" onclick="displayDashboard()" class="active action" >Dashboard<i class="ri-dashboard-2-line"></i></a>
        <a role="button" tabindex="0" onclick="displayContainer()" class="dropdown action" >Dati<div class="icon"><i class="ri-database-2-line"></i><i class="ri-arrow-drop-down-line"></i></div></a>
        <div class="dropdown-container">
            <a role="button" tabindex="0" onclick="getTable(this.innerHTML)" class="action">Prodotti</a>
            <a role="button" tabindex="0" onclick="getTable(this.innerHTML)" class="action">Generi</a>
            <a role="button" tabindex="0" onclick="getTable(this.innerHTML)" class="action">Utenti</a>
            <a role="button" tabindex="0" onclick="getTable(this.innerHTML)" class="action">Carrelli</a>
            <a role="button" tabindex="0" onclick="getTable(this.innerHTML)" class="action">Ordini</a>
        </div>
        <a  role="link" href="${context}/UpdateUser?from=logout" class="action">Logout<i class="ri-logout-box-line"></i></a>
    </div>

    <div style="width: 20%;visibility: hidden;" id="type">${type}</div>

    <div id="output" class="output">
        <div id="dashboard">
            <div role="button" tabindex="0" onclick="getTable('Carrelli')" class="element">
                <h2>Carrelli</h2>
                <p>${totCarrelli}</p>
            </div>
            <div role="button" tabindex="0" onclick="getTable('Utenti')" class="element">
                <h2>Utenti</h2>
                <p>${totUtenti}</p>
            </div>
            <div role="button" tabindex="0" onclick="getTable('Ordini effettuati')" class="element">
                <h2>Ordini</h2>
                <p>${totOrdini}</p>
            </div>

            <div role="button" tabindex="0" onclick="getTable('Prodotti')" class="element">
                <h2>Prodotti</h2>
                <p>${totProdotti}</p>
            </div>
            <div class="element">
                <h2>Guadagni Ultimo Mese</h2>
                <p><fmt:formatNumber value="${totUltimoMese}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
            </div>
            <div class="element">
                <h2>Guadagni</h2>
                <p><fmt:formatNumber value="${totGuadagno}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
            </div>
        </div>

        <div id="table">
            <h2 id="nomeTabella"></h2>

            <div class="table-actions">
                <button type="button" tabindex onclick="aggiungiEntita()" id="add-record">Aggiungi</button>
                <c:if test="${user.tipo == TipoUtente.Admin1}">
                    <button type="button" tabindex onclick="eliminaEntita()" id="delete-record">Elimina</button>
                    <form id="form-delete" action="${context}/AdminManager" style="display: none" method="post">
                        <input type="hidden" id="hidden-delete" name="from" value="delete">
                        <label>
                            <input aria-label="placeholder-delte" type="text" name="input" id="delete-input" placeholder="" title="">
                        </label>
                        <label class="error-input">
                            <c:forEach items="${errori}" var="errore">
                                <c:if test="${fn:containsIgnoreCase(errore, 'eliminato') || fn:containsIgnoreCase(errore, 'non eliminato')}" >
                                    ${errore}
                                </c:if>
                            </c:forEach>
                        </label>
                    </form>
                </c:if>
                <label>
                    <input aria-label="search" name="searchInDb" id="searchBar" type="search" placeholder="Cerca....">
                </label>

            </div>

            <div class="table-wrapper">
                <table>
                    <thead id="tableHead"></thead>
                    <tbody id="tableBody"></tbody>
                </table>
            </div>
        </div>

        <div id="form-table-action">
            <div id="addProdotto">
                <form action="${context}/AdminManager" method="post" onsubmit="return checkFormAdmin(this)">

                    <input type="hidden" name="from" value="addProdotto">

                    <label for="addNome">Nome</label>
                    <input type="text" name="nome" id="addNome" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'nome')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addDesc">Descrizione</label>
                    <textarea name="descrizione" id="addDesc"></textarea>
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'descrizione')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addData">Data di rilascio</label>
                    <input type="date" name="dataRilascio" id="addData" />
                    <label class="error-input" >
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'data')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addPrezzo">Prezzo (€)</label>
                    <input type="text" name="prezzo" id="addPrezzo" value="0" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'prezzo')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addSconto">Sconto (%)</label>
                    <input type="text" name="sconto" id="addSconto" value="0" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'sconto')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label>Generi</label>
                    <div class="scroll-box">
                        <c:forEach items="${generi}" var="genere">
                            <div class="container-generi">
                                <label>
                                    <input aria-label="genere" id="${genere}" type="checkbox" name="genere" value="${genere}">
                                </label>
                                <label for="${genere}">${genere}</label><br>
                            </div>
                        </c:forEach>
                    </div>
                    <label id="error-generi" class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'genere')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addImg">Immagine</label>
                    <input type="url" id="addImg" name="urlImg" accept="image/*" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'urlImg')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addTrailer">Trailer</label>
                    <input type="url" id="addTrailer" name="urlTrailer" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="error">
                            <c:if test="${fn:containsIgnoreCase(error, 'urlTrailer')}">
                                ${error}
                            </c:if>
                        </c:forEach>
                    </label>

                    <input type="reset" value="Reset">
                    <input type="submit" value="Salva">

                    <br>
                    <label id="mexProd-general" class="mex-general">
                        <c:forEach items="${errori}" var="mex">
                            <c:if test="${fn:containsIgnoreCase(mex, 'presente') || fn:containsIgnoreCase(mex, 'aggiunto')}">
                                ${mex}
                            </c:if>
                        </c:forEach>
                    </label>
                </form>
            </div>

            <div id="addUser">
                <form action="${context}/AdminManager" method="post" onsubmit="return checkFormAdmin(this)">

                    <input type="hidden" name="from" value="addUser">

                    <label for="addEmail">Email</label>
                    <input type="email" name="email" id="addEmail" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'email')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addNomeUser">Nome</label>
                    <input type="text" name="nome" id="addNomeUser" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, ' nome')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addCognome">Cognome</label>
                    <input type="text" name="cognome" id="addCognome" />
                    <label class="error-input" >
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addRegione">Regione</label>
                    <select name="regione" id="addRegione">
                        <option value="" aria-label="Seleziona regione">Seleziona</option>
                        <option value="US" aria-label="America">America</option>
                        <option value="EU" aria-label="Europa">Europa</option>
                        <option value="AS" aria-label="Asia">Asia</option>
                    </select>
                    <label class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'regione')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addDataUser">Data di nascita</label>
                    <input type="date" name="dataNascita" id="addDataUser" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'data')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label for="addPass">Password</label>
                    <input type="password" name="password" id="addPass" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, '8')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </label>

                    <div>
                        <div class="container-admin1">
                            <input type="radio" name="tipo" id="ad1" value="Admin1" />
                            <label for="ad1">Admin1</label>
                        </div>
                        <div class="container-admin2">
                            <input type="radio" name="tipo" id="ad2" value="Admin2" />
                            <label for="ad2">Admin2</label>
                        </div>
                        <div class="container-semplice">
                            <input type="radio" name="tipo" id="semplice" value="Semplice" />
                            <label for="semplice">Semplice</label>
                        </div>
                        <label class="error-input" id="error-tipo-user">
                            <c:forEach items="${errori}" var="mex">
                                <c:if test="${fn:containsIgnoreCase(mex, 'tipo')}">
                                    ${mex}
                                </c:if>
                            </c:forEach>
                        </label>
                    </div>

                    <input type="reset" value="Reset">
                    <input type="submit" value="Salva">

                    <br>
                    <label id="mexUser-general" class="mex-general">
                        <c:forEach items="${errori}" var="mex">
                            <c:if test="${fn:containsIgnoreCase(mex, 'presente') || fn:containsIgnoreCase(mex, 'aggiunto')}">
                                ${mex}
                            </c:if>
                        </c:forEach>
                    </label>

                </form>
            </div>

            <div id="addGenere">
                <form action="${context}/AdminManager" method="post" onsubmit="return checkFormAdmin(this)">

                    <input type="hidden" name="from" value="addGenere">

                    <label for="addGenereSingolo">Genere</label>
                    <input type="text" name="genere" id="addGenereSingolo" />
                    <label class="error-input">
                        <c:forEach items="${errori}" var="mex">
                            <c:if test="${fn:containsIgnoreCase(mex, 'valore')}">
                                ${mex}
                            </c:if>
                        </c:forEach>
                    </label>

                    <label>Giochi</label>
                    <div class="scroll-box">
                        <c:forEach items="${nomiGiochi}" var="nome">
                            <br>
                            <div class="container-nomi">
                                <label>
                                    <input aria-label="list-games" id="${nome}" type="checkbox" name="listGames" value="${nome}">
                                </label>
                                <label for="${nome}">${nome}</label>
                            </div>
                        </c:forEach>
                    </div>
                    <label class="error-input" id="error-giochi-selezionati">
                        <c:forEach items="${errori}" var="mex">
                            <c:if test="${fn:containsIgnoreCase(mex, 'gioco')}">
                                ${mex}
                            </c:if>
                        </c:forEach>
                    </label>


                    <input type="reset" value="Reset">
                    <input type="submit" value="Salva">

                    <br>
                    <label id="mexGenere-general" class="mex-general">
                        <c:forEach items="${errori}" var="mex">
                            <c:if test="${fn:containsIgnoreCase(mex, 'presente') || fn:containsIgnoreCase(mex, 'aggiunto')}">
                                ${mex}
                            </c:if>
                        </c:forEach>
                    </label>
                </form>
            </div>


        </div>
    </div>
</div>

<script src="${context}/js/admin.js"></script>

</body>
</html>

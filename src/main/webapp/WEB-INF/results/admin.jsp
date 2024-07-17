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
        <a role="button" onclick="displayDashboard()" class="active action" >Dashboard<i class="ri-dashboard-2-line"></i></a>
        <a role="button" onclick="displayContainer()" class="dropdown action" >Dati<div class="icon"><i class="ri-database-2-line"></i><i class="ri-arrow-drop-down-line"></i></div></a>
        <div class="dropdown-container">
            <a role="button" onclick="getTable(this.innerHTML)" class="action">Prodotti</a>
            <a role="button" onclick="getTable(this.innerHTML)" class="action">Generi</a>
            <a role="button" onclick="getTable(this.innerHTML)" class="action">Utenti</a>
            <a role="button" onclick="getTable(this.innerHTML)" class="action">Carrelli</a>
            <a role="button" onclick="getTable(this.innerHTML)" class="action">Ordini</a>
        </div>
        <a  role="link" href="${context}/UpdateUser?from=logout" class="action">Logout<i class="ri-logout-box-line"></i></a>
    </div>

    <div style="width: 20%;visibility: hidden;"></div>

    <div id="output" class="output">
        <div id="dashboard">
            <div onclick="getTable('Carrelli')" class="element">
                <h2>Carrelli</h2>
                <p>${totCarrelli}</p>
            </div>
            <div onclick="getTable('Utenti')" class="element">
                <h2>Utenti</h2>
                <p>${totUtenti}</p>
            </div>
            <div onclick="getTable('Ordini effettuati')" class="element">
                <h2>Ordini</h2>
                <p>${totOrdini}</p>
            </div>

            <div onclick="getTable('Prodotti')" class="element">
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

                <c:if test="${user.tipo == TipoUtente.Admin1}">
                    <button type="button" tabindex onclick="aggiungiEntita()" id="add-record">Aggiungi</button>
                    <button type="button" tabindex onclick="eliminaEntita()" id="delete-record">Elimina</button>
                </c:if>

                <button type="button" tabindex onclick="modificaEntita()" id="edit-record">Modifica</button>
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

        <div style="display: none" id="form-table-action">
            <div id="prodotti-form-add">
                <form id="addProdotti">

                    <label for="addNome">Nome</label>
                    <input type="text" name="nome" id="addNome" />
                    <label class="error-input"></label>

                    <label for="addDesc">Descrizione</label>
                    <input type="text" name="descrizione" id="addDesc" />
                    <label class="error-input"></label>

                    <label for="addData"></label>
                    <input type="date" name="data" id="addData" />
                    <label class="error-input"></label>

                    <label for="addPrezzo"></label>
                    <input type="number" name="prezzo" id="addPrezzo" />
                    <label class="error-input"></label>

                    <label for="addSconto">Sconto (%)</label>
                    <input type="number" name="sconto" id="addSconto" />
                    <label class="error-input"></label>

                    <label for="addImg">Immagine</label>
                    <input type="image" name="immagine" id="addImg" />
                    <label class="error-input"></label>

                    <label>Generi</label>
                    <c:forEach items="${generi}" var="genere">
                        <input id="${genere}" name="${genere}" value="${genere}">
                        <label for="${genere}"></label>
                    </c:forEach>
                    <label class="error-input"></label>



                </form>
            </div>

            <div id="user-form-add">

            </div>

            <div id="genere-form-add">

            </div>

            <div id="carrello-form-add">

            </div>

            <div id="ordini-form-add">

            </div>

            <div id="prodotti-form-delete">

            </div>

            <div id="user-form-delete">

            </div>

            <div id="genere-form-delete">

            </div>

            <div id="carrello-form-delete">

            </div>

            <div id="ordini-form-delete">

            </div>

            <div id="prodotti-form-modify">

            </div>

            <div id="user-form-modify">

            </div>

            <div id="genere-form-modify">

            </div>

            <div id="carrello-form-modify">

            </div>

            <div id="ordini-form-modify">

            </div>
        </div>
    </div>
</div>

<script src="${context}/js/admin.js"></script>

</body>
</html>

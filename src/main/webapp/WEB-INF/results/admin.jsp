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
        <a href="${context}/index.jsp"><img class="logo" src="${context}/images/logoWhiteNoBackround.png" alt="logo"></a>
        <a onclick="displayDashboard()" class="active action" >Dashboard<i class="ri-dashboard-2-line"></i></a>
        <a onclick="displayContainer()" class="dropdown action" >Dati<div class="icon"><i class="ri-database-2-line"></i><i class="ri-arrow-drop-down-line"></i></div></a>
        <div class="dropdown-container">
            <a onclick="getTable(this.innerHTML)" class="action">Prodotti</a>
            <a onclick="getTable(this.innerHTML)" class="action">Generi</a>
            <a onclick="getTable(this.innerHTML)" class="action">Utenti</a>
            <a onclick="getTable(this.innerHTML)" class="action">Carrelli</a>
            <a onclick="getTable(this.innerHTML)" class="action">Ordini</a>
        </div>
        <a href="${context}/UpdateUser?from=logout" class="action">Logout<i class="ri-logout-box-line"></i></a>
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
                    <button onclick="aggiungiEntita()" id="add-record">Aggiungi</button>
                    <button onclick="eliminaEntita()" id="delete-record">Elimina</button>
                </c:if>

                <button onclick="modificaEntita()" id="edit-record">Modifica</button>
                <label>
                    <input name="searchInDb" type="search" placeholder="Cerca....">
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
            <!-- TODO fare i form per ogni azione -->
        </div>
    </div>
</div>

<script src="${context}/js/admin.js"></script>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general.jsp"%>
    <link rel="stylesheet" type="text/css" href="${context}/css/pagamento.css">
    <title>GS-pagamento</title>
</head>
<body>
    <%@ include file="../fragments/navbar.jsp"%>

<div id="page-pagamento">
    <c:choose>
        <c:when test="${empty esito}">

            <form action="${context}/UpdateUser" method="post" class="form-carte" id="form-pagamento" onsubmit="return validateFormPagamentoCard(this, '')">
                <input type="hidden" name="from" value="pagamento">
                <div class="form-container" style="display: flex;width: 100%;">
                    <div class="container-carte">
                        <h1>Le tue carte</h1>
                        <div class="carte-credito">
                            <c:choose>
                                <c:when test="${carte.size() > 0}">
                                    <c:forEach items="${carte}" var="carta">
                                        <div class="carta">
                                            <label for="${carta.id}"></label>
                                            <input type="radio" aria-label="cartaCredito" id="${carta.id}" name="cartaSalvata" value="${carta.id}">
                                            <div class="info-main">
                                                <p>Nome : ${carta.nome}</p>
                                                <p>Cognome : ${carta.cognome}</p>
                                                <p>Data di scadenza : ${carta.data_scadenza}</p>
                                                <p>CVV : ${carta.cvv}</p>
                                                <p>Numero: ${carta.numero}</p>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <p>Non hai ancora carte salvate</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

            <div class="form-aggiunta-carta">
                <div>
                    <label for="cognome">Cognome:</label>
                    <input tabindex="0" id="cognome" type="text" name="cognome" class="input">
                    <span id="error-cognome" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}" >
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </span>
                </div>

                <div>
                    <label for="nome">Nome:</label>
                    <input tabindex="0" id="nome" type="text" name="nome" class="input">
                    <span id="error-nome" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, ' nome')}" >
                                ${errore}
                            </c:if>
                        </c:forEach>

                    </span>
                </div>

                <div>
                    <label for="numero">Numero:</label>
                    <input tabindex="0" id="numero" type="text" name="numero" class="input">
                    <span id="error-numero" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'numero')}" >
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </span>
                </div>

                <div>
                    <label for="data">Data di scadenza:</label>
                    <input tabindex="0" id="data" type="text" name="data" class="input">
                    <span id="error-data" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'data')}" >
                                ${errore}
                            </c:if>
                        </c:forEach>

                    </span>
                </div>

                <div>
                    <label for="cvv">CVV:</label>
                    <input tabindex="0" id="cvv" type="text" name="cvv" class="input">
                    <span id="error-cvv" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'cvv')}" >
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </span>
                </div>

                <div class="salva-carta">
                    <label for="saveCarta">Salva carta</label>
                    <input id="saveCarta" name="salvaCarta" type="checkbox" value="Salva">
                </div>
            </div>
        </div>
        <div class="pagamento-button">
            <input type="submit" value="Paga" id="paga">
            <input type="reset" value="Reset" id="reset">
        </div>
    </form>
    <div class="totale">Totale: <fmt:formatNumber value="${carrello.prezzoScontatoTotale}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</div>
    </c:when>
    <c:otherwise>
        <div class="empty-cart-message">
            <h2>Acquisto effettuato!</h2>
            <p>Visita la <a href="${context}/UserManager">tua pagina</a> per riscattare!</p>
        </div>
    </c:otherwise>
    </c:choose>

</div>



<script src="${context}/js/account.js"></script>
<script src="${context}/js/pagamento.js"></script>





<%@ include file="../fragments/footer.jsp"%>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="./general.jsp"%>
    <title>Categoria</title>
    <link rel="stylesheet" href="${context}/css/sezioni.css">
    <link rel="stylesheet" href="${context}/css/categoria.css">

</head>
<body>
<%@ include file="../fragments/navbar.jsp"%>

    <div class="filtri">
        <div class="gruppo1">
            <div class="genere-container">
                <label>
                    <select name="generi" aria-label="generi" id="filtroGenere" class="filtro genere" >
                        <option name="genere" id="vuoto" aria-label="value-genere" value="0"> </option>
                        <% if (request.getAttribute("generi")!=null){ %>
                        <jsp:useBean id="generi" scope="request" type="java.util.List"/>
                        <%}%>
                        <c:forEach items="${generi}" var="genere" varStatus="value">
                            <option name="genere" id="${genere}" aria-label="${genere}" value="${value.index + 1}">${genere}</option>
                        </c:forEach>
                    </select>
                </label>
                <div class="overlay-genere" id="overlayGenere">Generi</div>
            </div>
            <div class="ordine-container">
                <label>
                    <select name="filtri" aria-label="filtri" id="filtroOrdine" class="filtro ordine">

                        <option name="genere" aria-label="filtro1" id="filtro1" value="0"></option>
                        <option name="genere" aria-label="filtro3" id="filtro3" value="discount_desc">Sconto: migliore</option>
                        <option name="genere" aria-label="filtro4" id="filtro4" value="price_asc">Prezzo: crescente</option>
                        <option name="genere" aria-label="filtro5" id="filtro5" value="price_desc">Prezzo: decrescente</option>
                        <option name="genere" aria-label="filtro6" id="filtro6" value="avail_date_desc">Uscita: più recente</option>
                        <option name="genere"  aria-label="filtro7" id="filtro7" value="avail_date_asc">Uscita: meno recente</option>

                    </select>
                </label>
                <div class="overlay-ordine" id="overlayOrdine">Ordina per</div>
            </div>
        </div>
        <div class="gruppo2">
            <div class="prezzo">
                Tra
                <div aria-label="container-min-price" class="price-range">
                    <label for="filtroMinPrice"></label>
                    <input aria-label="min-price" name="min-price"  type="text" class="range min" value="0" data-default="0" id="filtroMinPrice">
                </div>
                a
                <div aria-label="container-max-price" class="price-range">
                    <label for="filtroMaxPrice"></label>
                    <input aria-label="max-price" name="max-price" type="text" class="range max" value="200" data-default="200" id="filtroMaxPrice">
                </div>
                <div>€</div>
            </div>
            <div role="button" tabindex="0" class="reset">
                <h3>Reset filtri</h3>
            </div>
        </div>
    </div>

    <section  class="sezione">
        <div class="titolo">
            <h2 id="textTitoloSezioneEstesa"></h2>
        </div>
        <div class="card-container">
            <% if(request.getAttribute("giochi") != null){ %>
                <jsp:useBean id="giochi" scope="request" type="java.util.List"/>
            <%}%>
            <c:forEach items="${giochi}" var="gioco" >
                <c:choose>
                        <c:when test="${gioco.sconto > 0}">
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-image-container">
                                        <a role="link" tabindex="0" aria-label="link-img" href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
                                        <div class="scontato">-${gioco.sconto}%</div>
                                    </div>
                                    <div class = "card-info">
                                        <h3 class="title">${gioco.nome}</h3>
                                        <p><fmt:formatNumber value="${gioco.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="card">
                                <div class="card-content">
                                    <div class="card-image-container">
                                        <a role="link" tabindex="0" aria-label="link-img" href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
                                    </div>
                                    <div class = "card-info">
                                        <h3 class="title">${gioco.nome}</h3>
                                        <p><fmt:formatNumber value="${gioco.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p role="button" tabindex="0" class="mostraAltroText">Mostra Altro</p>
    </section>

<%@ include file="../fragments/footer.jsp"%>
<script src="${context}/js/categoria.js"></script>
<script src="${context}/js/navbar.js"></script>
</body>
</html>

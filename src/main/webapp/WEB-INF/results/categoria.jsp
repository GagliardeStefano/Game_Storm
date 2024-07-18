<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="./general.jsp"%>
    <title>Categoria</title>
    <link rel="stylesheet" href="${context}/css/sezioni.css">
    <link rel="stylesheet" href="${context}/css/categoria.css">
    <script> const contextPath = '${context}';</script>

</head>
<body>
<%@ include file="../fragments/navbar.jsp"%>

    <div class="filtri">
        <div class="gruppo1">
            <div class="genere-container">
                <select id="filtroGenere" class="filtro genere" >
                    <option value="0"></option>
                    <% if (request.getAttribute("generi")!=null){ %>
                    <jsp:useBean id="generi" scope="request" type="java.util.List"/>
                    <%}%>
                    <c:forEach items="${generi}" var="genere" varStatus="value">
                        <option value="${value.index + 1}">${genere}</option>
                    </c:forEach>
               </select>
                <div class="overlay-genere" id="overlayGenere">Generi</div>
            </div>
            <div class="ordine-container">
                <select id="filtroOrdine" class="filtro ordine">
                    <option value="0"></option>
                    <option value="bestsellers_desc">Bestseller</option>
                    <option value="discount_desc">Sconto: migliore</option>
                    <option value="price_asc">Prezzo: da basso ad alto</option>
                    <option value="price_desc">Prezzo: da alto a basso</option>
                    <option value="avail_date_desc">Uscita: nuovo</option>
                    <option value="avail_date_asc">Uscita: vecchio</option>
                </select>
                <div class="overlay-ordine" id="overlayOrdine">Ordina per</div>
            </div>
        </div>
        <div class="gruppo2">
            <div class="prezzo">
                Tra
                <div class="price-range">
                    <input type="text" class="range min" value="0" data-default="0" id="filtroMinPrice">
                </div>
                a
                <div class="price-range">
                    <input type="text" class="range max" value="200" data-default="200" id="filtroMaxPrice">
                </div>
                <div>€</div>
            </div>
            <div class="reset">
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
                                        <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt=""></a>
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
                                        <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt=""></a>
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
        <p class="mostraAltroText">Mostra Altro</p>
    </section>

<%@ include file="../fragments/footer.jsp"%>
<script src="${context}/js/categoria.js"></script>
<script src="${context}/js/navbar.js"></script>
</body>
</html>

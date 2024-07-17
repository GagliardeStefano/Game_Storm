<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game Storm</title>

    <%@ include file="WEB-INF/results/general.jsp"%>

    <!-- Stili css -->
    <link rel="stylesheet" type="text/css" href="${context}/css/carousel.css" />
    <link rel="stylesheet" type="text/css" href="${context}/css/sezioni.css" />

</head>
<body>
<!-- NAVBAR -->
<%@include file="WEB-INF/fragments/navbar.jsp"%>

<main>
    <!-- CAROSELLO -->
    <section class="carousel">
        <c:forEach items="${carosello}" var="carta" varStatus="status">
            <div role="link" tabindex="0" class="card ${status.first ? 'expanded' : ''}">
                <div class="card-content">
                    <a href="${context}/CardManager?id=${carta.id}" class="disabled"><img src="${context}${carta.img}" alt="${carta.nome}"></a>
                    <div class="info">
                        <h3 class="title">${carta.nome}</h3>
                        <p><fmt:formatNumber value="${carta.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </section>
    <!-- FINE CAROSELLO-->

    <section class="sezione trending">
        <div class="titolo trending">
            <h2>In tendenza</h2>
            <a role="link" href="${context}/SectionManager"><h5>Visualizza tutto ></h5></a>
        </div>
        <div class="card-container trending">
            <c:forEach items="${tendenze}" var="tendenza">
                <div class="card">
                    <div class="card-content">
                        <div class="card-image-container">
                            <a role="link" href="${context}/CardManager?id=${tendenza.id}"><img class="card__image" src="${context}${tendenza.img}" alt="${tendenza.nome}"></a>
                            <div class="scontato">-${tendenza.sconto}%</div>
                        </div>
                        <div class = "card-info">
                            <h3 class="title">${tendenza.nome}</h3>
                            <p><fmt:formatNumber value="${tendenza.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>
    </section>

    <section class="sezione preorder">
        <div class="titolo preorder">
            <h2>Preordini</h2>
            <a role="link" href="${context}/SectionManager"><h5>Visualizza tutto ></h5></a>
        </div>
        <div class="card-container preorder">
            <c:forEach items="${preordini}" var="preordine">
                <div class="card">
                    <div class="card-content">
                        <div class="card-image-container">
                            <a role="link" href="${context}/CardManager?id=${preordine.id}"><img class="card__image" src="${context}${preordine.img}" alt="${preordine.nome}"></a>
                            <div class="scontato">-${preordine.sconto}%</div>
                        </div>
                        <div class = "card-info">
                            <h3 class="title">${preordine.nome}</h3>
                            <p><fmt:formatNumber value="${preordine.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>

    <section class="sezione arrival">
        <div class="titolo arrival">
            <h2>Prossime Uscite</h2>
            <a role="link" href="${context}/SectionManager"><h5>Visualizza tutto ></h5></a>
        </div>
        <div class="card-container arrival">
            <c:forEach items="${prossimeUscite}" var="prossimaUscita">
                <div class="card">
                    <div class="card-content">
                        <div class="card-image-container">
                            <a role="link" href="${context}/CardManager?id=${prossimaUscita.id}"><img class="card__image" src="${context}${prossimaUscita.img}" alt="${prossimaUscita.nome}"></a>
                            <div class="scontato">-${prossimaUscita.sconto}%</div>
                        </div>
                        <div class = "card-info">
                            <h3 class="title">${prossimaUscita.nome}</h3>
                            <p><fmt:formatNumber value="${prossimaUscita.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>

</main>

<%@ include file="./WEB-INF/fragments/footer.jsp"%>
<!-- SCRIPT -->
<script src="${context}/js/navbar.js"></script>
<script src="${context}/js/carousel.js"></script>
<script src="${context}/js/sezioni.js"></script>
</body>
</html>
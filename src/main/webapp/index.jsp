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
            <h2>In Tendenza</h2>
            <a role="link" href="${context}/CategoriaManager"><h4>Visualizza tutto ></h4></a>
        </div>
        <div class="card-container trending">
            <c:forEach items="${tendenze}" var="gioco" >
                <c:choose>
                    <c:when test="${gioco.sconto > 0}">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-image-container">
                                    <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
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
                        <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
                    </div>
                    <div class = "card-info">
                        <h3 class="title">${gioco.nome}</h3>
                        <p><fmt:formatNumber value="${gioco.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                    </div>
                </div>
            </div>
        </c:otherwise>
                </c:choose>
            </c:forEach>

        </div>
    </section>

    <section class="sezione preorder">
        <div class="titolo preorder">
            <h2>Preordini</h2>
            <a role="link" href="${context}/CategoriaManager"><h4>Visualizza tutto ></h4></a>
        </div>
        <div class="card-container preorder">
            <c:forEach items="${preordini}" var="gioco" >
                <c:choose>
                    <c:when test="${gioco.sconto > 0}">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-image-container">
                                    <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
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
                                    <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
                                </div>
                                <div class = "card-info">
                                    <h3 class="title">${gioco.nome}</h3>
                                    <p><fmt:formatNumber value="${gioco.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </section>

    <section class="sezione arrival">
        <div class="titolo arrival">
            <h2>Prossime Uscite</h2>
            <a role="link" href="${context}/CategoriaManager"><h4>Visualizza tutto ></h4></a>
        </div>
        <div class="card-container arrival">
            <c:forEach items="${prossimeUscite  }" var="gioco" >
                <c:choose>
                    <c:when test="${gioco.sconto > 0}">
                        <div class="card">
                            <div class="card-content">
                                <div class="card-image-container">
                                    <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
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
                                    <a href="${context}/CardManager?id=${gioco.id}"><img class="card__image" src="${context}${gioco.img}" alt="${gioco.nome}"></a>
                                </div>
                                <div class = "card-info">
                                    <h3 class="title">${gioco.nome}</h3>
                                    <p><fmt:formatNumber value="${gioco.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
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
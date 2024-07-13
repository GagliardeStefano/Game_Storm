<%--
  Created by IntelliJ IDEA.
  User: jiuse
  Date: 25/06/2024
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general.jsp" %>
    <title>GS-card</title>
    <link rel="stylesheet" href="${context}/css/game.css">
    <link rel="stylesheet" href="${context}/css/sezioni.css">
</head>
<body>
    <%@ include file="../fragments/header.jsp"%>
    <jsp:useBean id="prodotto" scope="request" type="Model.Prodotto" />

    <div class="main-container">
        <div class="card">
            <div class="card-img">
                <img src="${context}/${prodotto.img}" alt="${prodotto.nome}">
            </div>

            <div class="card-info">
                <h1 class="title">${prodotto.nome}</h1>
                <div class="amount">
                    <div class="disc-div">
                        <div class="original-price"><fmt:formatNumber value="${prodotto.prezzo}" type="number" minFractionDigits="2" maxFractionDigits="2"/></div>
                        <div class="discounted">-${prodotto.sconto}%</div>
                    </div>
                    <div class="total"><fmt:formatNumber value="${prodotto.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</div>
                </div>

                <div class="actions">
                    <c:choose>
                        <c:when test="${empty user}">
                            <div class="favourite tooltip">
                                <i id="mex-heart" class="ri-chat-heart-line"></i>
                                <span class="tooltiptext" >Accedi per aggiungerlo ai preferiti</span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="favourite" onclick="updateFavourite(${prodotto.id})">
                                <c:choose>
                                    <c:when test="${preferito}">
                                        <i id="heart" class="ri-heart-fill fill" ></i>
                                    </c:when>
                                    <c:otherwise>
                                        <i id="heart" class="ri-heart-line"></i>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:otherwise>
                    </c:choose>


                    <div class="cart">
                        <i class="ri-shopping-cart-2-line"></i>
                        <span>Aggiungi al carello</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-description">

            <div class="descrizione"> <h1>Descrizione</h1>
               <p> ${prodotto.descrizione}</p>
            </div>
            <div class="tags">
                <h1>Tags</h1>
                <div class="tag">
                    <c:forEach items="${generi}" var="genere">
                        <p>${genere}</p>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="title-trailer">
            <h3>Trailer</h3>
        </div>
        <div class="trailer">
            <iframe  src="${prodotto.trailer}" title="Grand Theft Auto VI" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
        </div>
    </div>
    <div class="sezione simili">
        <h1>Correlati</h1>
        <jsp:useBean id="prodotti" scope="request" type="java.util.List"/>
        <div class="card-container trending">
            <c:forEach items="${prodotti}" var="correlato">
                <div class="card">
                    <div class="card-content">
                        <div class="card-image-container">
                            <a href="${context}/CardManager?id=${correlato.id}"><img class="card__image" src="${context}${correlato.img}" alt=""></a>
                            <div class="scontato">-${correlato.sconto}%</div>
                        </div>
                        <div class = "card-info">
                            <h3 class="title">${correlato.nome}</h3>
                            <p><fmt:formatNumber value="${correlato.prezzoScontato}" type="number" minFractionDigits="2" maxFractionDigits="2"/>€</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>


    <%@ include file="../fragments/footer.jsp"%>
    <script src="${context}/js/sezioni.js"></script>
    <script src="${context}/js/game.js"></script>
</body>
</html>

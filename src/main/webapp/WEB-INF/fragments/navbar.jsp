<div role="navigation" class="fragment-nav">
    <header>
        <div class="container">
            <div class="content">
                <div class="left">
                    <span role="button" tabindex="0" class="trigger"><i class="ri-menu-line"></i></span>
                    <div class="logo"><a role="link" href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a></div>
                </div>
                <div class="middle">
                    <nav>
                        <ul>
                            <li><a role="link" href="${context}/index.jsp">Home</a></li>
                            <li><a role="link" href="${context}/CategoriaManager?categoria=In-Tendenza">In Tendenza</a></li>
                            <li><a role="link" href="${context}/CategoriaManager?categoria=Preordini">Preordini</a></li>
                            <li><a role="link" href="${context}/CategoriaManager?categoria=Prossime-Uscite">Prossime Uscite</a></li>
                            <li><a role="link" href="#footer">Contatti</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="right">
                    <ul>
                        <li><a href="${context}/CategoriaManager?categoria=search"><i role="button" tabindex="0" id="search"  class="ri-search-line" aria-label="search"></i> </a></li>
                        <li><a role="link" href="${context}/CartManager"><span class="num">
                            <c:choose>
                            <c:when test="${not empty sessionScope.carrello}">
                                <c:out value="${sessionScope.carrello.prodotti.size()}"/>
                            </c:when>
                            <c:otherwise>0</c:otherwise>
                        </c:choose></span><i class="ri-shopping-cart-2-line"></i></a></li>
                        <li>
                            <a role="link" href="${context}/UserManager">
                                <c:if test="${user != null}">
                                    <span class="account-active"></span>
                                </c:if>
                                <i class="ri-user-line"></i>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </header>
    <div class="search-bar-container">
        <form tabindex="0" action="" role="form" class="search-bar">
            <label>
                <input id="searchBar" role="searchbox" tabindex="0" type="search" placeholder="Search..." name="search" aria-label="search">
            </label>
        </form>
    </div>

    <!-- MOBILE -->
    <nav class="mobile-menu">
        <div class="mini">
            <a role="button" href="#" class="mini-close"><i class="ri-close-line"></i></a>
            <div class="menu-head">
                <div class="top">
                    <ul>
                        <li><a role="link" href="${context}/index.jsp">Home</a></li>
                        <li><a role="link" href="${context}/CategoriaManager?categoria=In-Tendenza">In Tendenza</a></li>
                        <li><a role="link" href="${context}/CategoriaManager?categoria=Preordini">Preordini</a></li>
                        <li><a role="link" href="${context}/CategoriaManager?categoria=Prossime-Uscite">Prossime Uscite</a></li>
                        <li><a role="link" href="#footer">Contatti</a></li>
                        <div class="search">
                            <i class="ri-search-line"></i>
                            <form action="CategoriaManager">
                                <label>
                                    <input id="searchBarMobile" type="search" tabindex role="searchbox" placeholder="Search..." name="search" aria-label="search">
                                </label>
                            </form>
                        </div>
                    </ul>
                </div>
                <div class="logo">
                    <a role="link" href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a>
                </div>

            </div>
        </div>
    </nav>
    <div class="overlay"></div>
</div>
<script src="${context}/js/carrello.js"></script>

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
                            <li><a role="link" href="#">In Tendenza</a></li>
                            <li><a role="link" href="#">Preordini</a></li>
                            <li><a role="link" href="#">Prossime Uscite</a></li>
                            <li><a role="link" href="#">Contatti</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="right">
                    <ul>
                        <li><i role="button" tabindex="0" id="search" onclick="showSearchBar()" class="ri-search-line" aria-label="search"></i></li>
                        <li><a role="link" href="#"><span class="num">0</span><i class="ri-shopping-cart-2-line"></i></a></li>
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
        <form action="" class="search-bar">
            <label>
                <input tabindex type="search" role="searchbox" placeholder="Search..." name="search" aria-label="search">
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
                        <li><a role="link" href="#">In Tendenza</a></li>
                        <li><a role="link" href="#">Preordini</a></li>
                        <li><a role="link" href="#">Prossime Uscite</a></li>
                        <li><a role="link" href="#">Contatti</a></li>
                        <div class="search">
                            <i class="ri-search-line"></i>
                            <form action="">
                                <label>
                                    <input type="search" tabindex role="searchbox" placeholder="Search..." name="search" aria-label="search">
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
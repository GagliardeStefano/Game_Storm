<div class="fragment-nav">
    <header>
        <div class="container">
            <div class="content">
                <div class="left">
                    <span class="trigger"><i class="ri-menu-line"></i></span>
                    <div class="logo"><a href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a></div>
                </div>
                <div class="middle">
                    <nav>
                        <ul>
                            <li><a href="${context}/index.jsp">Home</a></li>
                            <li><a href="#">In Tendenza</a></li>
                            <li><a href="#">Preordini</a></li>
                            <li><a href="#">Prossime Uscite</a></li>
                            <li><a href="#">Contatti</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="right">
                    <ul>
                        <li><i id="search" class="ri-search-line"></i></li>
                        <li><a><span class="num">0</span><i class="ri-shopping-cart-2-line"></i></a></li>
                        <li>
                            <a href="${context}/UserManager">
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
            <input type="search" placeholder="Search..." name="search">
        </form>
    </div>

    <!-- MOBILE -->
    <nav class="mobile-menu">
        <div class="mini">
            <a href="#" class="mini-close"><i class="ri-close-line"></i></a>
            <div class="menu-head">
                <div class="top">
                    <ul>
                        <li><a href="#">Home</a></li>
                        <li><a href="#">In Tendenza</a></li>
                        <li><a href="#">Preordini</a></li>
                        <li><a href="#">Prossime Uscite</a></li>
                        <li><a href="#">Contatti</a></li>
                        <div class="search">
                            <i class="ri-search-line"></i>
                            <form action="">
                                <input type="search" placeholder="Search..." name="search" >
                            </form>
                        </div>
                    </ul>
                </div>
                <div class="logo">
                    <a href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="logo"></a>
                </div>

            </div>
        </div>
    </nav>
    <div class="overlay"></div>
</div>
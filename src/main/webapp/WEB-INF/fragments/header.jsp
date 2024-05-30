<%--
  Created by IntelliJ IDEA.
  User: stefa
  Date: 29/05/2024
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<html>
<head></head>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Navbar</title>
    <link rel="stylesheet" href="./css/main.css">
    <link rel="stylesheet" href="./css/styleNavbar.css">
</head>
<body>
    <nav class="navbar">
        <div class="hamburger-menu" id="hamburger-menu">
            <span></span>
            <span></span>
            <span></span>
        </div>
        <div class="logo">
            <a href="index.jsp">
                <img src="./images/logoWhiteNoBackround.png" alt="Logo">
            </a>
        </div>
        <ul class="nav-links">
            <li><a href="#trend">In tendenza</a></li>
            <li><a href="#preorders">Preordini</a></li>
            <li><a href="#upcoming">Prossime Uscite</a></li>
            <li><a href="#about">About Us</a></li>
        </ul>
        <div class="nav-icons">
            <a href="#cart">
                <img alt="cart" src="./images/cart.svg">
            </a>
            <a href="#user">
                <img alt="account" src="./images/account.svg">
            </a>
        </div>
    </nav>

    <script src="./js/navbar.js"></script>
</body>
</html>

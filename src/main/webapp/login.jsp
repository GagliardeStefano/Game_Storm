<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>GS-login</title>

        <!--CSS-->
        <link rel="stylesheet" href="./css/main.css">
        <link rel="stylesheet" href="./css/styleLogin.css">

    </head>
    <body>
        <div class="container-page-login">
            <div class="close-btn"><a href="index.jsp"><i class="ri-close-line"></i></a></div>
            <div class="left-side">
                <img src="images/bgSoulsLogin.png" alt="">
            </div>
            <div class="right-side">
                <div class="logo"><a href="index.jsp"><img src="./images/logoWhiteNoBackround.png" alt="Logo"></a></div>

                <!--FORMS-->
                <div class="login-container">
                    <ul class="login-nav">
                        <li class="login-nav-item active" id="login-tab">
                            <a href="#">Accedi</a>
                        </li>
                        <li class="login-nav-item" id="register-tab">
                            <a href="#">Registrati</a>
                        </li>
                    </ul>

                    <!--LOGIN-->
                    <form action="#" class="form-login" id="login-form">
                        <label for="login-input-user" class="login-label">Email</label>
                        <input id="login-input-user" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                               title="Inserisci un email valida" class="login-input" type="text" required
                        />

                        <label for="login-input-password" class="login-label">Password</label>
                        <input id="login-input-password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                               title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri"
                               class="login-input" type="password" required
                        />

                        <label for="login-sign-up" class="login-label-checkbox">
                            <input id="login-sign-up" type="checkbox" class="login-input-checkbox" />
                            Rimani connesso
                        </label>
                        <button class="login-submit">Accedi</button>

                        <a href="#" class="login-forgot">Password dimenticata?</a>
                    </form>

                    <!--REGISTRAZIONE-->
                    <form action="#" class="form-login" id="register-form" style="display: none;">

                        <div class="input-group">
                            <div class="input-half">
                                <label for="register-input-nome" class="login-label">Nome</label>
                                <input id="register-input-nome" name="Nome" class="login-input" type="text" required/>
                            </div>
                            <div class="input-half">
                                <label for="register-input-cognome" class="login-label">Cognome</label>
                                <input id="register-input-cognome" name="Cognome" class="login-input" type="text" required/>
                            </div>
                        </div>

                        <div class="input-group">
                            <div class="input-half">
                                <label for="register-input-data" class="login-label">Data di nascita</label>
                                <input id="register-input-data" name="Data" class="login-input" type="date" required/>
                            </div>
                            <div class="input-half">
                                <label for="register-input-country" class="login-label">Paese</label>
                                <select id="register-input-country"  name="Country" class="login-input" required>
                                    <option value=""></option>
                                    <option value="US">Stati Uniti</option>
                                    <option value="EU">Europa</option>
                                    <option value="AS">Asia</option>
                                    <option value="UR">Regno Unito</option>
                                </select>
                            </div>
                        </div>

                        <div class="input-group">
                            <div class="input-half">
                                <label for="register-input-user" class="login-label">Email</label>
                                <input id="register-input-user" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                                       title="Inserisci un email valida"
                                       class="login-input" type="text" required
                                />
                            </div>
                            <div class="input-half">
                                <label for="register-input-password" class="login-label">Password</label>
                                <input id="register-input-password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                       title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri"
                                       class="login-input" type="password" required
                                />
                            </div>
                        </div>


                        <button class="login-submit">Registrati</button>
                    </form>

                </div>

            </div>
        </div>
        <script src="./js/login.js"></script>
    </body>
</html>

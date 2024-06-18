<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>GS-login</title>

        <%@ include file="general.jsp"%>

        <link rel="stylesheet" href="${context}/css/styleLogin.css">
    </head>
    <body>

        <% if( request.getAttribute("errori") != null){%>
            <jsp:useBean id="errori" scope="request" type="java.util.List"/>
        <%}%>

        <div class="container-page-login">

            <div class="close-btn"><a href="${context}/index.jsp"><i class="ri-close-line"></i></a></div>
            <div class="left-side">
                <img src="${context}/images/bgSoulsLogin.png" alt="">
            </div>
            <div class="right-side">
                <div class="logo"><a href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a></div>


                <div class="login-container">
                    <ul class="login-nav">
                        <li class="login-nav-item active" id="login-tab">
                            <a href="#">Accedi</a>
                        </li>
                        <li class="login-nav-item" id="register-tab">
                            <a href="#">Registrati</a>
                        </li>
                    </ul>
                    <!--FORMS-->
                    <!--LOGIN-->
                    <form action="${context}/UserManager?t=l" method="post" class="form-login" id="login-form">
                                <label for="login-input-user" class="login-label tooltip">Email</label>
                                <input id="login-input-user" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                                       title="Inserisci un email valida" class="login-input" type="text" name="Email" required
                                />
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, 'email')}">
                                        <span class="error-input">Inserisci un email valida</span>
                                    </c:if>
                                </c:forEach>



                                <label for="login-input-password" class="login-label">Password</label>
                                <input id="login-input-password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                       title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri"
                                       class="login-input" type="password" name="Password" required
                                />
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, '8')}">
                                        <span class="error-input">Deve contenere almeno un numero, una lettera maiuscola e minuscola, almeno 8 o più caratteri<br></span>
                                    </c:if>
                                </c:forEach>


                        <label for="login-sign-up" class="login-label-checkbox">
                            <input id="login-sign-up" type="checkbox" class="login-input-checkbox" />
                            Rimani connesso
                        </label>
                        <button class="login-submit">Accedi</button>
                    </form>

                    <!--REGISTRAZIONE-->
                    <form action="${context}/UserManager?t=r" method="post" class="form-login" id="register-form" style="display: none;">

                        <div class="input-group ">
                            <div class="input-half">
                                <label for="register-input-nome" class="login-label">Nome</label>
                                <input id="register-input-nome" name="Nome" class="login-input" type="text" /><!--required-->
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, ' nome')}">
                                        <span class="error-input">Inserisci un nome</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="input-half">
                                <label for="register-input-cognome" class="login-label">Cognome</label>
                                <input id="register-input-cognome" name="Cognome" class="login-input" type="text" /> <!--required-->
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, 'cognome')}">
                                        <span class="error-input">Inserisci un cognome</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="input-group">
                            <div class="input-half">
                                <label for="register-input-data" class="login-label">Data di nascita</label>
                                <input id="register-input-data"
                                       title="Devi essere almeno maggiorenne"
                                       name="Data" class="login-input" type="date" />
                                <!--pattern="^(19[3-9][4-9]|19[4-9]\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$"
                                required-->

                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, 'maggiorenne')}">
                                        <span class="error-input">Devi essere almeno maggiorenne</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="input-half">
                                <label for="register-input-country" class="login-label">Paese</label>
                                <select id="register-input-country"  name="Country" class="login-input" ><!--required-->
                                    <option value=""></option>
                                    <option value="US">Stati Uniti</option>
                                    <option value="EU">Europa</option>
                                    <option value="AS">Asia</option>
                                    <option value="UR">Regno Unito</option>
                                </select>
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, 'paese')}">
                                        <span class="error-input">Inserisci un paese</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="input-group">
                            <div class="input-half">
                                <label for="register-input-user" class="login-label">Email</label>
                                <input id="register-input-user"
                                       title="Inserisci un email valida"
                                       class="login-input" type="text" name="Email"/>

                                <!-- pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$" required -->
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, 'email')}">
                                        <span class="error-input">Inserisci un email valida</span>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div class="input-half">
                                <label for="register-input-password" class="login-label">Password</label>
                                <input id="register-input-password"
                                       title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri"
                                       class="login-input" type="password" name="Password"
                                />
                                <c:forEach items="${errori}" var="error">
                                    <c:if test="${fn:contains(error, '8')}">
                                        <span class="error-input">Deve contenere almeno un numero, una lettera maiuscola e minuscola, almeno 8 o più caratteri</span>
                                    </c:if>
                                </c:forEach>
                                    <!-- pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required -->
                            </div>
                        </div>


                        <button class="login-submit">Registrati</button>
                    </form>

                </div>

            </div>
        </div>
        <script src="${context}/js/login.js"></script>
    </body>
</html>

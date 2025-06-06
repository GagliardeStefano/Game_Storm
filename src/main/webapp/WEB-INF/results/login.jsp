<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>GS-login</title>
    <%@ include file="general.jsp"%>
    <link rel="stylesheet" href="${context}/css/login.css">
</head>
<body>
<p id="typeReq" style="visibility: hidden"><%= request.getParameter("t") %></p>
<div class="container-page-login">
    <div class="close-btn"><a role="link" href="${context}/index.jsp"><i class="ri-close-line"></i></a></div>
    <div class="left-side">
        <img src="${context}/images/bgSoulsLogin.png" alt="Background">
    </div>
    <div class="right-side">
        <div class="logo"><a role="link" href="${context}/index.jsp"><img src="${context}/images/logoWhiteNoBackround.png" alt="Logo"></a></div>
        <div class="login-container">
            <ul class="login-nav">
                <li class="login-nav-item active" id="login-tab">
                    <a role="button" aria-label="Accedi" onclick="showLogin()" href="#">Accedi</a>
                </li>
                <li class="login-nav-item" id="register-tab">
                    <a role="button" aria-label="Registrati" onclick="showRegister()" href="#">Registrati</a>
                </li>
            </ul>
            <!-- FORMS -->
            <!-- LOGIN -->
            <form action="${context}/UserManagerLogin" method="post" onsubmit="return validateFormLogin()" class="form-login" id="login-form">
                <label for="login-input-user" class="login-label">Email</label>
                <input id="login-input-user" title="Inserisci un email valida" class="login-input" type="email" name="Email" aria-label="Email"/>
                <span id="login-email-error" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, 'email')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </span>

                <label for="login-input-password" class="login-label">Password</label>
                <input id="login-input-password" title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri" class="login-input" type="password" name="Password" aria-label="Password" />
                <span id="login-pass-error" class="error-input">
                        <c:forEach items="${errori}" var="errore">
                            <c:if test="${fn:containsIgnoreCase(errore, '8') || fn:containsIgnoreCase(errore, 'Password')}">
                                ${errore}
                            </c:if>
                        </c:forEach>
                    </span>

                <button type="submit" tabindex class="login-submit">Accedi</button>
            </form>

            <!-- REGISTRAZIONE -->
            <form action="${context}/UserManagerRegister" method="post" onsubmit="return validateFormRegister()" class="form-login" id="register-form" style="display: none;">
                <input type="text" name="t" value="r" hidden />
                <div class="input-group">
                    <div class="input-half">
                        <label for="register-input-nome" class="login-label">Nome</label>
                        <input id="register-input-nome" name="Nome" class="login-input" type="text" aria-label="Nome" autocomplete="given-name"  />
                        <span id="reg-nome-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, ' nome')}">
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                    </div>
                    <div class="input-half">
                        <label for="register-input-cognome" class="login-label">Cognome</label>
                        <input id="register-input-cognome" name="Cognome" class="login-input" type="text" aria-label="Cognome" autocomplete="family-name"  />
                        <span id="reg-cognome-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'cognome')}">
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                    </div>
                </div>

                <div class="input-group">
                    <div class="input-half">
                        <label for="register-input-data" class="login-label">Data di nascita</label>
                        <input id="register-input-data" title="Devi essere almeno maggiorenne" name="Data" class="login-input" type="date" aria-label="Data di nascita"  />
                        <span id="reg-data-error" class="error-input">
                            <c:forEach items="${errori}" var="errore">
                                <c:if test="${fn:containsIgnoreCase(errore, 'data')}">
                                    ${errore}
                                </c:if>
                            </c:forEach>
                        </span>
                    </div>
                    <div class="input-half">
                        <label for="register-input-regione" class="login-label">Regione</label>
                        <select id="register-input-regione" name="Regione" class="login-input" aria-label="Regione" >
                            <option value="" aria-label="Seleziona regione">Seleziona</option>
                            <option value="US" aria-label="America">America</option>
                            <option value="EU" aria-label="Europa">Europa</option>
                            <option value="AS" aria-label="Asia">Asia</option>
                        </select>
                        <span id="reg-regione-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'regione')}">
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                    </div>
                </div>

                <div class="input-group">
                    <div class="input-half">
                        <label for="register-input-user" class="login-label">Email</label>
                        <input id="register-input-user" title="Inserisci un email valida" class="login-input" type="email" name="Email" aria-label="Email" autocomplete="email"  />
                        <span id="reg-email-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, 'email')}">
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                    </div>
                    <div class="input-half">
                        <label for="register-input-password" class="login-label">Password</label>
                        <input id="register-input-password" title="Deve contenere almeno un numero&#13una lettera maiuscola e minuscola&#13almeno 8 o più caratteri" class="login-input" type="password" name="Password" aria-label="Password" autocomplete="new-password"  />
                        <span id="reg-pass-error" class="error-input">
                                <c:forEach items="${errori}" var="errore">
                                    <c:if test="${fn:containsIgnoreCase(errore, '8')}">
                                        ${errore}
                                    </c:if>
                                </c:forEach>
                            </span>
                    </div>
                </div>

                <button type="submit" tabindex class="login-submit">Registrati</button>
            </form>
        </div>
    </div>
</div>
<script src="${context}/js/login.js"></script>
</body>
</html>

const param = document.getElementById('typeReq').textContent;
const errosLogin = document.querySelectorAll("#login-form .error-input");
const errosReg = document.querySelectorAll("#register-form .error-input");
const registerForm = document.getElementById('register-form');
const loginForm = document.getElementById('login-form');
const loginTab = document.getElementById('login-tab');
const registerTab = document.getElementById('register-tab');

if(param === "l"){ //login
    showLogin();
    hiddenErrors(errosReg);
}
else if (param === "r"){ //reg
    showRegister();
    hiddenErrors(errosLogin);
}

loginTab.addEventListener('click', function() {
    showLogin();
});

registerTab.addEventListener('click', function() {
    showRegister();
});

function hiddenErrors(errorElements){
    errorElements.forEach(element => {
        element.style.display = 'none';
    })
}

function showRegister(){
    loginForm.style.display = 'none';
    registerForm.style.display = 'block';
    registerTab.classList.add('active');
    loginTab.classList.remove('active');
}

function showLogin(){
    loginForm.style.display = 'block';
    registerForm.style.display = 'none';
    loginTab.classList.add('active');
    registerTab.classList.remove('active');
}

function validateFormLogin(){

    let hasErrors = [];

    const email = document.getElementById('login-input-user').value;
    const password = document.getElementById('login-input-password').value;

    const errorEmail = document.getElementById('login-email-error');
    const errorPass = document.getElementById('login-pass-error');

    const emailPattern = /[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}/;
    const passwordPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;

    hasErrors.push(checkViewErrors(email, errorEmail, emailPattern ,"L'email è obbligatoria", "Inserisci un email valida"))
    hasErrors.push(checkViewErrors(password, errorPass, passwordPattern ,"La password è obbligatoria", "La password deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri"));

    return !hasErrors.includes(true);


}

function validateFormRegister(){

    let hasErrors = [];

    const NomeCognomeRegionePattern = /.+/;
    const emailPattern = /[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}/;
    const passwordPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
    const dataPattern = /(19[3-9][4-9]|19[4-9]\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])/;

    const nome = document.getElementById('register-input-nome').value;
    const cognome = document.getElementById('register-input-cognome').value;
    const data = document.getElementById('register-input-data').value;
    const regione = document.getElementById('register-input-regione').value;
    const email = document.getElementById('register-input-user').value;
    const password = document.getElementById('register-input-password').value;

    const errorNome = document.getElementById('reg-nome-error');
    const errorCognome = document.getElementById('reg-cognome-error');
    const errorData = document.getElementById('reg-data-error');
    const errorRegione = document.getElementById('reg-regione-error');
    const errorEmail = document.getElementById('reg-email-error');
    const errorPass = document.getElementById('reg-pass-error');

    hasErrors.push(checkViewErrors(nome, errorNome, NomeCognomeRegionePattern, "Il nome è obbligatorio", "Inserisci un nome"));
    hasErrors.push(checkViewErrors(cognome, errorCognome, NomeCognomeRegionePattern, "Il cognome è obbligatorio", "Inserisci un cognome"));
    hasErrors.push(checkViewErrors(data, errorData, dataPattern, "Inserisci data di nascita", "Devi essere almeno maggiorenne"));
    hasErrors.push(checkViewErrors(regione, errorRegione, NomeCognomeRegionePattern, "Seleziona almeno una regione", "Seleziona almeno un paese"));
    hasErrors.push(checkViewErrors(email, errorEmail, emailPattern, "L'email è obbligatoria", "Inserisci un email valida"));
    hasErrors.push(checkViewErrors(password, errorPass, passwordPattern, "La password è obbligatoria", "La password deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri"));


    return !hasErrors.includes(true);

}

function checkViewErrors(value, errorElement, pattern, mexRequired, mexErrorPattern){

    if (value === ""){
        errorElement.innerHTML = mexRequired;
        errorElement.style.display = 'inline';
        return true;
    }else if(pattern.test(value) === false){
        errorElement.innerHTML = mexErrorPattern;
        errorElement.style.display = 'inline';
        return true;
    }else {
        errorElement.style.display = 'none';
        return false;
    }
}
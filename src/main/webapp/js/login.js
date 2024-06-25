const param = document.getElementById('typeReq').textContent;
const errosLogin = document.querySelectorAll("#login-form .error-input");
const errosReg = document.querySelectorAll("#register-form .error-input");

if(param === "l"){ //login
    showLogin();
    hiddenErrors(errosReg);
}
else if (param === "r"){ //reg
    showRegister();
    hiddenErrors(errosLogin);
}

document.getElementById('login-tab').addEventListener('click', function() {
    showLogin();
});

document.getElementById('register-tab').addEventListener('click', function() {
    showRegister();
});

function hiddenErrors(errorElements){
    errorElements.forEach(element => {
        element.style.display = 'none';
    })
}

function showRegister(){
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('register-form').style.display = 'block';
    document.getElementById('register-tab').classList.add('active');
    document.getElementById('login-tab').classList.remove('active');
}

function showLogin(){
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('register-form').style.display = 'none';
    document.getElementById('login-tab').classList.add('active');
    document.getElementById('register-tab').classList.remove('active');
}
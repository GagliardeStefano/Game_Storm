const url = new URLSearchParams(window.location.search);
const param = url.get("t");
if(param === "l"){ //login
    console.log(window.location.search);
    showLogin();
}else if (param === "r"){
    console.log(window.location.search);
    showRegister();
}

document.getElementById('login-tab').addEventListener('click', function() {
    showLogin();
});

document.getElementById('register-tab').addEventListener('click', function() {
    showRegister();
});

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
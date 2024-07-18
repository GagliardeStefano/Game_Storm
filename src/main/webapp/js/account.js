
    const wishlist = document.getElementById('scelta-wishlist');
    const ordiniEffettuati = document.getElementById('scelta-ordini');
    const metodiPagamento = document.getElementById('scelta-pagamento');
    const modificaDati = document.getElementById('scelta-modifica');

    const options = [wishlist, ordiniEffettuati, metodiPagamento, modificaDati];

    const sectionWishlist = document.getElementById('wishlist');
    const sectionOrdini = document.getElementById('ordini-effettuati');
    const sectionMetPagamento = document.getElementById('metodi-pagamento');
    const sectionModifica = document.getElementById('modifica-dati');

    showSection(sectionWishlist);
    hiddenSection(sectionOrdini);
    hiddenSection(sectionMetPagamento);
    hiddenSection(sectionModifica);

    wishlist.childNodes[0].addEventListener('click', function(){
        wishlist.classList.add('active');
        deactivateOtherElements(wishlist);

        showSection(sectionWishlist);
        hiddenSection(sectionOrdini);
        hiddenSection(sectionMetPagamento);
        hiddenSection(sectionModifica);
    });

    ordiniEffettuati.childNodes[0].addEventListener('click', function(){

        ordiniEffettuati.classList.add('active');
        deactivateOtherElements(ordiniEffettuati);

        showSection(sectionOrdini);
        hiddenSection(sectionWishlist);
        hiddenSection(sectionMetPagamento);
        hiddenSection(sectionModifica);

    });

    metodiPagamento.childNodes[0].addEventListener('click', function(){
        metodiPagamento.classList.add('active');
        deactivateOtherElements(metodiPagamento);

        showSection(sectionMetPagamento);
        hiddenSection(sectionWishlist);
        hiddenSection(sectionOrdini);
        hiddenSection(sectionModifica);
    });

    modificaDati.childNodes[0].addEventListener('click', function(){
        modificaDati.classList.add('active');
        deactivateOtherElements(modificaDati);

        showSection(sectionModifica);
        hiddenSection(sectionWishlist);
        hiddenSection(sectionOrdini);
        hiddenSection(sectionMetPagamento);
    });

    function showSection(section){
        section.style.display = 'flex';
    }

    function hiddenSection(section){
        section.style.display = 'none';
    }

    function deactivateOtherElements(activatedElement){
        options.forEach(option => {
            if (option !== activatedElement){
                if (option.classList.contains('active')){
                    option.classList.remove('active');
                }
            }
        });
    }

    function changeAvatar(){
        const accountAvatar = document.getElementById("account-avatar");
        const avatarSelection = document.getElementById("avatar-selection");


        avatarSelection.style.display = avatarSelection.style.display === "block" ? "none" : "block";


        const avatarOptions = document.querySelectorAll(".avatar-option");

        avatarOptions.forEach(option => {
            option.addEventListener("click", function() {

                const newAvatar = option.getAttribute("data-avatar");
                let srcAvatar = `/GameStorm_war/images/avatar/${newAvatar}`;


                let xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState === 4 && this.status === 200) {

                        accountAvatar.src = srcAvatar;
                        avatarSelection.style.display = "none";

                    }
                };

                xhttp.open("POST", "UpdateUser", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("from=changeAvatar&path=" + encodeURIComponent(srcAvatar.replace("/GameStorm_war", "")));
            });
        });
    }

    /*-- WISHLIST --*/
        const deleteButtons = document.querySelectorAll('.ri-delete-bin-5-line');
        const addButtons = document.querySelectorAll('.ri-shopping-cart-2-line');
        const addAll = document.getElementById('add-all');

        function deleteFromDb(id, element){

            const xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function() {
                if (this.readyState === 4 && this.status === 200) {
                    closeCard(element);
                }else if (this.readyState === 4) {
                    window.alert('Errore nel rimuovere il gioco dai preferiti');
                }
            };

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("from=wishlist&IdProd=" + encodeURIComponent(id));

        }

        function deleteAllFromDb(){

            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState === 4 && this.status === 200) {
                    closeAllCard();
                }else if (this.readyState === 4) {
                    window.alert('Errore nel rimuovere i giochi dai preferiti');
                }
            };

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("from=wishlist&IdProd=all");
        }

        function closeCard(element){
            let card = element.closest('.container-card');
            card.style.transition = 'opacity 0.5s';
            card.style.opacity = '0';
            setTimeout(() => card.remove(), 500);
        }

        function closeAllCard(){
            deleteButtons.forEach(button => {
                const card = button.closest('.container-card');
                card.style.transition = 'opacity 0.5s';
                card.style.opacity = '0';
                setTimeout(() => card.remove(), 500);

            })
        }

        function addAllToCart(){
            addAll.addEventListener('click', function(){
                addButtons.forEach(button => {
                    /*TODO aggiunta nel carrello*/
                });
            });
        }


    /*-- ORDINI EFFETTUATI --*/
    function ShowMoreGames(button){

        let container = button.closest('.container-games');

        button.style.display = 'none';

        container.querySelectorAll('.hidden').forEach(function(hiddenElement) {
            hiddenElement.style.display = 'flex';
        });

        const mostraMeno = container.nextElementSibling;
        mostraMeno.style.display = 'block';

        let orderId = button.getAttribute('data-order-id');
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                if (xhttp.responseText){

                    let response = JSON.parse(xhttp.responseText);

                    printOtherGames(container, response);
                    addCopyEventAtKeys();
                }
            }
        }

        xhttp.open('GET', 'UpdateUser?from=ordini&orderId='+orderId, true);
        xhttp.setRequestHeader('Content-type', 'application/json');
        xhttp.send();

    }

    function printOtherGames(container, games){

        games.forEach(game => {

            var newGame =`
                    <a href="/GameStorm_war/CardManager?id=${game.idProd}"><img src="/GameStorm_war${game.img}" alt='locandina' /></a>
                    <div class='info'>
                        <h4 class='titolo'>${game.nome}</h4>
                        <div class='key'>
                            <p>key: ${game.keyProd}</p>
                            <i class='ri-file-copy-2-line' title='copia'></i>
                        </div>
                        <p class='prezzo'>${game.prezzo}€</p>
                    </div>`

            let gameDiv = document.createElement("div");
            gameDiv.classList.add("game");
            gameDiv.innerHTML = newGame;

            container.appendChild(gameDiv);

        })
    }

    function addCopyEventAtKeys(){
        const copiaKey = document.querySelectorAll(".ri-file-copy-2-line");
        copiaKey.forEach(element => {
            element.addEventListener('click', function() {

                let text = element.previousElementSibling.textContent.replace(" ","").split(":")[1];
                CopyKeyOnClipboard(text, element);
            })
        });
    }

    function CopyKeyOnClipboard(text, icon){
        navigator.clipboard.writeText(text).then(function(){

            icon.classList.remove('ri-file-copy-2-line');
            icon.classList.add('ri-check-line');
            icon.style.color = 'lime';

            setTimeout(() => {
                icon.classList.remove('ri-check-line');
                icon.classList.add('ri-file-copy-2-line');
                icon.style.color = 'unset';

            }, 3000);

        });
    }

    ordiniEffettuati.childNodes[0].addEventListener('click', function(){

        const lessButtons = document.querySelectorAll('.mostra-meno');

        addCopyEventAtKeys();

        lessButtons.forEach(function(button) {
            button.addEventListener('click', function() {

                showLess(button);

            });
        });
    });

    function showLess(button){
        button.style.display = 'none';

        const container = button.previousElementSibling;

        container.querySelectorAll('.hidden').forEach(function(hiddenElement) {
            hiddenElement.style.display = 'none';
        });

        const more = container.querySelector('.game.more');
        more.style.display = 'flex';

        let otherGameSibling = more.nextElementSibling;

        while (otherGameSibling) {
            if (otherGameSibling.classList.contains('game')) {
                otherGameSibling.remove();
            }
            otherGameSibling = otherGameSibling.nextElementSibling;
        }
    }

    /*-- METODI DI PAGAMENTO --*/

    function deleteCarta(id){
        const xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                let card = document.getElementById('carta'+id);
                card.style.transition = 'opacity 0.5s';
                card.style.opacity = '0';
                setTimeout(() => card.remove(), 500);
            }
        };

        xhttp.open("POST", "UpdateUser", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("from=metodiRem&IdCarta=" + encodeURIComponent(id));
    }

    function modificaCarta(id){

        const form = document.getElementById(id);

        const data = form.previousElementSibling;
        const mainDAti = data.previousElementSibling;

        mainDAti.style.display = 'none';
        data.style.display = 'none';
        form.classList.remove('hidden');


        let dataInput = form.querySelector('#data'+id);
        dataInput.addEventListener('input', function(event){

            let input = event.target;
            let value = input.value.replace(/\D/g, ''); // Rimuove tutti i caratteri non numerici
            let formattedValue = '';

            if (value.length === 0) {
                formattedValue = '';
            } else if (value.length < 2) {
                formattedValue = value;
            } else {
                formattedValue = value.substring(0, 2) + '/' + value.substring(2, 4);
            }

            input.value = formattedValue;

        })

        let numeroCarta = form.querySelector('#numero'+id);
        numeroCarta.addEventListener('input', function(event){

            let input = event.target;
            let value = input.value.replace(/\D/g, ''); // Rimuove tutti i caratteri non numerici
            let formattedValue = '';

            for (let i = 0; i < value.length; i += 4) {
                if (i > 0) {
                    formattedValue += ' ';
                }
                formattedValue += value.substring(i, i + 4);
            }

            if (formattedValue.length > 19) {
                formattedValue = formattedValue.substring(0, 19); // Limita a 19 caratteri (16 cifre + 3 spazi)
            }

            input.value = formattedValue;

        });

        let cvv = form.querySelector('#cvv'+id);
        cvv.addEventListener('input', function(event){

            let input = event.target;
            let value = input.value.replace(/\D/g, '');
            let formattedValue = '';

            if (value.length === 0){
                formattedValue = '';
            }else if (value.length <= 3) {
                formattedValue = value;
            }else{
                formattedValue = value.substring(0, 3);
            }

            input.value = formattedValue;

        });
    }

    function checkValueAndSubmit(event, id){

        let form = document.getElementById(id);

        event.preventDefault();

        if (validateFormInputCard(form, id)){

            const formData = new FormData(form);
            formData.append("from", "metodi");
            formData.append("IdCarta", id);

            let params= "";
            formData.forEach((value, key) => {
                params += key+"="+value+"&";
            })


            const xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function() {
                if (xhttp.readyState === 4 && xhttp.status === 200) {

                    const data = form.previousElementSibling;
                    const mainDAti = data.previousElementSibling;

                    mainDAti.querySelector('#info-numero').innerHTML = "Termina con .... "+formData.get("numero").split(' ')[3];
                    data.querySelector('#info-data').innerHTML = "Data di scadenza: "+formData.get("data");

                    form.classList.add('hidden');
                    data.style.display = 'flex';
                    mainDAti.style.display = 'flex';
                }
            }

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(params);

        }
    }

    function validateFormInputCard(form, id){

        const numero = form.querySelector('#numero'+id);
        let errorNumero = form.querySelector('#error-numero');

        const data = form.querySelector('#data'+id);
        let errorData = form.querySelector('#error-data');

        const cvv = form.querySelector('#cvv'+id);
        let errorCvv = form.querySelector('#error-cvv');

        const nome = form.querySelector('#nome'+id);
        let errorNome = form.querySelector('#error-nome');

        const cognome = form.querySelector('#cognome'+id);
        let errorCognome = form.querySelector('#error-cognome');


        let hasErrors = [];

        // Validazione del titolare
        hasErrors.push(checkErrorPattern(nome, errorNome, /^[a-zA-Z\s]+$/, "Il nome del titolare deve contenere solo lettere e spazi."))
        hasErrors.push(checkErrorPattern(cognome, errorCognome, /^[a-zA-Z\s]+$/, "Il cognome del titolare deve contenere solo lettere e spazi."))

        // Validazione numero di carta
        hasErrors.push(checkErrorPattern(numero, errorNumero, /^\d{4} \d{4} \d{4} \d{4}$/, "Il numero della carta deve essere composto da 16 cifre."))

        // Validazione data di scadenza
        hasErrors.push(checkErrorPattern(data, errorData, /^(0[1-9]|1[0-2])\/\d{2}$/, "La data di scadenza deve essere nel formato MM/AA."))

        // Controllo se la data è nel passato
        hasErrors.push(checkErrorDataPassata(data, errorData, "La data di scadenza non può essere nel passato."));

        // Validazione CVV
        hasErrors.push(checkErrorPattern(cvv, errorCvv, /^\d{3}$/, "Il CVV deve essere composto da 3 cifre."))


        return !hasErrors.includes(true);

    }

    function checkErrorPattern(inputElement, errorElement, pattern, mexError){

        if (!pattern.test(inputElement.value)) {
            errorElement.innerHTML = mexError;
            return true;
        }else if (inputElement.value.trim().length <= 0){
            errorElement.innerHTML = mexError;
            return true;
        }else {
            errorElement.innerHTML = "";
            return false;
        }
    }

    function checkErrorDataPassata(data, errorElement, mexError){

        const today = new Date();
        const month = parseInt(data.value.split('/')[0], 10);
        const year = parseInt('20' + data.value.split('/')[1], 10);
        const expiry = new Date(year, month - 1);
        if (expiry < today) {
            errorElement.innerHTML = mexError;
            return true
        }else {
            return false;
        }

    }


    /*-- MODIFICA DATI --*/
    function validateUpdateForm(event, form){

        event.preventDefault();

        const dati = new FormData(form);
        dati.append("from", "modifica");

        let params= "";
        dati.forEach((value, key) => {
            params += key+"="+value+"&";
        })

        let nome = form.querySelector('#nome-utente');
        let cognome = form.querySelector('#cognome-utente');
        let email = form.querySelector('#email-utente');
        let pass = form.querySelector('#pass-utente');
        let regione = form.querySelector('#regione-utente');
        let data = form.querySelector('#data-utente');

        let success = form.querySelector('.success');

        if (validate() === true){


            let xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState === 4 && this.status === 200) {

                    nome.value = dati.get("nome");
                    cognome.value = dati.get("cognome");
                    email.value = dati.get("email");
                    pass.value = '';
                    regione.value = dati.get("regione");
                    data.value = dati.get("data");

                    form.querySelector('.input[type="submit"]').style.display = 'none';

                    success.style.display = 'block';
                    setTimeout(() => {
                        success.style.display = 'none';
                    }, 3000);


                    form.classList.add('disabled');
                }
            };

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(params);

        }
    }

    function validate(){
        let hasErrors = [];

        const NomeCognomeRegionePattern = /.+/;
        const emailPattern = /[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}/;
        const passwordPattern = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
        const dataPattern = /(19[3-9][4-9]|19[4-9]\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])/;

        const nome = document.getElementById('nome-utente');
        const cognome = document.getElementById('cognome-utente');
        const data = document.getElementById('data-utente');
        const regione = document.getElementById('regione-utente');
        const email = document.getElementById('email-utente');
        const password = document.getElementById('pass-utente');

        const errorNome = document.getElementById('nome-utente-error');
        const errorCognome = document.getElementById('cognome-utente-error');
        const errorData = document.getElementById('data-utente-error');
        const errorRegione = document.getElementById('regione-utente-error');
        const errorEmail = document.getElementById('email-utente-error');
        const errorPass = document.getElementById('pass-utente-error');

        hasErrors.push(checkErrorPattern(nome, errorNome, NomeCognomeRegionePattern, "Inserisci un nome"));
        hasErrors.push(checkErrorPattern(cognome, errorCognome, NomeCognomeRegionePattern, "Inserisci un cognome"));
        hasErrors.push(checkErrorPattern(data, errorData, dataPattern, "Devi essere almeno maggiorenne"));
        hasErrors.push(checkErrorPattern(regione, errorRegione ,NomeCognomeRegionePattern, "Inserisci una regione"));
        hasErrors.push(checkErrorPattern(email, errorEmail, emailPattern, "Inserisci un email valida"));

        if (password.value.trim().length > 0){
            hasErrors.push(checkErrorPattern(password, errorPass, passwordPattern, "La password deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri"));
        }

        return !hasErrors.includes(true);

    }

    function abilitaModifica(button){

        let form = button.nextElementSibling;

        const inputs = form.querySelectorAll('.input');
        const submitButton = form.querySelector('.input[type="submit"]');

        form.classList.remove('disabled');

        inputs.forEach(input => {
            input.disabled = false;
        });

        submitButton.style.display = 'inline-block';
        submitButton.disabled = false;

    }

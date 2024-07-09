
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

    wishlist.addEventListener('click', function(){
        this.classList.add('active');
        deactivateOtherElements(this);

        showSection(sectionWishlist);
        hiddenSection(sectionOrdini);
        hiddenSection(sectionMetPagamento);
        hiddenSection(sectionModifica);
    });

    ordiniEffettuati.addEventListener('click', function(){
        this.classList.add('active');
        deactivateOtherElements(this);

        showSection(sectionOrdini);
        hiddenSection(sectionWishlist);
        hiddenSection(sectionMetPagamento);
        hiddenSection(sectionModifica);

    });

    metodiPagamento.addEventListener('click', function(){
        this.classList.add('active');
        deactivateOtherElements(this);

        showSection(sectionMetPagamento);
        hiddenSection(sectionWishlist);
        hiddenSection(sectionOrdini);
        hiddenSection(sectionModifica);
    });

    modificaDati.addEventListener('click', function(){
        this.classList.add('active');
        deactivateOtherElements(this);

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

        addAll.addEventListener('click', function(){
            addButtons.forEach(button => {
                /*TODO aggiunta nel carrello*/
            });
        });

    /*-- ORDINI EFFETTUATI --*/
    function ShowMoreGames(button){

        let container = button.closest('.container-games');
        console.log(container);
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

        xhttp.open('GET', 'UpdateUser?from=ordini&orderId='+orderId ,true);
        xhttp.setRequestHeader('Content-type', 'application/json');
        xhttp.send();


    }

    function printOtherGames(container, games){

        games.forEach(game => {

            var newGame =`
                    <a href="${game.idProd}"><img src="/GameStorm_war${game.img}" alt='locandina' /></a>
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
                navigator.clipboard.writeText(text).then(function(){

                    element.classList.remove('ri-file-copy-2-line');
                    element.classList.add('ri-check-line');
                    element.style.color = 'lime';

                    setTimeout(() => {
                        element.classList.remove('ri-check-line');
                        element.classList.add('ri-file-copy-2-line');
                        element.style.color = 'unset';

                    }, 3000);

                });
            })
        });
    }

    ordiniEffettuati.addEventListener('click', function(){

        const lessButtons = document.querySelectorAll('.mostra-meno');

        addCopyEventAtKeys();

        lessButtons.forEach(function(button) {
            button.addEventListener('click', function() {

                this.style.display = 'none';

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

            });
        });
    });


    /*-- METODI DI PAGAMENTO --*/

    function modificaCarta(id){

        const form = document.getElementById(id);

        const data = form.previousElementSibling;
        const mainDAti = data.previousElementSibling;

        mainDAti.style.display = 'none';
        data.style.display = 'none';
        form.classList.remove('hidden');


        let dataInput = form.querySelector('#data');
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

        let numeroCarta = form.querySelector('#numero');
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

        let cvv = form.querySelector('#cvv');
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

        if (validateFormInputCard(form)){

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

    function validateFormInputCard(form){



        const numero = form.querySelector('#numero');
        let errorNumero = form.querySelector('#error-numero');

        const data = form.querySelector('#data');
        let errorData = form.querySelector('#error-data');

        const cvv = form.querySelector('#cvv');
        let errorCvv = form.querySelector('#error-cvv');

        const nome = form.querySelector('#nome');
        let errorNome = form.querySelector('#error-nome');

        const cognome = form.querySelector('#cognome');
        let errorCognome = form.querySelector('#error-cognome');


        let hasErrors = [];

        // Validazione nome del titolare
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
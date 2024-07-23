let table = document.getElementById('table');
let dashboard = document.getElementById('dashboard');
const searchBar = document.getElementById('searchBar');
let addRecordButton = document.getElementById('add-record');
let deleteRecordButton = document.getElementById('delete-record');

let allForm = document.getElementById('form-table-action').querySelectorAll('#form-table-action > div');
let typeReq = document.getElementById('type').innerHTML;
let tabellaScelta;
let dropdownContainer = document.querySelector('.dropdown-container');

let formDelete = document.getElementById('form-delete');

hiddenTable();

switch (typeReq){
    case "addProdotto":
        hiddenDashboard();
        hiddenTable();
        tabellaScelta = "prodotti";
        showFormById('addProdotto', true);
        break;
    case "addUser":
        hiddenDashboard();
        hiddenTable();
        tabellaScelta = "utente";
        showFormById('addUser', true);
        break;
    case "addGenere":
        hiddenDashboard();
        hiddenTable();
        tabellaScelta = "genere";
        showFormById('addGenere', true);
        break;
    case "deleteProd":

        hiddenDashboard();
        tabellaScelta = "prodotti";
        getTable("Prodotti");
        eliminaEntita()
        break;
    case "deleteUser":

        hiddenDashboard();
        tabellaScelta = "utente";
        getTable("Utenti");
        eliminaEntita()
        break;
    case "deleteGenere":

        hiddenDashboard();
        tabellaScelta = "genere";
        getTable("Generi");
        eliminaEntita()
        break;
    default:break;
}


// Funzione per mostrare/nascondere il dropdown-container
function displayContainer() {

    // Verifica lo stato attuale del dropdown-container
    if (dropdownContainer.style.display === 'block') {
        dropdownContainer.style.display = 'none'; // Nascondi il dropdown-container se è visibile
    } else {
        dropdownContainer.style.display = 'block'; // Mostra il dropdown-container se è nascosto
    }
}

function hiddenForms(){
    document.getElementById('form-table-action').style.display = 'none';
}

function showForms(){
    document.getElementById('form-table-action').style.display = 'block';
}

function hiddenButtons(){
    addRecordButton.style.display = 'none';
    deleteRecordButton.style.display = 'none';
}

function showButtons(){
    addRecordButton.style.display = 'block';
    deleteRecordButton.style.display = 'block';
}

// Aggiungi un evento di click a tutti gli elementi con classe 'action'
var actionElements = document.querySelectorAll('.action');
actionElements.forEach(function(element) {
    element.addEventListener('click', function() {
        // Rimuovi la classe 'active' da tutti gli elementi 'action'
        actionElements.forEach(function(el) {
            el.classList.remove('active');
        });

        // Aggiungi la classe 'active' all'elemento cliccato
        element.classList.add('active');
    });
});

function displayDashboard(){
    dashboard.style.display = 'flex';
    hiddenTable();
    hiddenForms();
}

function hiddenDashboard(){
    dashboard.style.display = 'none';
}

function displayTable(){
    table.style.display = 'block';
    hiddenDashboard();
    hiddenForms();
}

function hiddenTable(){
    table.style.display = 'none';
}

function getTable(element){

    switch (element){
        case 'Prodotti':
            tabellaScelta = "prodotti";
            break;
        case 'Generi':
            tabellaScelta = "genere";
            break;
        case 'Utenti':
            tabellaScelta = "utente";
            break;
        case 'Carrelli':
            tabellaScelta = "carrello";
            break;
        case 'Ordini':
        case 'Ordini effettuati':
            tabellaScelta = "ordini";
            break;
    }

    if (tabellaScelta === "prodotti" || tabellaScelta === "genere" || tabellaScelta === "utente"){
        showButtons();
    }else {
        hiddenButtons();
    }

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(xhttp.responseText);
            printTable(response);
            displayTable();
        }
    };

    xhttp.open("POST", "AdminManager", false);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("from=showTable&tabella="+tabellaScelta);

}


function printTable(response) {
    if(document.getElementById('form-delete')){
        document.getElementById('form-delete').style.display = 'none';
    }

    let tableBody = document.getElementById('tableBody');
    let tableHead = document.getElementById('tableHead');
    let nomeTabella = document.getElementById('nomeTabella');

    nomeTabella.innerText = tabellaScelta;


    if (response.records != null) {
        tableHead.innerHTML = '';
        const columns = Object.keys(response.records[0]);

        const headerRow = tableHead.insertRow();
        columns.forEach(columnName => {
            const th = document.createElement('th');
            th.textContent = columnName;
            headerRow.appendChild(th);
        });

        tableBody.innerHTML = '';

        response.records.forEach(record => {
            const row = tableBody.insertRow();
            Object.entries(record).forEach(([key, value]) => {
                const cell = row.insertCell();

                // Verifica se il valore è una stringa che contiene tag <img>
                if (typeof value === 'string' && value.startsWith('<img')) {

                    // Controlla la chiave associata al valore
                    if (key === 'immagini_giochi') {
                        cell.classList.add('img-listgames');

                    }else if(key === 'immagine' || key === 'foto') {

                        if (value.includes('avatar')) {
                            cell.classList.add('img-avatar');
                        } else {
                            cell.classList.add('img-game');
                        }
                    }

                    cell.innerHTML = value; // Inserisce il contenuto HTML nella cella

                }else{
                    switch (key){
                        case "sconto":
                            cell.innerHTML = value+"%";
                            break;

                        case "prezzo":
                        case "totale":
                            cell.innerHTML = value+"€";
                            break;

                        case "prezzi_giochi":
                            let values1 = value.split(" / ");
                            values1.forEach((subValue, index) => {
                                if (index === values1.length - 1) {
                                    cell.innerHTML += subValue + "€"; // Aggiunge € all'ultimo elemento
                                } else {
                                    cell.innerHTML += subValue + "€ / ";
                                }
                            });
                            break;

                        case "sconti_giochi":
                            let values2 = value.split(" / ");
                            values2.forEach((subValue, index) => {
                                if (index === values2.length - 1) {
                                    cell.innerHTML += subValue + "%"; // Aggiunge % all'ultimo elemento
                                } else {
                                    cell.innerHTML += subValue + "% / ";
                                }
                            });
                            break;

                        default: cell.innerHTML = value; break;
                    }
                }

            });
        });
    }else{
        document.getElementById('tableBody').innerHTML = '';
    }
}



/*FORMS*/
function showFormById(id, from){
    allForm.forEach(form => {
        form.style.display = 'none';
    })

    let container = document.getElementById(id);
    container.querySelectorAll('.error-input').forEach(element => {
        element.innerHTML = "";
    });

    container.style.display = 'flex';

    if (from === false){
        container.querySelector('.mex-general').style.display = 'none';
    }

}

function aggiungiEntita(){
    table.style.display = 'none';
    showForms();
    switch (tabellaScelta){
        case "prodotti":
            showFormById('addProdotto', false);
            break;
        case "utente":
            showFormById('addUser', false);
            break;
        case "genere":
            showFormById('addGenere', false);
            break;

    }
}

function eliminaEntita() {

    if(formDelete.style.display === 'flex'){
        formDelete.style.display = 'none';
    }else {
        formDelete.style.display = 'flex';
    }


    let hiddenInput  = document.getElementById('hidden-delete');
    hiddenInput.value = "delete";
    let Input = document.getElementById('delete-input');
    Input.value = "";


    // Definire le proprietà del nuovo input in base alla tabella selezionata
    switch (tabellaScelta) {
        case "prodotti":
            hiddenInput.value += "Prod";
            Input.placeholder = 'Inserisci nome del prodotto';
            Input.title = 'Inserisci il nome del record che vuoi eliminare';
            break;
        case "utente":
            hiddenInput.value += "User";
            Input.placeholder = 'Inserisci email utente';
            Input.title = 'Inserisci email';
            break;
        case "genere":
            hiddenInput.value += "Genere";
            Input.placeholder = 'Inserisci nome del genere';
            Input.title = 'Inserisci il nome del record che vuoi eliminare';
            break;
        default:
            return;
    }


}






searchBar.addEventListener('keyup', function(event){

    const query = searchBar.value.trim();

    if (event.key === 'Escape') {
        searchBar.value = '';
        document.getElementById('tableBody').innerHTML = '';
    }

    if (query){
        performSearch(query);
    }else {
        document.getElementById('tableBody').innerHTML = '';
    }

});

function performSearch(query) {
    const xhr = new XMLHttpRequest();


    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            printTable(data);
        }
    };

    xhr.open('GET', `search?q=${encodeURIComponent(query)}&t=`+encodeURIComponent(tabellaScelta), true);
    xhr.onerror = function() {
        console.error('Error:', xhr.statusText);
    };
    xhr.send();
}

function checkFormAdmin(form) {

    document.getElementById('mexProd-general').innerHTML = "";
    let inputs = new FormData(form);
    return NoErrorForm(form, inputs) === true;

}

function NoErrorForm(form, inputs){

    let hasErrors = [];

    inputs.forEach((value, key) => {

        let inputElement = form.querySelector(`[name="${key}"]`);

        if (key !== "from"){
            if (inputElement) {

                let errorElement = inputElement.nextElementSibling;
                errorElement.innerHTML = "";
                let inputType = inputElement.type;

                if(tabellaScelta === "prodotti"){

                    if(!validateGeneri(form)){
                        hasErrors.push(true);
                        document.getElementById("error-generi").innerHTML = "Selezionane almeno un genere";
                    }else {
                        document.getElementById("error-generi").innerHTML = "";
                    }

                }
                else if (tabellaScelta === "genere"){

                    if (!validateCheckGames(form)){
                        hasErrors.push(true);
                        document.getElementById("error-giochi-selezionati").innerHTML = "Selezionane almeno un gioco";
                    }else {
                        document.getElementById("error-giochi-selezionati").innerHTML = "";
                    }

                } else if(tabellaScelta === "utente"){
                    if (!validateTipoUser(form)){
                        hasErrors.push(true);
                        document.getElementById("error-tipo-user").innerHTML = "Selezionane un solo tipo";
                    }else {
                        document.getElementById("error-tipo-user").innerHTML = "";
                    }
                }


                if (key === "prezzo" || key === "sconto"){

                    if (!validateNumber(value)){
                        hasErrors.push(true);
                        errorElement.innerHTML = "Inserisci un numero";
                    }else {
                        errorElement.innerHTML = "";
                    }

                }
                else if ( (inputType === 'text' || inputType === 'textarea') && !validateText(value)) {

                    hasErrors.push(true);
                    errorElement.innerHTML = "Inserisci un valore";

                }
                else if (inputType === 'date') {

                    if(key === "dataNascita" && !validateDataNascita(value)){
                        hasErrors.push(true);
                        errorElement.innerHTML = "Deve essere almeno maggiorenne";

                    }else if (key === "dataRilascio" && !validateDateGame(value)){
                        hasErrors.push(true);
                        errorElement.innerHTML = "Inserisci una data valida";
                    }else {
                        errorElement.innerHTML = "";
                    }

                }
                else if (inputType === 'password' && !validatePassword(value)) {
                    hasErrors.push(true);
                    errorElement.innerHTML = "La password deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri";
                }
                else if (inputType === 'email' && !validateEmail(value)) {
                    hasErrors.push(true);
                    errorElement.innerHTML = "Inserisci un email valida";
                }
                else if (inputType === 'url' && !validateUrl(value)) {
                    hasErrors.push(true);
                    errorElement.innerHTML = "Inserisci un url valido";
                }
                else if(key === 'regione' && inputElement.value === ""){
                    hasErrors.push(true);
                    errorElement.innerHTML = "Fai una scelta";
                }
                else {
                    errorElement.innerHTML = "";
                }

            }
        }

    });

    return !hasErrors.includes(true);

}

function validateDataNascita(data){
    const re = /(19[3-9][4-9]|19[4-9]\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])/;
    return re.test(data);
}

function validateEmail(email) {
    const re = /[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}/;
    return re.test(email);
}

function validatePassword(password){
    const re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
    return re.test(password);
}

function validateText(text){
    const re = /.+/;
    return re.test(text);
}

function validateNumber(number){
    const re = /^\d+(\.\d+)?$/
    return re.test(number);
}

function validateDateGame(date){
    const re = /(19|20)\d\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])/;
    return re.test(date);
}

function validateGeneri(form) {
    const checkboxes = form.querySelectorAll('input[name="genere"]:checked');
    return checkboxes.length > 0;
}

function validateCheckGames(form){
    const checkboxes = form.querySelectorAll('input[name="listGames"]:checked');
    return checkboxes.length > 0;
}

function validateTipoUser(form){
    const radioButtons = form.querySelectorAll('input[name="tipo"]:checked');
    return radioButtons.length === 1;
}

function validateUrl(url) {
    const urlPattern = /(http|https):\/\/[^\s\/$.?#].\S*/;
    return url !== "" && urlPattern.test(url);
}


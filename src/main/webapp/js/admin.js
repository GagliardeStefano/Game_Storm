let table = document.getElementById('table');
let dashboard = document.getElementById('dashboard');
const searchBar = document.getElementById('searchBar');

let tabella;


table.style.display = 'none';

let dropdownContainer = document.querySelector('.dropdown-container');

// Funzione per mostrare/nascondere il dropdown-container
function displayContainer() {

    // Verifica lo stato attuale del dropdown-container
    if (dropdownContainer.style.display === 'block') {
        dropdownContainer.style.display = 'none'; // Nascondi il dropdown-container se è visibile
    } else {
        dropdownContainer.style.display = 'block'; // Mostra il dropdown-container se è nascosto
    }
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
    table.style.display = 'none';
    dashboard.style.display = 'flex';
}

function displayTable(){
    dashboard.style.display = 'none';
    table.style.display = 'block';
}

function getTable(element){

    switch (element){
        case 'Prodotti':
            tabella = "prodotti";
            break;
        case 'Generi':
            tabella = "genere";
            break;
        case 'Utenti':
            tabella = "utente";
            break;
        case 'Carrelli':
            tabella = "carrello";
            break;
        case 'Ordini':
            tabella = "ordini";
            break;
    }

    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(xhttp.responseText);
            printTable(response);
            displayTable();
        }
    };

    xhttp.open("POST", "AdminManager", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("tabella="+tabella);

}

/* TODO creare queste funziono */

function aggiungiEntita(){
    switch (tabella){
        case "prodotti":

    }
}

function eliminaEntita(){

}

function modificaEntita(){

}

function printTable(response) {
    let tableBody = document.getElementById('tableBody');
    let tableHead = document.getElementById('tableHead');
    let nomeTabella = document.getElementById('nomeTabella');

    nomeTabella.innerText = tabella;


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

    xhr.open('GET', `search?q=${encodeURIComponent(query)}&t=`+encodeURIComponent(tabella), true);
    xhr.onerror = function() {
        console.error('Error:', xhr.statusText);
    };
    xhr.send();
}




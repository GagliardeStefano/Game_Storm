let titolo = document.getElementById("textTitoloSezioneEstesa");

// Ottiene la stringa della query dall'URL
const windowLocation = window.location.search;
// Crea un oggetto URLSearchParams per analizzare la stringa della query
const urlParams = new URLSearchParams(windowLocation);
// Restituisce il valore del parametro specificato
const categoria = urlParams.get("categoria");
const query = urlParams.get("search");
if(categoria != null) {
    if(categoria !== "search") {
        titolo.textContent = categoria.replace("-", " ");
    }
}
/*logica ricerca*/
const search = document.getElementById("search");
const searchBar = document.getElementById("searchBar");
const giveClassCategoria = document.querySelector('.fragment-nav');
const filtroGenere = document.getElementById('filtroGenere');
const overlayGenere = document.getElementById('overlayGenere');
const filtroOrdine = document.getElementById('filtroOrdine');
const overlayOrdine = document.getElementById('overlayOrdine');
const filtroPrezzoMin = document.getElementById('filtroMinPrice');
const filtroPrezzoMax = document.getElementById('filtroMaxPrice');
const resetButton = document.querySelector('.reset');

if(categoria === "search" || (query !== "" && query !== null)) {
    if(query !== ""){
        searchBar.value = query;
        ajaxRequestFiltri();
    }
    giveClassCategoria.classList.toggle('show-search-bar');
    search.addEventListener("click", function (event){
        event.preventDefault();
    })
    searchBar.focus();
    searchBar.addEventListener("keyup", function (e) {
        ajaxRequestFiltri()
    })
} else{
    giveClassCategoria.classList.remove('show-search-bar');
}


/* logica filtri*/
function ajaxRequestFiltri(offset){
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", window.location.href,true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4) {
            if (this.status === 200) {
                let cardContainer = document.querySelector(".sezione .card-container");
                let response = JSON.parse(xhttp.responseText);
                let numProdotti = response.pop().numProdotti;
                printGame(cardContainer, response);
                if(cardContainer.childElementCount >= numProdotti){
                    mostraAltro.style = "display:none"
                }
                else{
                    mostraAltro.style = "display:unset"
                }
                if(categoria === "search" || query !== null) {
                    if(categoria === "search"){
                        if (searchBar.value === "") {
                            titolo.textContent = "Tutti i nostri giochi"
                        }
                        else {
                            titolo.textContent = "Risultati per " + searchBar.value
                        }
                    }
                    else {
                        if (query === null || query ==="" || searchBar.value === "") {
                            titolo.textContent = "Tutti i nostri giochi"
                        } else {
                            titolo.textContent = "Risultati per " + searchBar.value
                        }
                    }
                }
            } else {
                console.error("Errore nella richiestaajax: " + xhttp.status + "\n"+ xhttp.statusText);
            }
        }
    };

    let data = "genere=" + filtroGenere.value
        + "&ordine=" + filtroOrdine.value
        + "&minPrice=" + filtroPrezzoMin.value
        + "&maxPrice=" + filtroPrezzoMax.value
        if(offset !== undefined) {
            data += "&offset=" + offset;
        }
            data += "&q=" + searchBar.value;

    xhttp.send(data);
}



filtroGenere.addEventListener('change', () => {
    ajaxRequestFiltri();
    if(filtroGenere.value && filtroGenere.value !== 0){
        overlayGenere.style.display = 'none';
    } else {
        overlayGenere.style.display = 'flex';
    }
});

filtroOrdine.addEventListener('change', () => {
    ajaxRequestFiltri();
    if(filtroOrdine.value && filtroOrdine.value !== "0"){
        overlayOrdine.style.display = 'none';
    } else {
        overlayOrdine.style.display = 'flex';
    }
});

filtroPrezzoMin.addEventListener('input', () => {
    if(parseInt(filtroPrezzoMax.value) < parseInt(filtroPrezzoMin.value) && filtroPrezzoMin.value != ""){
        alert("Inserire range prezzo valido min > max ")
        filtroPrezzoMin.value = 0
        ajaxRequestFiltri()
    }else{
        ajaxRequestFiltri();
    }
    ajaxRequestFiltri();
})

filtroPrezzoMax.addEventListener('input', () => {
    if(parseInt(filtroPrezzoMax.value) < parseInt(filtroPrezzoMin.value) && filtroPrezzoMax.value != ""){
        alert("Inserire range prezzo valido max < min")
        filtroPrezzoMax.value = 200
        ajaxRequestFiltri()
    }else{
        ajaxRequestFiltri();
    }
})

resetButton.addEventListener('click', ()=>{
    filtroOrdine.value = "0";
    filtroGenere.value= "0";
    filtroPrezzoMin.value = 0;
    filtroPrezzoMax.value = 200;
    overlayGenere.style.display = 'flex';
    overlayOrdine.style.display = 'flex';
    ajaxRequestFiltri();
})

const mostraAltro = document.querySelector('.mostraAltroText');
mostraAltro.addEventListener('click', () => {
    let cardContainer = document.querySelector(".sezione .card-container");
    ajaxRequestFiltri(cardContainer.childElementCount);
})


function printGame (container, games){
    while(container.firstChild){
        container.removeChild(container.firstChild);
    }
    if(games.length == 0){
        var noGameText = `<p>Nessun gioco con questi filtri</p>`
        let noGame = document.createElement("div");
        noGame.classList.add("noGame");
        noGame.innerHTML = noGameText;
        container.appendChild(noGame)
        mostraAltro.style = "display:none"
    }
    else {
        mostraAltro.style = "display:unset"
        games.forEach(game => {
            if (game.sconto === 0) {
                var newGame = `
                            <div class="card-content">
                                <div class="card-image-container">
                                       <a role="link" aria-label="link-img" tabindex="0" href="${contextPath}/CardManager?id=${game.id}"><img class="card__image" src="${contextPath}${game.img}" alt="${game.nome}"></a>
                                    </div>
                                <div class = "card-info">
                                    <h3 class="title">${game.nome}</h3>
                                    <p>${parseFloat(game.prezzo).toFixed(2).replace(".", ",")}€</p>
                                </div>
                            </div>`
            } else {
                var newGame = `<div class="card-content">
                    <div class="card-image-container">
                        <a href="${contextPath}/CardManager?id=${game.id}"><img class="card__image" src="${contextPath}${game.img}" alt=""></a>
                        <div class="scontato">-${game.sconto}%</div>
                    </div>
                    <div class="card-info">
                        <h3 class="title">${game.nome}</h3>
                        <p>${parseFloat(game.prezzoScontato).toFixed(2).replace(".", ",")}€</p>
                    </div>
                </div>`
            }

            let cardDiv = document.createElement("div");
            cardDiv.classList.add("card");
            cardDiv.innerHTML = newGame;
            container.appendChild(cardDiv);

        })
    }
}

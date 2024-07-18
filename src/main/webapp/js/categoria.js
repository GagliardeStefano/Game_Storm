let titolo = document.getElementById("textTitoloSezioneEstesa");

// Ottiene la stringa della query dall'URL
const queryString = window.location.search;
// Crea un oggetto URLSearchParams per analizzare la stringa della query
const urlParams = new URLSearchParams(queryString);
// Restituisce il valore del parametro specificato
if(urlParams.get("categoria")!=null) {
    titolo.textContent = urlParams.get("categoria").replace("-", " ");
}

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
                                        <a href="${contextPath}/CardManager?id=${game.id}"><img class="card__image" src="${contextPath}${game.img}" alt=""></a>
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
                console.log("child: " + cardContainer.childElementCount);
                console.log("numProdotti: " + numProdotti);
                if(cardContainer.childElementCount >= numProdotti){
                    mostraAltro.style = "display:none"
                }
                else{
                    mostraAltro.style = "display:unset"
                }


            } else {
                console.error("Errore nella richiestaajax: " + xhttp.status + "\n"+ xhttp.responseText);
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
    xhttp.send(data);
}

const filtroGenere = document.getElementById('filtroGenere');
const overlayGenere = document.getElementById('overlayGenere');
const filtroOrdine = document.getElementById('filtroOrdine');
const overlayOrdine = document.getElementById('overlayOrdine');
const filtroPrezzoMin = document.getElementById('filtroMinPrice');
const filtroPrezzoMax = document.getElementById('filtroMaxPrice');
const resetButton = document.querySelector('.reset');

filtroGenere.addEventListener('change', () => {
    ajaxRequestFiltri();
    if(filtroGenere.value && filtroGenere.value != 0){
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

filtroPrezzoMin.addEventListener('change', () => {
    ajaxRequestFiltri();
})

filtroPrezzoMax.addEventListener('change', () => {
   ajaxRequestFiltri();
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


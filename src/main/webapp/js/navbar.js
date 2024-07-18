
const menuTrigger = document.querySelector('.trigger'),
    closeTrigger = document.querySelector('.mini-close'),
    giveClass = document.querySelector('.fragment-nav');

menuTrigger.addEventListener('click', function(){
    giveClass.classList.toggle('showmenu');
});

closeTrigger.addEventListener('click', function(){
    giveClass.classList.remove('showmenu');
});

const searchTrigger = document.querySelector('#search');
searchTrigger.addEventListener('click', function() {
    giveClass.classList.toggle('show-search-bar');
});




const searchInput = document.getElementById('searchInput');
const resultsList = document.getElementById('results');

async function performSearch(query) {
    if (!query) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/GameStorm_war/search?q=${query}`);
        const data = await response.json();
        // Esempio: aggiorna un elenco di risultati
        updateResultsList(data);
    } catch (error) {
        console.error('Errore nella ricerca:', error);
    }
}

function updateResultsList(results) {
    resultsList.innerHTML = '';

    results.forEach(item => {
        const li = document.createElement('li');
        li.textContent = item.nome; // Assicurati che 'nome' sia il campo corretto da visualizzare
        resultsList.appendChild(li);
    });
}

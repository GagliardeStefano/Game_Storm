
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
        const deleteAll = document.getElementById('delete-all');
        const addAll = document.getElementById('add-all');

        function deleteFromDb(id, element){

            const xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    closeCard(element);
                }else if (this.readyState == 4) {
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
                if (this.readyState == 4 && this.status == 200) {
                    closeAllCard();
                }else if (this.readyState == 4) {
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
        button.style.display = 'none';

        let orderId = button.getAttribute('data-order-id');
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
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

        container.querySelectorAll('.hidden').forEach(function(hiddenElement) {
            hiddenElement.style.display = 'flex';
        });

        const mostraMeno = container.nextElementSibling;
        mostraMeno.style.display = 'block';
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
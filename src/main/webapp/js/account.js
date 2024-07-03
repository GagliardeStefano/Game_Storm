
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
                    window.alert('Errore nel rimuovere il gioco dal database');
                }
            };

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("IdProd=" + encodeURIComponent(id));

        }

        function deleteAllFromDb(){

            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    closeAllCard();
                }else if (this.readyState == 4) {
                    window.alert('Errore nel rimuovere i gioco dal database');
                }
            };

            xhttp.open("POST", "UpdateUser", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("IdProd=all");
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
    ordiniEffettuati.addEventListener('click', function(){

        const moreButtons = document.querySelectorAll('.game.more');
        const lessButtons = document.querySelectorAll('.mostra-meno');

        moreButtons.forEach(function(button) {
            button.addEventListener('click', function() {
                let container = button.closest('.container-games');
                container.style.display = 'flex';
                button.style.display = 'none';

                container.querySelectorAll('.hidden').forEach(function(hiddenElement) {
                    hiddenElement.style.display = 'flex';
                });

                const nextContainer = container.nextElementSibling;

                if (nextContainer && nextContainer.classList.contains('container-games')) {
                    nextContainer.style.display = 'flex';
                    nextContainer.querySelectorAll('.hidden').forEach(function(hiddenElement) {
                        hiddenElement.style.display = 'flex';
                    });

                    // Mostra il pulsante "Mostra Meno" del prossimo contenitore
                    const nextShowLessButton = nextContainer.nextElementSibling
                    if (nextShowLessButton) {
                        nextShowLessButton.style.display = 'block';
                    }
                }
            });
        });

        lessButtons.forEach(function(button) {
            button.addEventListener('click', function() {
                let containerMore = button.previousElementSibling;
                console.log(containerMore);

                containerMore.querySelectorAll('.hidden').forEach(function(hiddenElement) {
                    console.log(hiddenElement);
                    hiddenElement.style.display = 'none';
                });

                containerMore.style.display = 'none';

                let container = containerMore.previousElementSibling;
                console.log(container);

                container.querySelector('.game.more').style.display = 'flex';

                container.querySelectorAll('.hidden').forEach(element =>{
                    console.log(element);
                    element.style.display = 'none';
                })

            });
        });
    })
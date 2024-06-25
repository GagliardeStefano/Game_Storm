
document.addEventListener('DOMContentLoaded', function() {
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

    const deleteButtons = document.querySelectorAll('.ri-delete-bin-5-line');
    const addButtons = document.querySelectorAll('.ri-shopping-cart-2-line');
    const deleteAll = document.getElementById('delete-all');
    const addAll = document.getElementById('add-all');

    deleteAll.addEventListener('click', function(){
        deleteButtons.forEach(button => {
            const card = button.closest('.container-card');
            card.style.transition = 'opacity 0.5s';
            card.style.opacity = '0';
            setTimeout(() => card.remove(), 500);
        })
    })


    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            let card = button.closest('.container-card');
            card.style.transition = 'opacity 0.5s';
            card.style.opacity = '0';
            setTimeout(() => card.remove(), 500);
        });
    });

    addAll.addEventListener('click', function(){
        addButtons.forEach(button => {
            /*TODO aggiunta nel carrello*/
        });
    })


    addButtons.forEach(button => {
        button.addEventListener('click', function() {
            alert('Aggiunto al carrello!');
        });
    });
});

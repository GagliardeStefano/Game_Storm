var cards = document.querySelectorAll('.carousel .card');

checkWindowSize();
window.addEventListener('resize', checkWindowSize);


cards.forEach(card => {
    card.addEventListener('click', (event) => {
        const link = card.querySelector('a');

        if (!card.classList.contains('expanded')) {
            event.preventDefault();
            handleExpand(card);
        } else {
            link.classList.remove('disabled');
        }
    });
});

// FUNZIONI
function handleExpand(card) {
    const link = card.querySelector('a');

    if (card.classList.contains('expanded')) {
        link.classList.remove('disabled');
    } else {
        cards.forEach(c => {
            c.classList.remove('expanded');
            c.querySelector('a').classList.add('disabled');
        });
        card.classList.add('expanded');
        link.classList.remove('disabled');
    }
}

function checkWindowSize(){

    let cardsHiding = 0;
    const widths = [540, 420, 480];

    widths.forEach((width) => {
        if(window.innerWidth < width){
            cardsHiding++;
        }
    });

    cards.forEach((card, index) => {
        if(index >= cards.length - cardsHiding){
            card.style.display = 'none';
        } else {
            card.style.display = '';
        }
    })
}
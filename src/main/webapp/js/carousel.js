
const cards = document.querySelectorAll('.card');

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

function removeCartItem(element, id) {
    const cartItem = element.closest('.cart-item');
    let param = "remove";
    if (cartItem) {
        cartItem.style.transition = 'opacity 0.5s';
        cartItem.style.opacity = '0';
        setTimeout(() => {
            cartItem.remove();
            const xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    const response = this.responseText.split(";");
                    const totale = parseFloat(response[0]);
                    const scontoTotale = parseFloat(response[1]);
                    const prezzoScontatoTotale = parseFloat(response[2]);
                    updateCartValues(totale, scontoTotale, prezzoScontatoTotale);
                    checkIfCartIsEmpty();
                    updateCartCount();
                }
            }
            xhttp.open("POST", "CartManager", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("type=" + encodeURIComponent(param) + "&id=" + encodeURIComponent(id));
        }, 500);
    }
}

function updateCartValues(totale, scontoTotale, prezzoScontatoTotale) {
    document.querySelector('.prezzo-ufficiale p:last-child').textContent = totale.toFixed(2) + '€';
    document.querySelector('.sconto p:last-child').textContent = scontoTotale.toFixed(2) + '€';
    document.querySelector('.prezzo-finale p:last-child').textContent = prezzoScontatoTotale.toFixed(2) + '€';
}

function checkIfCartIsEmpty() {
    const cartItems = document.querySelectorAll('.cart-item');
    if (cartItems.length === 0) {
        showEmptyCartMessage();
        removeLinkPagamento();
    }
}

function removeLinkPagamento(){
    let button = document.getElementById('link-pagamento');

    if (button != null)
        button.href = "#";
}

function showEmptyCartMessage() {
    const cartContainer = document.querySelector('.cart');
    const emptyCartMessage = document.createElement('div');
    emptyCartMessage.classList.add('empty-cart-message');
    emptyCartMessage.innerHTML = `
        <h2>Il tuo carrello è vuoto</h2>
        <p>Aggiungi dei prodotti per iniziare a fare shopping!</p>
    `;
    cartContainer.appendChild(emptyCartMessage);

}
function updateCartCount() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.querySelector('.num').textContent = parseInt(this.responseText, 10);
        }
    };
    xhttp.open("POST", "CartManager", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("action=cartCount");
}

function addToCart(id)
{
    let param="add";

    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function (){
        if(this.readyState === 4 && this.status === 200){
            updateCartCount();
        }
    }
    xhttp.open("POST", "CartManager", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+param+"&id=" + encodeURIComponent(id));
}







document.getElementById('hamburger-menu').addEventListener('click', function() {
    var navLinks = document.querySelector('.nav-links');
    var hamburgerMenu = document.getElementById('hamburger-menu');
    navLinks.classList.toggle('open');
    hamburgerMenu.classList.toggle('open');
});
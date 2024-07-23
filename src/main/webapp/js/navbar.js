
const menuTrigger = document.querySelector('.trigger'),
    closeTrigger = document.querySelector('.mini-close'),
    giveClass = document.querySelector('.fragment-nav');

menuTrigger.addEventListener('click', function(){
    giveClass.classList.toggle('showmenu');
});

closeTrigger.addEventListener('click', function(){
    giveClass.classList.remove('showmenu');
});
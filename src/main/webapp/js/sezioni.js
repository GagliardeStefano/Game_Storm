{
    const MathUtil = {
        lerp: (a, b, n) => (1 - n) * a + n * b
        //usata per animare transizione fra due valori
    };

    let winsize; //dimensioni schermo
    const calcWinsize = () => winsize = { width: window.innerWidth, height: window.innerHeight };
    calcWinsize();
    window.addEventListener('resize',calcWinsize);


    class Card {
        constructor(el, parent) {
            this.el = el;
        }
    }

    class Sezione {
        constructor(el) {
            this.el = el;
            this.inner = this.el.querySelector('.sezione .card-container');
            this.innerWidth = 0;
            this.items = [];
            //creo una elemento Card per ogni card presente nello schermo
            [...this.el.querySelectorAll('.sezione .card')].forEach(item => {
                this.items.push(new Card(item))
                this.innerWidth += item.getBoundingClientRect().width;
            });
            this.isDragged = false;
            this.currentX = 0; //posizione attuale del carosello
            this.initialX = 0; // la x da dove il mouse clicca quando inizia a scorrere
            this.xOffset = 0; //di quanto sono state spostate le card
            this.pervPosition = 0;
            this.maxDrag = this.innerWidth - winsize.width;
            this.intervalId = undefined;
            this.init();
            this.initEvents();
        }

        init() {
            this.render = () => {
                this.intervalId = undefined;

                this.pervPosition = MathUtil.lerp(this.pervPosition, this.currentX, 0.1);
                if(winsize.width < 430) {
                    this.inner.style.transform = 'matrix(1, 0, 0, 1, ' + this.pervPosition + ', 0)';
                    this.inner.style.width = this.innerWidth + 'px';
                }
                else{
                    this.inner.style.transform = '';
                    this.inner.style.width = '';
                }

                if (!this.intervalId) {
                    this.intervalId = requestAnimationFrame(() => this.render());
                }
            };
            this.intervalId = requestAnimationFrame(() => this.render());

        }

        onDragStart(e) {
            this.isDragged = true;
            this.initialX = this.unify(e).clientX - this.xOffset;
        }

        onDragMove(e) {
            if (!this.isDragged) return;
            e.preventDefault();
            this.currentX = this.unify(e).clientX - this.initialX;
        }

        onDragEnd() {
            //torna indietro quando scorro troppo a destra
                if (this.currentX > 0) {
                    this.currentX = 0;
                }
                if (this.currentX < -1 * this.maxDrag) {
                    this.currentX = -1 * this.maxDrag - 210;
                }
            this.isDragged = false;
            this.xOffset =this.currentX;
        }

        initEvents() {
            if(window.PointerEvent){
                // Pointer events
                this.inner.addEventListener('pointerdown', (e) => {
                    this.onDragStart(e);
                });
                this.inner.addEventListener('pointermove', (e) => {
                    this.onDragMove(e);
                });
                this.inner.addEventListener('pointerup', (e) => {
                    this.onDragEnd();
                });
            }else{
                // Mouse events
                this.inner.addEventListener('mousedown', (e) => {
                    this.onDragStart(e);
                });
                this.inner.addEventListener('mouseleave', (e) => {
                    this.onDragEnd();
                });
                this.inner.addEventListener('mouseup', (e) => {
                    this.onDragEnd();
                });
                this.inner.addEventListener('mousemove', (e) => {
                    this.onDragMove(e);
                });

                // Touch events
                this.inner.addEventListener('touchstart', (e) => {
                    this.onDragStart(e);
                });
                this.inner.addEventListener('touchmove', (e) => {
                    this.onDragMove(e);
                });
                this.inner.addEventListener('touchend', (e) => {
                    this.onDragEnd();
                });
            }
            // Unifying touch and click
            this.unify = (e) => {
                return e.changedTouches ? e.changedTouches[0] : e
            };
        }
    }

    function resize(){
        const sezione = document.querySelectorAll('.sezione');
        if (sezione.length > 0) {
                    for (let i = 0; i < sezione.length; i++) {
                            new Sezione(sezione[i]);
                    }
                }
    }
    /*nel caso lo shcermo sia inizialmente minore di 430*/
    resize();
    window.addEventListener('resize',resize);

    /*aggiungo elemento all'url in modo da riconoscere che sezione ho scelto*/
    let link = document.querySelectorAll('.sezione .titolo a');
    let titoli = document.querySelectorAll('.sezione .titolo h2');
    for (let i = 0; i < link.length; i++) {
        link[i].addEventListener('click', (e) => {
            if(!link[i].href.includes("categoria"))
                link[i].href += "?categoria=" + titoli[i].textContent.replace(" ","-");
            else{
                const index = link[i].href.indexOf("categoria=");
                link[i].href = link[i].substring(0, index) + titoli[i].textContent.replace(" ","-");

            }

        })
    }

}
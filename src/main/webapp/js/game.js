

    function updateFavourite(id){
        let tag = document.getElementById('heart');
        let param;
        if (tag.classList.contains('ri-heart-fill')){
            param = "idRem";
        }else {
            param = "idAdd";
        }

        const xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function (){
            if(this.readyState === 4 && this.status === 200){
                // Update the heart element based on the response
                if (tag.classList.contains('ri-heart-fill')) {
                    tag.classList.add('ri-heart-line');
                    tag.classList.remove('ri-heart-fill');
                } else {
                    tag.classList.add('ri-heart-fill');
                    tag.classList.remove('ri-heart-line');
                }
            }
        }

        xhttp.open("POST", "UpdateUser", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("type=" + param + "&id=" + id + "&from=game");
    }



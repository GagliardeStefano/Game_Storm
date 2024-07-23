addEventListenerAtInput(document.getElementById('form-pagamento'), '');

function validateFormPagamentoCard(form, id){

    let radioButtonsChecked = form.querySelectorAll('input[type=radio]:checked');

    if (radioButtonsChecked.length === 1){
        return true;
    }else if(validateFormInputCard(form, id)){
        return true;
    }

    return false;


}
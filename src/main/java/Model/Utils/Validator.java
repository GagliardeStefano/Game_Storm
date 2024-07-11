package Model.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    private final List<String> errors;

    private static final Pattern NOME_COGNOME_REGIONE_PATTERN = Pattern.compile(".+");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
    private static final Pattern DATA_NASCITA_PATTERN = Pattern.compile("(19[3-9][4-9]|19[4-9]\\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])");


    private static final Pattern NUMERO_CARTA_PATTERN = Pattern.compile("\\d{4} \\d{4} \\d{4} \\d{4}");
    private static final Pattern DATA_SCADENZA_CARTA_PATTERN = Pattern.compile("(0[1-9]|1[0-2])/\\d{2}");
    private static final Pattern CVV_CARTA_PATTERN = Pattern.compile("\\d{3}");
    private static final Pattern NOME_COGNOME_CARTA_PATTERN = Pattern.compile("[a-zA-Z\\s]+");


    public Validator(){
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors(){
        return !this.errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors.addAll(errors);
    }

    private boolean addErros(boolean condition, String error){

        if(!condition){
            this.errors.add(error);
            return false;
        }
        return true;
    }

    public void validateAll(String nome, String cognome, String regione, String email, String password, String dataNascita){
        asserString(nome, "Inserisci un nome");
        asserString(cognome, "Inserisci un cognome");
        asserString(regione, "Seleziona almeno una regione");
        asserEmail(email, "Inserisci un email valida");
        asserPassword(password, "Deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri");
        asserData(dataNascita, "Inserisci una data valida");
    }

    public void validateAll(String email, String password){
        asserEmail(email, "Inserisci un email valida");
        asserPassword(password, "Deve contenere almeno un numero, almeno una lettera maiuscola e minuscola e almeno 8 o più caratteri");
    }

    private boolean validate(String string, Pattern pattern, String msg){

        boolean condition = pattern.matcher(string).matches();
        return addErros(condition, msg);
    }

    public boolean asserString(String string, String msg){
        return validate(string, NOME_COGNOME_REGIONE_PATTERN, msg);
    }

    public boolean asserEmail(String string, String msg){
        return validate(string, EMAIL_PATTERN, msg);
    }

    public boolean asserPassword(String string, String msg){
        return validate(string, PASSWORD_PATTERN, msg);
    }

    public boolean asserData(String string, String msg){
        return validate(string, DATA_NASCITA_PATTERN, msg);
    }

    public void validateCarta(String numero, String data, String cvv, String nome, String cognome){
        asserNumeroCarta(numero, "Il numero della carta deve essere composto da 16 cifre.");
        asserDataCarta(data, "La data di scadenza deve essere nel formato MM/AA.");
        asserDataPassataCarta(data, "La data di scadenza non può essere nel passato.");
        asserCvvCarta(cvv, "Il CVV deve essere composto da 3 cifre.");
        asserNomeCarta(nome, "Il nome del titolare deve contenere solo lettere e spazi.");
        asserCognomeCarta(cognome, "Il cognome del titolare deve contenere solo lettere e spazi.");
    }

    public boolean asserNumeroCarta(String numero, String msg){
        return validate(numero, NUMERO_CARTA_PATTERN, msg);
    }

    public boolean asserDataCarta(String data, String msg){
        return validate(data, DATA_SCADENZA_CARTA_PATTERN, msg);
    }

    public boolean asserDataPassataCarta(String data, String msg){
        LocalDate today = LocalDate.now();
        int month = Integer.parseInt(data.split("/")[0]);
        int year = Integer.parseInt("20" + data.split("/")[1]);

        // Creare la data di scadenza come l'ultimo giorno del mese specificato
        LocalDate expiry = LocalDate.of(year, month, 1).withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth());

        if (expiry.isBefore(today)) {
            addErros(true, msg);
            return false;
        }
        return true;
    }

    public boolean asserCvvCarta(String cvv, String msg) {
        return validate(cvv, CVV_CARTA_PATTERN, msg);
    }

    public boolean asserNomeCarta(String nome, String msg){
        return validate(nome, NOME_COGNOME_CARTA_PATTERN, msg);
    }

    public boolean asserCognomeCarta(String cognome, String msg){
        return validate(cognome, NOME_COGNOME_CARTA_PATTERN, msg);
    }

}

package Model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    private final List<String> errors;

    private static final Pattern NOME_COGNOME_COUNTRY_PATTERN = Pattern.compile(".+");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
    private static final Pattern DATA_NASCITA_PATTERN = Pattern.compile("^(19[3-9][4-9]|19[4-9]\\d|200[0-6])-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");



    public Validator(){
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors(){
        return !this.errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    private boolean addErros(boolean condition, String error){

        if(!condition){
            this.errors.add(error);
            return false;
        }
        return true;
    }

    public void validateAll(String nome, String cognome, String country, String email, String password, String dataNascita){
        asserString(nome, "Inserisci un nome");
        asserString(cognome, "Inserisci un cognome");
        asserString(country, "Inserisci un paese");
        asserEmail(email, "Inserisci un email valida");
        asserPassword(password, "Password: Deve contenere almeno un numero\nuna lettera maiuscola e minuscola\nalmeno 8 o più caratteri");
        asserData(dataNascita, "Devi essere almeno maggiorenne");
    }

    public void validateAll(String email, String password){
        asserEmail(email, "Inserisci un email valida");
        asserPassword(password, "Password: Deve contenere almeno un numero\nuna lettera maiuscola e minuscola\nalmeno 8 o più caratteri");
    }

    private boolean validate(String string, Pattern pattern, String msg){

        boolean condition = pattern.matcher(string).matches();
        return addErros(condition, msg);
    }

    public boolean asserString(String string, String msg){
        return validate(string, NOME_COGNOME_COUNTRY_PATTERN, msg);
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
}

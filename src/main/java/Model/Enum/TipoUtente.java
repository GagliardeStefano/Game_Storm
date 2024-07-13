package Model.Enum;

public enum TipoUtente {
    Semplice, Admin1, Admin2;

    public static TipoUtente StringtoEnum(String tipo){

        if(tipo.equals("Semplice")) return Semplice;
        if(tipo.equals("Admin1")) return Admin1;
        if(tipo.equals("Admin2")) return Admin2;
        return null;
    }
}

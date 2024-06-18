package Model.Enum;

public enum TipoUtente {
    Semplice, Amministratore;

    public static TipoUtente StringtoEnum(String tipo){

        if(tipo.equals("Semplice")) return Semplice;
        if(tipo.equals("Amministratore")) return Amministratore;
        return null;
    }
}

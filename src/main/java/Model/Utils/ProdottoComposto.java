package Model.Utils;

import Model.Prodotto;

public class ProdottoComposto {

    private Prodotto prodotto;
    private double prezzo;
    private String key;

    public ProdottoComposto() {}


    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

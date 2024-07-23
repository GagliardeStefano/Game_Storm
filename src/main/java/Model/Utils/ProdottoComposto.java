package Model.Utils;

import Model.Prodotto;

import java.util.Random;

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

    public String generateKey(){
        String segment1 = generateRandomSegment(3);
        String segment2 = generateRandomSegment(3);
        String segment3 = generateRandomSegment(3);

        return segment1 + "-" + segment2 + "-" + segment3;
    }

    private static String generateRandomSegment(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder segment = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            segment.append(characters.charAt(random.nextInt(characters.length())));
        }

        return segment.toString();
    }
}

package Model;

import Model.Utils.ProdottoComposto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Carrello {

    private int id;
    private String email;
    private List<ProdottoComposto> prodotti;
    private double totale = 0;
    private double scontoTotale = 0;
    private double prezzoScontatoTotale=0;
    private String data;

    public Carrello(){
        this.prodotti = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public double getPrezzoScontatoTotale() {
        return prezzoScontatoTotale;
    }

    public double getScontoTotale() {
        return scontoTotale;
    }

    public void setProdotti(List<ProdottoComposto> prodotti) {
        this.prodotti = prodotti;
        this.setTotale();
        this.setScontoTotale();
        this.setPrezzoScontatoTotale();
    }

    public List<ProdottoComposto> getProdotti() {
        return prodotti;
    }

    public void addProdotto(ProdottoComposto prodotto) {
        for (ProdottoComposto p : prodotti) {
            if (p.getProdotto().getId() == prodotto.getProdotto().getId()) {
                return;
            }
        }
        prodotti.add(prodotto);
        this.totale += prodotto.getProdotto().getPrezzo();
        this.scontoTotale += ((double) prodotto.getProdotto().getSconto() /100)*prodotto.getProdotto().getPrezzo();
        this.prezzoScontatoTotale += prodotto.getProdotto().getPrezzoScontato();

    }

    public void removeProdotto(ProdottoComposto prodotto) {
        Iterator<ProdottoComposto> iterator = prodotti.iterator();
        while (iterator.hasNext()) {
            ProdottoComposto p = iterator.next();
            if (p.getProdotto().getId() == prodotto.getProdotto().getId()) {
                iterator.remove();
                this.totale -= prodotto.getProdotto().getPrezzo();
                this.scontoTotale -= ((double) prodotto.getProdotto().getSconto() / 100) * prodotto.getProdotto().getPrezzo();
                this.prezzoScontatoTotale -= prodotto.getProdotto().getPrezzoScontato();
                break; // Exit the loop after removing the product
            }
        }
    }


    public void setTotale(double totale) {
        this.totale = totale;
    }

    public void setTotale(){

        prodotti.forEach((ProdottoComposto p)->{
            totale += p.getPrezzo();
        });
    }

    public void setScontoTotale() {
        for(ProdottoComposto prodottoComposto : prodotti){
            this.scontoTotale += ((double) prodottoComposto.getProdotto().getSconto() /100)*prodottoComposto.getProdotto().getPrezzo();
        }
    }

    public void setPrezzoScontatoTotale() {
        for(ProdottoComposto prodottoComposto : prodotti){
            this.prezzoScontatoTotale += prodottoComposto.getProdotto().getPrezzoScontato();
        }
    }
    public void setTotaleProdotti(){
        for(ProdottoComposto prodottoComposto : prodotti){
            this.totale += prodottoComposto.getProdotto().getPrezzo();
        }
    }

    public void addPrezzo(double prezzo){
        this.totale += prezzo;
    }

    public double getTotale() {
        return totale;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", prodotti=" + prodotti +
                ", totale=" + totale +
                ", data=" + data +
                '}';
    }
}

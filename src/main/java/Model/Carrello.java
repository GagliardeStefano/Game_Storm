package Model;

import Model.Utils.ProdottoComposto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carrello {

    private int id;
    private String email;
    private List<ProdottoComposto> prodotti;
    private double totale = 0;
    private Date data;

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

    public void setProdotti(List<ProdottoComposto> prodotti) {
        this.prodotti = prodotti;
    }

    public List<ProdottoComposto> getProdotti() {
        return prodotti;
    }

    public void addProdotto(ProdottoComposto prodotto) {
        prodotti.add(prodotto);
    }

    public void removeProdotto(ProdottoComposto prodotto) {
        prodotti.remove(prodotto);
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public void setTotale(){

        prodotti.forEach((ProdottoComposto p)->{
            totale += p.getPrezzo();
        });
    }

    public void addPrezzo(double prezzo){
        this.totale += prezzo;
    }

    public double getTotale() {
        return totale;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
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

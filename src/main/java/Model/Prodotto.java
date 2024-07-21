package Model;

import jakarta.servlet.ServletContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class Prodotto {

    private static final int WIDTH_IMG = 728;
    private static final int HEIGHT_IMG = 453;

    private int id;
    private String key;
    private String descrizione;
    private String nome;
    private Date dataRilascio;
    private double prezzo;
    private int sconto;
    private double prezzoScontato;
    private String img;
    private String trailer;

    public Prodotto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataRilascio() {
        return dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio) {
        this.dataRilascio = dataRilascio;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getSconto() {
        return sconto;
    }

    public void setSconto(int sconto) {
        this.sconto = sconto;
    }

    public double getPrezzoScontato() {
        return prezzoScontato;
    }

    public void setPrezzoScontato() {
        this.prezzoScontato = this.getPrezzo() - ((this.getPrezzo()*this.getSconto())/100);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void downloadImage(String urlString, String imageName, ServletContext servletContext) throws IOException {
        URL url = new URL(urlString);
        BufferedImage originalImage = ImageIO.read(url);

        // Ridimensiona l'immagine
        Image resizedImage = originalImage.getScaledInstance(WIDTH_IMG, HEIGHT_IMG, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(WIDTH_IMG, HEIGHT_IMG, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedResizedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Ottiene il percorso reale della directory di destinazione
        String destinationPath = servletContext.getRealPath("/images/giochi/");
        File outputDir = new File(destinationPath);

        // Costruisci il nome del file
        String fileName = imageName.replace(" ", "") + ".jpg";
        File outputFile = new File(outputDir, fileName);

        // Salva l'immagine ridimensionata
        ImageIO.write(bufferedResizedImage, "jpg", outputFile);

    }
}

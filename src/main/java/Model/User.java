package Model;

import Model.Enum.TipoUtente;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String nome, cognome, email, password, passwordHash, data, foto, regione;
    private TipoUtente tipo;

    public User(){}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public void setPasswordHash() throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.reset();
        md.update(this.password.getBytes(StandardCharsets.UTF_8));
        this.passwordHash = String.format("%032x", new BigInteger(1, md.digest()));
    }

    public void setPasswordHash(String passwordhashata) throws NoSuchAlgorithmException {
        this.passwordHash = passwordhashata;
    }

    public void setTipo(TipoUtente tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getData() {
        return data;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public TipoUtente getTipo() {
        return tipo;
    }

    public String getRegione() {
        return regione;
    }
}

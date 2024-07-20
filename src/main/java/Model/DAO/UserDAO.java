package Model.DAO;

import Model.Carrello;
import Model.CartaCredito;
import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ConPool;
import Model.Utils.ProdottoComposto;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {

    public boolean doSave(User user) {
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("INSERT INTO utente(email, nome, cognome, regione, data_nascita, password_hash, tipo, foto) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getNome());
            ps.setString(3, user.getCognome());
            ps.setString(4, user.getRegione());
            ps.setString(5, user.getData());
            ps.setString(6, user.getPasswordHash());
            ps.setString(7, user.getTipo().name());
            ps.setString(8, user.getFoto());

            return ps.executeUpdate() != -1;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void changeAvatar(String email, String path){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("UPDATE utente SET foto = ? WHERE email=?");

            ps.setString(1, path);
            ps.setString(2, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public User doRetrieveByEmail(String email){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM utente WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){

                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setNome(rs.getString("nome"));
                user.setCognome(rs.getString("cognome"));
                user.setRegione(rs.getString("regione"));
                user.setData(rs.getString("data_nascita"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setTipo(TipoUtente.valueOf(rs.getString("tipo")));
                user.setFoto(rs.getString("foto"));

                return user;
            }

            return null;

        }catch (SQLException | NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }

    public boolean checkLoginUser(String email, String passwordHashata){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT password_hash FROM utente WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("password_hash").equals(passwordHashata);
            }

            return false;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getWishlistByEmail(String email){
        List<Prodotto> prodotti = new ArrayList<>();

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotti JOIN preferiti ON prodotti.ID = preferiti.ID_prodotto WHERE email_utente = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setPrezzoScontato();

                prodotti.add(prodotto);
            }

            return prodotti;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeFavorite(String idProd, String email){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("DELETE FROM preferiti WHERE ID_prodotto = ? AND email_utente = ?");
            ps.setString(1, idProd);
            ps.setString(2, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeAllFavorite(String email){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("DELETE FROM preferiti WHERE email_utente = ?");
            ps.setString(1, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean emailAlreadyExists(String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT email FROM utente WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private List<Carrello> getOrdiniEffettuatiByEmail(String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ordini JOIN prodotti ON ordini.ID_prodotto = prodotti.ID WHERE email_utente = ? ORDER BY data_acquisto ASC;");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            List<Carrello> out = new ArrayList<>();
            Carrello carrello = null;
            Date lastDate = null;
            int count = 0; // Contatore per tenere traccia delle righe per ogni ordine

            while (rs.next()) {
                Date currentDate = rs.getDate("data_acquisto");

                // Se la data corrente è diversa dalla data dell'ultimo carrello
                if (!currentDate.equals(lastDate)) {
                    // Se c'è un carrello attuale, impostiamo il totale e lo aggiungiamo alla lista
                    if (carrello != null) {
                        carrello.setTotale();
                        out.add(carrello);
                    }

                    // Creiamo un nuovo carrello per la nuova data
                    carrello = new Carrello();
                    carrello.setEmail(rs.getString("email_utente"));
                    carrello.setData(currentDate);
                    count = 0; // Resetta il contatore per l'ordine appena iniziato

                    // Aggiorniamo la data dell'ultimo carrello
                    lastDate = currentDate;
                }

                // Verifica se abbiamo già raggiunto il limite di 3 righe per l'ordine corrente
                if (count < 3) {
                    // Aggiungiamo il prodotto al carrello attuale
                    Prodotto prodotto = new Prodotto();
                    prodotto.setId(rs.getInt("ID_prodotto"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setImg(rs.getString("immagine"));

                    ProdottoComposto prodottoComposto = new ProdottoComposto();
                    prodottoComposto.setProdotto(prodotto);
                    prodottoComposto.setPrezzo(rs.getDouble("prezzo_prodotto"));
                    prodottoComposto.setKey(rs.getString("key_prodotto"));

                    carrello.addProdotto(prodottoComposto);

                    // Incrementa il contatore delle righe per l'ordine corrente
                    count++;
                }else {
                    double prezzo = rs.getDouble("prezzo_prodotto");
                    carrello.addPrezzo(prezzo);
                    count++;
                }
            }

            // Aggiungiamo l'ultimo carrello alla lista se non è nullo
            if (carrello != null) {
                carrello.setTotale();
                out.add(carrello);
            }

            return out;


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<ProdottoComposto> getOtherGamesByOrderId(String email, String orderId){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ordini JOIN prodotti ON ordini.ID_prodotto = prodotti.ID WHERE data_acquisto = ? AND email_utente = ? LIMIT ? OFFSET ?");

            ps.setString(1, orderId);
            ps.setString(2, email);
            ps.setInt(3, Integer.MAX_VALUE);
            ps.setInt(4, 3);

            ResultSet rs = ps.executeQuery();

            List<ProdottoComposto> out = new ArrayList<>();

            while (rs.next()) {

                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("ID_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setImg(rs.getString("immagine"));

                ProdottoComposto prodottoComposto = new ProdottoComposto();
                prodottoComposto.setProdotto(prodotto);
                prodottoComposto.setPrezzo(rs.getDouble("prezzo_prodotto"));
                prodottoComposto.setKey(rs.getString("key_prodotto"));

                out.add(prodottoComposto);

            }

            return out;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<Carrello>> getOrdiniByMonth(String email){

        List<Carrello> ordini = getOrdiniEffettuatiByEmail(email);
        ordini.sort(Comparator.comparing(Carrello::getData).reversed());

        Map<String, List<Carrello>> ordiniByMonth = new HashMap<>();

        for(Carrello ordine : ordini){
            Calendar cal = Calendar.getInstance();
            cal.setTime(ordine.getData());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; // I mesi in Calendar vanno da 0 a 11
            String key = year + "-" + String.format("%02d", month);

            ordiniByMonth.computeIfAbsent(key, k -> new ArrayList<>()).add(ordine);
        }

        return ordiniByMonth.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public List<CartaCredito> getMetodiPagamentoByEmail(String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM pagamenti WHERE email_utente = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            List<CartaCredito> out = new ArrayList<>();

            while (rs.next()) {
                CartaCredito cartaCredito = new CartaCredito();

                cartaCredito.setId(rs.getInt("ID"));
                cartaCredito.setEmail(rs.getString("email_utente"));

                String[] proprietario = rs.getString("proprietario").split(" ");
                cartaCredito.setCognome(proprietario[0]);
                cartaCredito.setNome(proprietario[1]);

                cartaCredito.setNumero(rs.getString("numero"));
                cartaCredito.setData_scadenza(rs.getString("data_scadenza"));
                cartaCredito.setCvv(rs.getString("cvv"));
                cartaCredito.setTipo(rs.getString("tipo"));

                out.add(cartaCredito);
            }

            return out;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void addFavourite(String id, String email){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("insert into preferiti values (?,?)");
            ps.setString(1, id);
            ps.setString(2, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void updateCartaCredito(String id, String email, String numero, String data, String cvv, String nome, String cognome){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("UPDATE pagamenti " +
                    "SET numero = ? , proprietario = ?, data_scadenza = ?, cvv = ?" +
                    "WHERE ID = ? AND email_utente = ? LIMIT 1");

            ps.setString(1, numero);
            ps.setString(2, cognome+" "+nome);
            ps.setString(3, data);
            ps.setString(4, cvv);
            ps.setString(5, id);
            ps.setString(6, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeCartaCredito(String idCarta, String email){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("DELETE FROM pagamenti WHERE ID = ? AND email_utente = ?");
            ps.setString(1, idCarta);
            ps.setString(2, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateUser(String nome, String cognome, String regione, String newEmail, String data, String passHash, String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("UPDATE utente " +
                    "SET email = ?, nome = ?, cognome = ?, regione = ?, data_nascita = ?, password_hash = ?" +
                    "WHERE email = ?");

            ps.setString(1, newEmail);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            ps.setString(4, regione);
            ps.setString(5, data);
            ps.setString(6, passHash);
            ps.setString(7, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateUser(String nome, String cognome, String regione, String newEmail, String data, String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("UPDATE utente " +
                    "SET email = ?, nome = ?, cognome = ?, regione = ?, data_nascita = ?" +
                    "WHERE email = ?");

            ps.setString(1, newEmail);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            ps.setString(4, regione);
            ps.setString(5, data);
            ps.setString(6, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void saveCartOfUser(String email, List<String> idsGame) {
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("INSERT INTO carrello VALUES (?,?)");

            for (String gameId : idsGame) {
                ps.setString(1, email);
                ps.setString(2, gameId);
                ps.addBatch(); // Use addBatch for batch processing
            }
            ps.executeBatch(); // Execute the batch

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deleteCartByEmail(String email){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("DELETE FROM carrello WHERE email_utente = ?");

            ps.setString(1, email);

            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Carrello getCartByEmail(String email){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT carrello.email_utente, carrello.ID_prodotto, prodotti.nome, prodotti.data_rilascio, prodotti.prezzo, prodotti.sconto, prodotti.immagine " +
                    "FROM carrello " +
                    "LEFT JOIN prodotti ON prodotti.ID = carrello.ID_prodotto " +
                    "WHERE carrello.email_utente = ? ");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            Carrello carrello = new Carrello();
            carrello.setEmail(email);

            while (rs.next()){
                Prodotto prodotto = new Prodotto();

                prodotto.setId(rs.getInt("ID_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setPrezzoScontato();
                prodotto.setImg(rs.getString("immagine"));


                ProdottoComposto prodottoComposto = new ProdottoComposto();
                prodottoComposto.setProdotto(prodotto);

                carrello.addProdotto(prodottoComposto);
            }

            return carrello;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}

package Model.DAO;

import Model.Carrello;
import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ConPool;
import Model.Utils.ProdottoComposto;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class UserDAO {

    public void cancellaTutti(){//temporanea

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("DELETE FROM utente");
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public boolean doSave(User user) {
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("INSERT INTO utente(email, nome, cognome, regione, data_nascita, password_hash, tipo) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getNome());
            ps.setString(3, user.getCognome());
            ps.setString(4, user.getPaese());
            ps.setString(5, user.getData());
            ps.setString(6, user.getPasswordHash());
            ps.setString(7, user.getTipo().name());

            return ps.executeUpdate() != -1;

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
                user.setPaese(rs.getString("regione"));
                user.setData(rs.getString("data_nascita"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setTipo(TipoUtente.valueOf(rs.getString("tipo")));
                user.setFoto(rs.getString("foto"));

                return user;
            }

            return null;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean checkLoginUser(String email, String passwordHashata){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT password_hash FROM utente WHERE email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getString("password_hash").equals(passwordHashata)){
                    return true;
                }
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
                prodotto.setSconto(rs.getDouble("sconto"));
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

            while (rs.next()) {
                Date currentDate = rs.getDate("data_acquisto");

                // Se la data corrente è diversa dalla data dell'ultimo carrello
                if (lastDate == null || !currentDate.equals(lastDate)) {
                    // Se c'è un carrello attuale, impostiamo il totale e lo aggiungiamo alla lista
                    if (carrello != null) {
                        carrello.setTotale();
                        out.add(carrello);
                    }

                    // Creiamo un nuovo carrello per la nuova data
                    carrello = new Carrello();
                    carrello.setEmail(rs.getString("email_utente"));
                    carrello.setData(currentDate);

                    // Aggiorniamo la data dell'ultimo carrello
                    lastDate = currentDate;
                }

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

    public Map<String, List<Carrello>> getOrdiniByMonth(String email){

        List<Carrello> ordini = getOrdiniEffettuatiByEmail(email);
        ordini.sort(Comparator.comparing(Carrello::getData).reversed());

        System.out.println("Lista: "+ordini);

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
                .sorted(Map.Entry.<String, List<Carrello>>comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


}

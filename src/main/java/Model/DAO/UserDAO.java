package Model.DAO;

import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            PreparedStatement ps =
                    conn.prepareStatement("insert into utente(email, nome, cognome, regione, data_nascita, password_hash, tipo) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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

            PreparedStatement ps = conn.prepareStatement("select * from utente where email = ?");
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

            PreparedStatement ps = conn.prepareStatement("select password_hash from utente where email = ?");
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

    public boolean EmailAlreadyExists(String email){

        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select email from utente where email = ?");
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


}

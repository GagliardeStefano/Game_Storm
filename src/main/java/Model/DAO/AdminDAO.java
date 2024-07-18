package Model.DAO;
import Model.Prodotto;
import Model.Utils.ConPool;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminDAO {

    public List<String> getAllGeneri(){
        try(Connection conn = ConPool.getConnection()) {

            List<String> out = new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM genere");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                out.add(rs.getString(2));
            }

            return out;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumTotByTable(String tabella){

        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM "+tabella);
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                return rs.getInt("total");
            }
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getNumTotCarrelli(){
        try(Connection conn = ConPool.getConnection()) {


            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(DISTINCT email_utente) AS total FROM carrello;");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                return rs.getInt("total");
            }
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTotUltimoMese(){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("select sum(prezzo_prodotto) as somma from ordini where data_acquisto >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH)");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                return rs.getInt("somma");
            }

            return 0;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getGuadagno(){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("select sum(prezzo_prodotto) as somma from ordini");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                return rs.getInt("somma");
            }

            return 0;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Map<String, Object>> createRecords(ResultSet rs) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        int columnCount = rs.getMetaData().getColumnCount();

        while (rs.next()) {
            Map<String, Object> record = new LinkedHashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                int columnType = rs.getMetaData().getColumnType(i);
                Object columnValue = rs.getObject(i);

                if (columnType == Types.TIMESTAMP || columnType == Types.DATE) {
                    columnValue = rs.getString(i);
                }

                // Se la colonna è "immagini_giochi"
                if (columnName.equals("immagini_giochi")) {
                    String[] imagePaths = rs.getString(i).split(", "); // Supponendo che immagini_giochi contenga più percorsi separati da ', '
                    StringBuilder imagesHtml = new StringBuilder();
                    for (String path : imagePaths) {
                        imagesHtml.append("<img src='/GameStorm_war").append(path).append("' alt='immagine'>");
                    }
                    columnValue = imagesHtml.toString();
                }

                if (columnName.equals("immagine") || columnName.equals("foto")) {
                    columnValue = "<img src='/GameStorm_war" + rs.getString(i) + "' alt='" + columnName + "'>";
                }

                record.put(columnName, columnValue);
            }

            records.add(record);
        }

        return records;
    }

    public List<Map<String, Object>> getProdotti() {


        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT prodotti.descrizione, prodotti.nome, prodotti.data_rilascio, prodotti.prezzo, prodotti.sconto, prodotti.immagine, " +
                            "GROUP_CONCAT(genere.nome_genere ORDER BY genere.nome_genere SEPARATOR ' / ') as generi " +
                            "FROM prodotti " +
                            "JOIN prodotto_genere ON prodotti.ID = prodotto_genere.ID_prodotto " +
                            "JOIN genere ON prodotto_genere.ID_genere = genere.ID " +
                            "GROUP BY prodotti.ID, prodotti.descrizione, prodotti.nome, prodotti.data_rilascio, prodotti.prezzo, prodotti.sconto, prodotti.immagine"
            );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getProdottiWhere(String nome){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT prodotti.descrizione, prodotti.nome, prodotti.data_rilascio, prodotti.prezzo, prodotti.sconto, prodotti.immagine, " +
                            "GROUP_CONCAT(genere.nome_genere ORDER BY genere.nome_genere SEPARATOR ' / ') as generi " +
                            "FROM prodotti " +
                            "JOIN prodotto_genere ON prodotti.ID = prodotto_genere.ID_prodotto " +
                            "JOIN genere ON prodotto_genere.ID_genere = genere.ID " +
                            "WHERE prodotti.nome LIKE ?" +
                            "GROUP BY prodotti.ID, prodotti.descrizione, prodotti.nome, prodotti.data_rilascio, prodotti.prezzo, prodotti.sconto, prodotti.immagine"
            );

            ps.setString(1, "%" + nome + "%" );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getUtenti(){

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto, " +
                            "COUNT(ordini.email_utente) as numero_di_ordini, " +
                            "COUNT(DISTINCT pagamenti.ID) as numero_carte_di_credito " +
                            "FROM utente " +
                            "LEFT JOIN ordini ON utente.email = ordini.email_utente " +
                            "LEFT JOIN pagamenti ON pagamenti.email_utente = utente.email " +
                            "GROUP BY utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto"
            );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getUtentiWhere(String email){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto, " +
                            "COUNT(ordini.email_utente) as numero_di_ordini, " +
                            "COUNT(DISTINCT pagamenti.ID) as numero_carte_di_credito " +
                            "FROM utente " +
                            "LEFT JOIN ordini ON utente.email = ordini.email_utente " +
                            "LEFT JOIN pagamenti ON pagamenti.email_utente = utente.email " +
                            "WHERE utente.email LIKE ?" +
                            "GROUP BY utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto"
            );

            ps.setString(1, "%" + email + "%" );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getOridiniEffettauti(){

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT ordini.email_utente, ordini.data_acquisto, " +
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                            "GROUP_CONCAT(ordini.prezzo_prodotto ORDER BY prodotti.nome SEPARATOR ' / ') AS prezzi_giochi, " +
                            "GROUP_CONCAT(ordini.key_prodotto ORDER BY prodotti.nome SEPARATOR ' / ') AS chiavi_giochi, " +
                            "ROUND(SUM(ordini.prezzo_prodotto), 2) AS totale " +
                            "FROM ordini " +
                            "JOIN prodotti ON prodotti.ID = ordini.ID_prodotto " +
                            "GROUP BY ordini.email_utente, ordini.data_acquisto"
            );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getOridiniEffettautiWhere(String email){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT ordini.email_utente, ordini.data_acquisto, " +
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                            "GROUP_CONCAT(ordini.prezzo_prodotto ORDER BY prodotti.nome SEPARATOR ' / ') AS prezzi_giochi, " +
                            "GROUP_CONCAT(ordini.key_prodotto ORDER BY prodotti.nome SEPARATOR ' / ') AS chiavi_giochi, " +
                            "ROUND(SUM(ordini.prezzo_prodotto), 2) AS totale " +
                            "FROM ordini " +
                            "JOIN prodotti ON prodotti.ID = ordini.ID_prodotto " +
                            "WHERE ordini.email_utente LIKE ?" +
                            "GROUP BY ordini.email_utente, ordini.data_acquisto"
            );

            ps.setString(1, "%" + email + "%" );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getCarrelli(){

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT carrello.email_utente," +
                        "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                        "GROUP_CONCAT(prodotti.immagine ORDER BY prodotti.nome SEPARATOR ', ') AS immagini_giochi, "+
                        "GROUP_CONCAT(prodotti.prezzo ORDER BY prodotti.nome SEPARATOR ' / ') AS prezzi_giochi, "+
                        "GROUP_CONCAT(prodotti.sconto ORDER BY prodotti.nome SEPARATOR ' / ') AS sconti_giochi, "+
                        "ROUND(SUM(prodotti.prezzo * (1 - prodotti.sconto / 100)), 2) AS totale "+
                        "FROM carrello "+
                        "JOIN prodotti ON carrello.ID_prodotto = prodotti.ID "+
                        "GROUP BY carrello.email_utente"
            );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getCarrelliWhere(String email){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT carrello.email_utente," +
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                            "GROUP_CONCAT(prodotti.immagine ORDER BY prodotti.nome SEPARATOR ', ') AS immagini_giochi, "+
                            "GROUP_CONCAT(prodotti.prezzo ORDER BY prodotti.nome SEPARATOR ' / ') AS prezzi_giochi, "+
                            "GROUP_CONCAT(prodotti.sconto ORDER BY prodotti.nome SEPARATOR ' / ') AS sconti_giochi, "+
                            "ROUND(SUM(prodotti.prezzo * (1 - prodotti.sconto / 100)), 2) AS totale "+
                            "FROM carrello "+
                            "JOIN prodotti ON carrello.ID_prodotto = prodotti.ID " +
                            "WHERE carrello.email_utente LIKE ?"+
                            "GROUP BY carrello.email_utente"
            );

            ps.setString(1, "%" + email + "%" );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getGeneri(){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT genere.nome_genere AS genere, " +
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                            "GROUP_CONCAT(prodotti.immagine ORDER BY prodotti.nome SEPARATOR ', ') AS immagini_giochi, " +
                            "COUNT(*) AS totali " +
                            "FROM genere " +
                            "JOIN prodotto_genere ON prodotto_genere.ID_genere = genere.ID " +
                            "JOIN prodotti ON prodotto_genere.ID_prodotto = prodotti.ID " +
                            "GROUP BY genere.nome_genere"
            );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> getGeneriWhere(String nome){
        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT genere.nome_genere AS genere, " +
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ' / ') AS nomi_giochi, " +
                            "GROUP_CONCAT(prodotti.immagine ORDER BY prodotti.nome SEPARATOR ', ') AS immagini_giochi, " +
                            "COUNT(*) AS totali " +
                            "FROM genere " +
                            "JOIN prodotto_genere ON prodotto_genere.ID_genere = genere.ID " +
                            "JOIN prodotti ON prodotto_genere.ID_prodotto = prodotti.ID " +
                            "WHERE genere.nome_genere LIKE ?" +
                            "GROUP BY genere.nome_genere"
            );

            ps.setString(1, "%" + nome + "%" );

            ResultSet rs = ps.executeQuery();
            return createRecords(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllNameGames(){

        try(Connection conn = ConPool.getConnection()) {

            List<String> out = new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT nome FROM prodotti");
            ps.executeQuery();

            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                out.add(rs.getString("nome"));
            }

            return out;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean existGame(String nome){

        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("SELECT nome FROM prodotti WHERE nome = ?");
            ps.setString(1, nome);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewGame(Prodotto prodotto){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO " +
                    "prodotti(descrizione, nome, data_rilascio, prezzo, sconto, immagine, trailer) " +
                    "VALUES (?,?,?,?,?,?,?)");

            ps.setString(1, prodotto.getDescrizione());
            ps.setString(2, prodotto.getNome());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(prodotto.getDataRilascio());
            ps.setDate(3, Date.valueOf(formattedDate));

            ps.setDouble(4, prodotto.getPrezzo());
            ps.setInt(5, prodotto.getSconto());
            ps.setString(6, prodotto.getImg());
            ps.setString(7, prodotto.getTrailer());

            ps.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGeneriAtProdById(String idGame, List<String> generi){
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO prodotto_genere (ID_prodotto, ID_genere) VALUES (?, ?)");

            for (String genere : generi) {
                // Eseguire una query per recuperare l'ID del genere dal nome
                int idGenere = getIdGenereFromNome(genere); // Implementa questa funzione

                // Inserire l'associazione nella tabella gioco_genere
                ps.setString(1, idGame);
                ps.setInt(2, idGenere);
                ps.executeUpdate();
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getIdGenereFromNome(String genere) {
        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("SELECT ID FROM genere WHERE nome_genere = ?");

            ps.setString(1, genere);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
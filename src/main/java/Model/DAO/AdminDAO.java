package Model.DAO;
import Model.Utils.ConPool;
import java.sql.*;
import java.util.*;

public class AdminDAO {

    public int getNumTotByTable(String tabella){

        try(Connection conn = ConPool.getConnection()) {

            System.out.println("prima sql");
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

    public List<String> getAllNamesColumnsByTable(String nomeTabella){
        List<String> nomi = new ArrayList<>();

        try(Connection conn = ConPool.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("SHOW COLUMNS FROM "+nomeTabella);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nomi.add(rs.getString("Field"));
            }

            return nomi;

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
                            "GROUP_CONCAT(genere.nome_genere) as generi " +
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

    public List<Map<String, Object>> getUtenti(){

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto, " +
                            "COUNT(ordini.email_utente) as numero_di_ordini " +
                            "FROM utente " +
                            "LEFT JOIN ordini ON utente.email = ordini.email_utente " +
                            "GROUP BY utente.email, utente.nome, utente.cognome, utente.regione, utente.data_nascita, utente.tipo, utente.foto"
            );

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
                            "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ', ') AS nomi_giochi, " +
                            "GROUP_CONCAT(ordini.prezzo_prodotto ORDER BY prodotti.nome SEPARATOR ', ') AS prezzi_giochi, " +
                            "GROUP_CONCAT(ordini.key_prodotto ORDER BY prodotti.nome SEPARATOR ', ') AS chiavi_giochi, " +
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

    public List<Map<String, Object>> getCarrelli(){

        try (Connection conn = ConPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT carrello.email_utente," +
                        "GROUP_CONCAT(prodotti.nome ORDER BY prodotti.nome SEPARATOR ', ') AS nomi_giochi, " +
                        "GROUP_CONCAT(prodotti.immagine ORDER BY prodotti.nome SEPARATOR ', ') AS immagini_giochi, "+
                        "GROUP_CONCAT(prodotti.prezzo ORDER BY prodotti.nome SEPARATOR ', ') AS prezzi_giochi, "+
                        "GROUP_CONCAT(prodotti.sconto ORDER BY prodotti.nome SEPARATOR ', ') AS sconti_giochi, "+
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



}
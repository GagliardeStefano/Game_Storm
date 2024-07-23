package Model.DAO;

import Model.Prodotto;
import Model.Utils.ConPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.spi.AbstractResourceBundleProvider;

public class ProdottoDAO {

    public ProdottoDAO(){}
    public List<Prodotto>  doRetrieveAll(){
        List<Prodotto> prodotti = new ArrayList<>();
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotti");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getInt("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();
                prodotti.add(prodotto);
            }
            return prodotti;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Prodotto doRetrieveById(String id){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select * from Prodotti where ID=?");
            ps.setString(1, id);


            ResultSet rs = ps.executeQuery();
            if(rs.next()){

               Prodotto prodotto=new Prodotto();
               prodotto.setId(rs.getInt("ID"));
               prodotto.setDescrizione(rs.getString("descrizione"));
               prodotto.setNome(rs.getString("nome"));
               prodotto.setDataRilascio(rs.getDate("data_rilascio"));
               prodotto.setPrezzo(rs.getDouble("prezzo"));
               prodotto.setSconto(rs.getInt("sconto"));
               prodotto.setImg(rs.getString("immagine"));
               prodotto.setTrailer(rs.getString("trailer"));
               prodotto.setPrezzoScontato();

                return prodotto;
            }
            return null;


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Prodotto doRetrieveById(int id){
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotti WHERE ID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Prodotto prodotto = new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getInt("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();
                return prodotto;
            }
            return null;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetriveByGenere(int idGenere, int minPrice, int maxPrice, String queryRicerca){
        List<Prodotto> prodotti = new ArrayList<>();
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotto_genere WHERE ID_genere = ? AND nome LIKE ?");
            ps.setInt(1, idGenere);
            if(queryRicerca!=null){
                ps.setString(2, "%" + queryRicerca + "%");
            } else{
                ps.setString(2, "%%");
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int idProdotto = rs.getInt("ID_prodotto");
                Prodotto prodotto = doRetrieveById(idProdotto);
                if(prodotto != null && prodotto.getPrezzo() >= minPrice && prodotto.getPrezzo() <= maxPrice){
                    prodotti.add(prodotto);
                }else{
                    System.out.println("prodotto con id = " + idProdotto + " non trovato");
                }
            }
            return prodotti;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private String getOrdine(String order){
        String ordine;
        switch (order) {
            case "0": ordine="null"; break;
            case "discount_desc": ordine="sconto DESC";break;
            case "price_asc": ordine="prezzo_scontato";break;
            case "price_desc": ordine="prezzo_scontato DESC";break;
            case "avail_date_desc": ordine="data_rilascio DESC";break;
            case "avail_date_asc":ordine= "data_rilascio";break;
            default :throw new RuntimeException("ordine non valida");
        };
        return ordine;
    }

    public List<Prodotto> doRetrieveByOrder(String order, int minPrice, int maxPrice, String queryRicerca){
        List<Prodotto> prodotti = new ArrayList<>();
        try(Connection conn = ConPool.getConnection()) {
            String ordine = getOrdine(order);

            PreparedStatement ps = conn.prepareStatement("SELECT *, prezzo - (prezzo * sconto / 100) AS prezzo_scontato " +
                                                             "FROM prodotti " +
                                                             "WHERE prezzo - (prezzo * sconto / 100) BETWEEN ? AND ? AND nome LIKE ? " +
                                                             "ORDER BY " + ordine);
            ps.setInt(1, minPrice);
            ps.setInt(2, maxPrice);
            if(queryRicerca!=null){
                ps.setString(3, "%" + queryRicerca + "%");
            } else{
                ps.setString(3, "%%");
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                prodotti.add(prodotto);
            }
            return prodotti;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<String> doRetrieveGeneri(){
        List<String> generi = new ArrayList<>();
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM genere");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                generi.add(rs.getString("nome_genere"));
            }
            return generi;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<String> getGeneriByIdProd(String id){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT nome_genere " +
                                                             "FROM prodotto_genere JOIN genere ON prodotto_genere.ID_genere = genere.ID " +
                                                             "WHERE prodotto_genere.ID_prodotto = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            List<String> out = new ArrayList<>();

            while (rs.next()){
                out.add(rs.getString("nome_genere"));
            }

            return out;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getCorrelati(List<String> generi,String id){
        try(Connection conn = ConPool.getConnection()){
            Random random=new Random();
            List<Prodotto> prodottos=new ArrayList<>();

            int pos=0;
            if(generi.size()==2){
                pos=random.nextInt(2);
            }
            if(generi.size()==3){
                pos=random.nextInt(3);
            }
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotto_genere JOIN genere ON prodotto_genere.ID_genere = genere.ID JOIN prodotti on prodotto_genere.ID_prodotto = prodotti.ID WHERE genere.nome_genere = ? and prodotti.ID<>? LIMIT 3");
            ps.setString(1, generi.get(pos));
            ps.setString(2,id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID_prodotto"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveByFilter(int genere, String order, int minPrice, int maxPrice, String queryRicerca){
        {
            List<Prodotto> prodotti = new ArrayList<>();
            String ordine = getOrdine(order);
            try (Connection conn = ConPool.getConnection()) {
                if (genere != 0 && !ordine.equals("0")) {
                    PreparedStatement ps = conn.prepareStatement("SELECT *, prezzo - (prezzo * sconto / 100) AS prezzo_scontato FROM prodotti JOIN prodotto_genere ON prodotti.ID=prodotto_genere.ID_prodotto WHERE ID_genere=? AND prezzo - (prezzo * sconto / 100) BETWEEN ? AND ? AND nome LIKE ? ORDER BY " + ordine );
                    ps.setInt(1, genere);
                    ps.setInt(2, minPrice);
                    ps.setInt(3, maxPrice);
                    if(queryRicerca.isBlank()){
                        ps.setString(4, "%%");
                    } else{
                        ps.setString(4, "%" + queryRicerca + "%");
                    }

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                        prodotti.add(prodotto);
                    }
                } else if (genere == 0) {
                    prodotti = doRetrieveByOrder(order, minPrice, maxPrice, queryRicerca);
                } else {
                    prodotti = doRetriveByGenere(genere, minPrice, maxPrice, queryRicerca);
                }

                return prodotti;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Prodotto> doRetrieveByFiltriAndCategoria(String categoria, int genere, String order, int minPrice, int maxPrice){
        List<Prodotto> prodotti = new ArrayList<>();
        String ordine = getOrdine(order);
        try(Connection conn = ConPool.getConnection()){
            PreparedStatement delete = conn.prepareStatement("DROP VIEW IF EXISTS tmp");
            delete.executeUpdate();
            switch (categoria){
                case "In-Tendenza":
                    //caso in cui ho filtro genere
                    if(genere != 0 ) {
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "CREATE VIEW tmp AS " +
                                    "SELECT" +
                                    "    p.ID," +
                                    "    p.nome," +
                                    "    p.descrizione," +
                                    "    p.data_rilascio," +
                                    "    p.prezzo," +
                                    "    p.sconto," +
                                    "    p.immagine," +
                                    "    p.trailer," +
                                    "    COUNT(o.ID_prodotto) AS vendite, " +
                                    "   prezzo - (prezzo * sconto / 100) AS prezzo_scontato " +
                                    "FROM prodotti p " +
                                    "JOIN ordini o ON p.ID = o.ID_prodotto " +
                                    "JOIN prodotto_genere pg ON p.ID = pg.ID_prodotto " +
                                    "WHERE ID_genere = ? AND prezzo - (prezzo * sconto / 100) BETWEEN ? AND ? " +
                                    "GROUP BY p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer " +
                                    "ORDER BY vendite DESC");
                        mainStatement.setInt(1, genere);
                        mainStatement.setInt(2, minPrice);
                        mainStatement.setInt(3, maxPrice);
                        mainStatement.executeUpdate();
                        PreparedStatement view = conn.prepareStatement("SELECT * FROM tmp ORDER BY " + ordine);
                        ResultSet rs = view.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                    //caso in cui non ho filtro genere / modifico gli altri filtri
                    else {
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "CREATE VIEW tmp AS " +
                                        "SELECT" +
                                        "    p.ID," +
                                        "    p.nome," +
                                        "    p.descrizione," +
                                        "    p.data_rilascio," +
                                        "    p.prezzo," +
                                        "    p.sconto," +
                                        "    p.immagine," +
                                        "    p.trailer," +
                                        "    COUNT(o.ID_prodotto) AS vendite, " +
                                        "   prezzo - (prezzo * sconto / 100) AS prezzo_scontato " +
                                        "FROM prodotti p " +
                                        "JOIN ordini o ON p.ID = o.ID_prodotto " +
                                        "JOIN prodotto_genere pg ON p.ID = pg.ID_prodotto " +
                                        "WHERE prezzo prezzo_scontato ? AND ? " +
                                        "GROUP BY p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer " +
                                        "ORDER BY vendite DESC"
                        );
                        mainStatement.setInt(1,minPrice);
                        mainStatement.setInt(2,maxPrice);
                        mainStatement.executeUpdate();
                        PreparedStatement view = conn.prepareStatement("SELECT * FROM tmp ORDER BY " + ordine);
                        ResultSet rs = view.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                break;

                case "Preordini":
                    if(genere!= 0){
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "SELECT *, prezzo - (prezzo * sconto / 100) as prezzo_scontato " +
                                    "FROM prodotti p JOIN prodotto_genere pg ON p.ID=pg.ID_prodotto " +
                                    "WHERE ID_genere = ? AND YEAR(data_rilascio) = YEAR(CURDATE()) AND data_rilascio > CURDATE() AND prezzo - (prezzo * sconto / 100) BETWEEN ? AND ? " +
                                    "ORDER BY " + ordine
                        );
                        mainStatement.setInt(1, genere);
                        mainStatement.setInt(2, minPrice);
                        mainStatement.setInt(3, maxPrice);
                        ResultSet rs = mainStatement.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                    else {
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "SELECT *, prezzo - (prezzo * sconto / 100) as prezzo_scontato " +
                                    "FROM prodotti p " +
                                    "WHERE YEAR(data_rilascio) = YEAR(CURDATE()) AND data_rilascio > CURDATE() AND prezzo - (prezzo * sconto / 100) BETWEEN ? AND ? " +
                                    "ORDER BY " + ordine
                        );
                        mainStatement.setInt(1,minPrice);
                        mainStatement.setInt(2, maxPrice);
                        ResultSet rs = mainStatement.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                break;

                case "Prossime-Uscite" :
                    if(genere!= 0){
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "SELECT * " +
                                    "FROM prodotti p JOIN prodotto_genere pg ON p.ID=pg.ID_prodotto " +
                                    "WHERE ID_genere = ? AND YEAR(data_rilascio) > YEAR(CURDATE()) AND prezzo BETWEEN ? AND ? " +
                                    "ORDER BY " + ordine
                        );
                        mainStatement.setInt(1, genere);
                        mainStatement.setInt(2, minPrice);
                        mainStatement.setInt(3, maxPrice);
                        ResultSet rs = mainStatement.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                    else{
                        PreparedStatement mainStatement = conn.prepareStatement(
                                "SELECT * " +
                                    "FROM prodotti p " +
                                    "WHERE YEAR(data_rilascio) > YEAR(CURDATE()) AND prezzo BETWEEN ? AND ? " +
                                    "ORDER BY " + ordine
                        );
                        mainStatement.setInt(1, minPrice);
                        mainStatement.setInt(2, maxPrice);
                        ResultSet rs = mainStatement.executeQuery();
                        while (rs.next()) {
                            Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                            prodotti.add(prodotto);
                        }
                    }
                break;

                default: prodotti = null ;
            }
            return prodotti;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

    public List<Prodotto> getTendenza(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer, COUNT(o.ID_prodotto) AS vendite\n" +
                    "FROM prodotti p\n" +
                    "JOIN ordini o ON p.ID = o.ID_prodotto\n" +
                    "GROUP BY p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer\n" +
                    "ORDER BY vendite DESC\n" +
                    "LIMIT 6;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> getPreordini(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                    "FROM prodotti \n" +
                    "WHERE YEAR(data_rilascio) = YEAR(CURDATE()) \n" +
                    "  AND data_rilascio > CURDATE() LIMIT 6");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> getProssimeUscite(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                    "FROM prodotti \n" +
                    "WHERE YEAR(data_rilascio) > YEAR(CURDATE()) LIMIT 6");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getAllTendenza(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer, COUNT(o.ID_prodotto) AS vendite\n" +
                    "FROM prodotti p\n" +
                    "JOIN ordini o ON p.ID = o.ID_prodotto\n" +
                    "GROUP BY p.ID, p.nome, p.descrizione, p.data_rilascio, p.prezzo, p.sconto, p.immagine, p.trailer\n" +
                    "ORDER BY vendite DESC\n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> getAllPreordini(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                    "FROM prodotti \n" +
                    "WHERE YEAR(data_rilascio) = YEAR(CURDATE()) \n" +
                    "  AND data_rilascio > CURDATE()");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> getAllProssimeUscite(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * \n" +
                    "FROM prodotti \n" +
                    "WHERE YEAR(data_rilascio) > YEAR(CURDATE())");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> doRetrieveByCategoria(String categoria){
        List<Prodotto> prodotti;
        switch (categoria){
            case "In-Tendenza": prodotti=getTendenza(); break;
            case "Preordini": prodotti=getPreordini(); break;
            case "Prossime-Uscite" : prodotti=getProssimeUscite(); break;
            default: prodotti = null ;
        }
        return prodotti;
    }
    public List<Prodotto> doRetrieveAllByCategoria(String categoria){
        List<Prodotto> prodotti;
        switch (categoria){
            case "In-Tendenza": prodotti=getAllTendenza(); break;
            case "Preordini": prodotti=getAllPreordini(); break;
            case "Prossime-Uscite" : prodotti=getAllProssimeUscite(); break;
            default: prodotti = null ;
        }
        return prodotti;
    }
    public List<Prodotto> getCarosello(){
        try(Connection conn = ConPool.getConnection()){
            List<Prodotto> prodottos=new ArrayList<>();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM prodotti ORDER BY RAND() LIMIT 6;" );
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                prodottos.add(prodotto);
            }

            return prodottos;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Prodotto getProdByName(String nome){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("select * from Prodotti where nome=?");
            ps.setString(1, nome);


            ResultSet rs = ps.executeQuery();
            if(rs.next()){

                Prodotto prodotto=new Prodotto();
                prodotto.setId(rs.getInt("ID"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDataRilascio(rs.getDate("data_rilascio"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));
                prodotto.setSconto(rs.getInt("sconto"));
                prodotto.setImg(rs.getString("immagine"));
                prodotto.setTrailer(rs.getString("trailer"));
                prodotto.setPrezzoScontato();

                return prodotto;
            }
            return null;


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Prodotto> doRetrieveSix(){
        List<Prodotto> prodotti = new ArrayList<>();
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement("select * from Prodotti LIMIT 6");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Prodotto prodotto = doRetrieveById(rs.getInt("ID"));
                prodotti.add(prodotto);
            }
            return prodotti;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public boolean keyAlreadyExists(String key){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT key_prodotto FROM ordini WHERE key_prodotto = ?");

            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}

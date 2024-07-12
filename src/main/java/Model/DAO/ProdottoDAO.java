package Model.DAO;

import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ConPool;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProdottoDAO {
    public ProdottoDAO(){

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

    public List<String> getGeneriByIdProd(String id){
        try(Connection conn = ConPool.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT nome_genere FROM prodotto_genere JOIN genere ON prodotto_genere.ID_genere = genere.ID WHERE prodotto_genere.ID_prodotto = ?");
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
}

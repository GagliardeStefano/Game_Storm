package Model.DAO;

import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ConPool;

import java.sql.*;

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
}

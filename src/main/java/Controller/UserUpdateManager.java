package Controller;

import Model.DAO.UserDAO;
import Model.User;
import Model.Utils.ProdottoComposto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="UpdateUser", value = "/UpdateUser")
public class UserUpdateManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserDAO userDAO = new UserDAO();
        SessionManager sm = new SessionManager(req, false);

        if (sm.getSession() != null){
            if(sm.getAttribute("utente") != null){

                User user = (User) sm.getAttribute("utente");

                String from = req.getParameter("from");

                if (from != null){
                    if (from.equals("wishlist")){

                        String id = req.getParameter("IdProd");

                        if (id.equals("all")){
                            userDAO.removeAllFavorite(user.getEmail());
                        } else if(!id.isEmpty()){
                            userDAO.removeFavorite(id, user.getEmail());
                        }

                    } else if (from.equals("ordini")) {

                            String orderId = req.getParameter("orderId");

                            List<ProdottoComposto> otherGames;
                            otherGames = userDAO.getOtherGamesByOrderId(user.getEmail(), orderId);

                            if (!otherGames.isEmpty()){

                                JSONArray jsonGamesArray = getJsonArray(otherGames);

                                // tipo e encoding della risposta
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");

                                //risposta JSON nella risposta HTTP
                                PrintWriter out = resp.getWriter();
                                out.print(jsonGamesArray.toJSONString());
                                out.flush();
                            }
                    }
                }

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private static JSONArray getJsonArray(List<ProdottoComposto> otherGames) {
        JSONArray jsonGamesArray = new JSONArray();
        for (ProdottoComposto prodottoComposto : otherGames) {
            JSONObject jsonGame = new JSONObject();

            jsonGame.put("idProd", prodottoComposto.getProdotto().getId());
            jsonGame.put("nome", prodottoComposto.getProdotto().getNome());
            jsonGame.put("img", prodottoComposto.getProdotto().getImg());
            jsonGame.put("keyProd", prodottoComposto.getKey());
            jsonGame.put("prezzo", prodottoComposto.getPrezzo());


            jsonGamesArray.add(jsonGame);
        }

        return jsonGamesArray;
    }
}

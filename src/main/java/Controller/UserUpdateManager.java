package Controller;

import Model.DAO.UserDAO;
import Model.User;
import Model.Utils.ProdottoComposto;
import Model.Utils.Validator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="UpdateUser", value = "/UpdateUser")
public class UserUpdateManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserDAO userDAO = new UserDAO();
        SessionManager sm = new SessionManager(req, false);
        Validator validator = new Validator();

        if (sm.getSession() != null){
            if(sm.getAttribute("user") != null){

                User user = (User) sm.getAttribute("user");

                String from = req.getParameter("from");

                if (from != null){
                    switch (from) {

                        case "wishlist":

                            String id = req.getParameter("IdProd");

                            if (id.equals("all")) {
                                userDAO.removeAllFavorite(user.getEmail());
                            } else if (!id.isEmpty()) {
                                userDAO.removeFavorite(id, user.getEmail());
                            }

                            break;

                        case "ordini":

                            String orderId = req.getParameter("orderId");

                            List<ProdottoComposto> otherGames;
                            otherGames = userDAO.getOtherGamesByOrderId(user.getEmail(), orderId);

                            if (!otherGames.isEmpty()) {

                                JSONArray jsonGamesArray = getJsonArray(otherGames);

                                // tipo e encoding della risposta
                                resp.setContentType("application/json");
                                resp.setCharacterEncoding("UTF-8");

                                //risposta JSON nella risposta HTTP
                                PrintWriter out = resp.getWriter();
                                out.print(jsonGamesArray.toJSONString());
                                out.flush();
                            }

                            break;

                        case "game":
                            String type = req.getParameter("type");
                            String idGame = req.getParameter("id");
                            if(type.equals("idAdd")){
                                userDAO.addFavourite(idGame, user.getEmail());
                            }
                            else {
                                userDAO.removeFavorite(idGame, user.getEmail());
                            }
                            break;

                        case "metodi":

                            String idCarta = req.getParameter("IdCarta");
                            String numero = req.getParameter("numero");
                            String data = req.getParameter("data");
                            String cvv = req.getParameter("cvv");
                            String nome = req.getParameter("nome");
                            String cognome = req.getParameter("cognome");

                            validator.validateCarta(numero, data, cvv, nome, cognome);

                            if (validator.hasErrors() || idCarta.isEmpty()){

                                req.setAttribute("erroriCarta", validator.getErrors());
                                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                                rd.forward(req, resp);

                            }else {

                                userDAO.updateCartaCredito(idCarta, user.getEmail(), numero, data, cvv, nome, cognome);

                            }
                            break;

                        case "metodiRem":
                            String idCartaRem = req.getParameter("IdCarta");
                            userDAO.removeCartaCredito(idCartaRem, user.getEmail());
                            break;

                        case "modifica":
                            String newNome = req.getParameter("nome");
                            String newCognome = req.getParameter("cognome");
                            String newEmail = req.getParameter("email");
                            String newRegione = req.getParameter("regione");
                            String newData = req.getParameter("data");
                            String newPassword = req.getParameter("new-pass");

                            if (!newPassword.isEmpty()){

                                validator.validateAll(newNome, newCognome, newRegione, newEmail, newPassword, newData);

                                if (validator.hasErrors()){
                                    req.setAttribute("errori", validator.getErrors());
                                    req.getRequestDispatcher("WEB-INF/results/account.jsp").forward(req, resp);
                                }


                                User temp = new User();
                                temp.setPassword(newPassword);
                                try {
                                    temp.setPasswordHash();
                                } catch (NoSuchAlgorithmException e) {
                                    throw new RuntimeException(e);
                                }

                                userDAO.updateUser(newNome, newCognome, newRegione, newEmail, newData, temp.getPasswordHash(), user.getEmail());

                            }else {


                                validator.validateAll(newNome, newCognome, newRegione, newEmail, user.getPassword(), newData);

                                if (validator.hasErrors()){
                                    req.setAttribute("errori", validator.getErrors());
                                    req.getRequestDispatcher("WEB-INF/results/account.jsp").forward(req, resp);
                                }

                                userDAO.updateUser(newNome, newCognome, newRegione, newEmail, newData, user.getEmail());
                            }

                            user = userDAO.doRetrieveByEmail(newEmail);
                            sm.setAttribute("user", user);
                            break;

                        case "changeAvatar":

                            System.out.println("change");
                            String newPath = req.getParameter("path");
                            System.out.println(newPath);
                            userDAO.changeAvatar(user.getEmail(), newPath);

                            user = userDAO.doRetrieveByEmail(user.getEmail());
                            sm.setAttribute("user", user);
                            break;

                        case "logout":
                            sm.getSession().invalidate();
                            req.getRequestDispatcher("index.jsp").forward(req, resp);
                            break;
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
            jsonGame.put("prezzo", String.format("%.2f", prodottoComposto.getPrezzo()));

            jsonGamesArray.add(jsonGame);
        }

        return jsonGamesArray;
    }
}

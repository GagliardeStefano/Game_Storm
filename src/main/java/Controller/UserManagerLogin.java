package Controller;

import Model.Carrello;
import Model.CartaCredito;
import Model.DAO.UserDAO;
import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.Validator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserManagerLogin", value = "/UserManagerLogin")
public class UserManagerLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Validator validator = new Validator();
        SessionManager sessionManager = new SessionManager(req, false);
        List<String> errore = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        User check = new User();

        RequestDispatcher dispatcher;

        if (sessionManager.getSession() == null){
            req.getRequestDispatcher("/WEB-INF/results/login.jsp").forward(req, resp);
        }else {
            check = (User) sessionManager.getAttribute("user");
        }

        if(check != null){
            switch (check.getTipo()){
                case Semplice:
                    try {
                        caricaStrutturePerUtente(check.getEmail(), sessionManager, check.getTipo());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                    dispatcher.forward(req, resp);
                    break;

                case Admin1:
                case Admin2:
                    dispatcher = req.getRequestDispatcher("/AdminManager");
                    dispatcher.forward(req, resp);
                    break;
            }

        }else {


            String email = req.getParameter("Email");
            String password = req.getParameter("Password");

            validator.validateAll(email, password);

            if (validator.hasErrors()){//errori pattern
                req.setAttribute("errori", validator.getErrors());
                dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=l");
                dispatcher.forward(req, resp);

            }else {

                if (!userDAO.emailAlreadyExists(email)){ //email non trovata nel DB

                    errore.add("Email non trovata, riprova o Registrati");
                    validator = new Validator();
                    validator.setErrors(errore);
                    req.setAttribute("errori", validator.getErrors());
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=l");
                    dispatcher.forward(req, resp);

                }else { //trovata


                    User tempUser = new User();
                    tempUser.setPassword(password);

                    try {
                        tempUser.setPasswordHash();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    if(userDAO.checkLoginUser(email, tempUser.getPasswordHash())){ //check delle credenziali

                        User user = userDAO.doRetrieveByEmail(email);
                        user.setPassword(password);
                        sessionManager = new SessionManager(req, true);

                        switch (user.getTipo()){
                            case Semplice:

                                try {
                                    caricaStrutturePerUtente(email, sessionManager, user.getTipo());
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                sessionManager.setAttribute("user", user);
                                dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                                dispatcher.forward(req, resp);
                                break;

                            case Admin1:
                            case Admin2:
                                try {
                                    caricaStrutturePerUtente(email, sessionManager, user.getTipo());
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                sessionManager.setAttribute("user", user);
                                resp.sendRedirect("https://localhost:8443/GameStorm-1.0-SNAPSHOT/AdminManager");
                                break;

                        }

                    }else {

                        errore.add("Email o Password non corretti, riprova");
                        validator = new Validator();
                        validator.setErrors(errore);
                        req.setAttribute("errori", validator.getErrors());
                        dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=l");
                        dispatcher.forward(req, resp);
                    }

                }

            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void caricaStrutturePerUtente(String email, SessionManager sessionManager, TipoUtente tipo) throws ParseException {

        UserDAO userDAO = new UserDAO();

        List<Prodotto> wishlist;
        List<CartaCredito> metodiPagamento;
        Map<String, List<Carrello>> ordini;

        if (tipo == TipoUtente.Semplice){
            wishlist = userDAO.getWishlistByEmail(email);
            ordini = userDAO.getOrdiniByMonth(email);
            metodiPagamento = userDAO.getMetodiPagamentoByEmail(email);

            sessionManager.setAttribute("wishlist", wishlist);
            sessionManager.setAttribute("ordini", ordini);
            sessionManager.setAttribute("carte", metodiPagamento);
        }

        setCarrelloInSessione(sessionManager, email);

    }

    static void setCarrelloInSessione(SessionManager sessionManager, String email) {
        UserDAO userDAO = new UserDAO();
        Carrello carrello;

        carrello = userDAO.getCartByEmail(email);

        if (!carrello.getProdotti().isEmpty()){

            sessionManager.setAttribute("carrello", carrello);

        }else if (sessionManager.getAttribute("carrello") == null){

            carrello = new Carrello();
            sessionManager.setAttribute("carrello", carrello);
        }else {
            carrello = (Carrello) sessionManager.getAttribute("carrello");
            sessionManager.setAttribute("carrello", carrello);
        }
    }
}

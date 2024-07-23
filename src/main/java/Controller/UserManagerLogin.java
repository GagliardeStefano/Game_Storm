package Controller;

import Model.Carrello;
import Model.CartaCredito;
import Model.DAO.UserDAO;
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
                            caricaStrutturePerUtente(check.getEmail(), sessionManager);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                        dispatcher.forward(req, resp);
                        break;

                    case Admin1:
                    case Admin2:
                        dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
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
                                        caricaStrutturePerUtente(email, sessionManager);
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    sessionManager.setAttribute("user", user);
                                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                                    dispatcher.forward(req, resp);
                                    break;

                                case Admin1:
                                case Admin2:
                                    sessionManager.setAttribute("user", user);
                                    resp.sendRedirect("http://localhost:8080/GameStorm_war/AdminManager");
                                    /*TODO cambiare forse per https */
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

    private void caricaStrutturePerUtente(String email, SessionManager sessionManager) throws ParseException {

        UserDAO userDAO = new UserDAO();

        List<Prodotto> wishlist;
        List<CartaCredito> metodiPagamento;
        Map<String, List<Carrello>> ordini;
        Carrello carrello;


        wishlist = userDAO.getWishlistByEmail(email);
        ordini = userDAO.getOrdiniByMonth(email);
        metodiPagamento = userDAO.getMetodiPagamentoByEmail(email);
        carrello = userDAO.getCartByEmail(email);

        sessionManager.setAttribute("wishlist", wishlist);
        sessionManager.setAttribute("ordini", ordini);
        sessionManager.setAttribute("carte", metodiPagamento);
        if (!carrello.getProdotti().isEmpty())
            sessionManager.setAttribute("carrello", carrello);
    }
}

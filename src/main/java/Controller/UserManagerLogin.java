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

        /*TODO vedere il perchè se invio l'url non si vede nulla*/

        if (sessionManager.getSession() == null){
            req.getRequestDispatcher("/WEB-INF/results/login.jsp").forward(req, resp);
        }else {
            check = (User) sessionManager.getAttribute("utente");
        }

            List<Prodotto> wishlist;
            List<CartaCredito> metodiPagamento;
            Map<String, List<Carrello>> ordini;

            if(check != null){



                wishlist = userDAO.getWishlistByEmail(check.getEmail());


                ordini = userDAO.getOrdiniByMonth(check.getEmail());


                metodiPagamento = userDAO.getMetodiPagamentoByEmail(check.getEmail());


                sessionManager.setAttribute("wishlist", wishlist);
                sessionManager.setAttribute("ordini", ordini);
                sessionManager.setAttribute("carte", metodiPagamento);


                dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                dispatcher.forward(req, resp);

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
                            wishlist = userDAO.getWishlistByEmail(email);
                            ordini = userDAO.getOrdiniByMonth(email);
                            metodiPagamento = userDAO.getMetodiPagamentoByEmail(email);

                            sessionManager = new SessionManager(req, true);

                            sessionManager.setAttribute("utente", user);
                            sessionManager.setAttribute("wishlist", wishlist);
                            sessionManager.setAttribute("ordini", ordini);
                            sessionManager.setAttribute("carte", metodiPagamento);

                            dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                            dispatcher.forward(req, resp);

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
}

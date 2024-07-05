package Controller;

import Model.Carrello;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UserManagerRegister", value = "/UserManagerRegister")
public class UserManagerRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionManager sessionManager = new SessionManager(req, false);
        User check = new User();

        if (sessionManager.getSession() == null){
            req.getRequestDispatcher("/WEB-INF/results/login.jsp").forward(req, resp);
        }else {
            check = (User) sessionManager.getAttribute("utente");
        }

        List<Prodotto> wishlist;
        Map<String, List<Carrello>> ordini;
        Validator validator = new Validator();
        UserDAO userDAO = new UserDAO();
        RequestDispatcher dispatcher;

        if (check != null) {

            wishlist = userDAO.getWishlistByEmail(check.getEmail());
            ordini = userDAO.getOrdiniByMonth(check.getEmail());
            sessionManager.setAttribute("wishlist", wishlist);
            sessionManager.setAttribute("ordini", ordini);
            dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
            dispatcher.forward(req, resp);

        }else {

            String email = req.getParameter("Email");
            String password = req.getParameter("Password");
            String nome = req.getParameter("Nome");
            String cognome = req.getParameter("Cognome");
            String data = req.getParameter("Data");
            String paese = req.getParameter("Country");

            //controllo errori
            validator.validateAll(nome, cognome, paese, email, password, data);

            if (validator.hasErrors()){

                req.setAttribute("errori", validator.getErrors());
                dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=r");
                dispatcher.forward(req, resp);

            }else {

                if (userDAO.emailAlreadyExists(email)){

                    List<String> errore = new ArrayList<>();
                    errore.add("Email già registrata, prova ad accedere");
                    validator.setErrors(errore);
                    req.setAttribute("errori", validator.getErrors());
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=r");
                    dispatcher.forward(req, resp);

                }

                User user = new User(nome, cognome, email, password, data, paese);
                user.setTipo(TipoUtente.Semplice);
                try {
                    user.setPasswordHash();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }


                if (userDAO.doSave(user)){//salvo utente nel DB
                    //prendo wishlist
                    wishlist = userDAO.getWishlistByEmail(user.getEmail());

                    ordini = userDAO.getOrdiniByMonth(user.getEmail());

                    //salvo nella sessione
                    sessionManager = new SessionManager(req, true);
                    sessionManager.setAttribute("utente", user);
                    sessionManager.setAttribute("wishlist", wishlist);
                    sessionManager.setAttribute("ordini", ordini);

                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");
                    dispatcher.forward(req, resp);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

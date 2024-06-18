package Controller;

import Model.Enum.TipoUtente;
import Model.User;
import Model.Utils.Validator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "UserManager", value = "/UserManager")
public class UserManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("utente");

        RequestDispatcher dispatcher;
        if (user != null) { //esiste la sessione

            dispatcher = req.getRequestDispatcher("/WEB-INF/results/account.jsp");

        }else { //non esiste

            dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp");
        }
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String tipo = req.getParameter("t");
        if (tipo.equals("l")) { //LOGIN

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            //check, prendo l'utente dal database e lo aggiungo come attributo alla sessione...


        }else{ //REGISTRAZIONE

            String email = req.getParameter("Email");
            String password = req.getParameter("Password");
            String nome = req.getParameter("Nome");
            String cognome = req.getParameter("Cognome");
            String data = req.getParameter("Data");
            String paese = req.getParameter("Country");

            Validator validator = new Validator();
            validator.validateAll(nome, cognome, paese, email, password, data);

            if (!validator.hasErrors()) { //tutto ok

                User user = new User(nome, cognome, email, password, data, paese);
                user.setTipo(TipoUtente.Semplice);
                try {
                    user.setPasswordHash();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                //Aggiungo utente nel database...

                //creo sessione
                HttpSession session = req.getSession(true);
                session.setAttribute("utente", user);

                //indirizzo alla home o pagina utente
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.forward(req, resp);

            }else { //c'è almeno un errore

                req.setAttribute("errori", validator.getErrors());
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=r");
                dispatcher.forward(req, resp);
            }
        }
    }
}

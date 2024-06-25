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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "UserManager", value = "/UserManager")
public class UserManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        SessionManager sessionManager = new SessionManager(req, false);
        User user = (User) sessionManager.getAttribute("utente");

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

        Validator validator = new Validator();
        String tipo = req.getParameter("t");

        if (tipo.equals("l")) { //LOGIN

            String email = req.getParameter("Email");
            String password = req.getParameter("Password");
            validator.validateAll(email, password);

            if (!validator.hasErrors()){ //tutto ok per la sintassi degli input

                /*TODO check delle credenziali dell'utente dal DB (se esiste o non esiste o se admin) */

                RequestDispatcher dispatcher = req.getRequestDispatcher("/UserManagerLogin");
                dispatcher.forward(req, resp);

            }else {
                req.setAttribute("errori", validator.getErrors());
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=l");
                dispatcher.forward(req, resp);
            }

        }else if (tipo.equals("r")){ //REGISTRAZIONE

            String email = req.getParameter("Email");
            String password = req.getParameter("Password");
            String nome = req.getParameter("Nome");
            String cognome = req.getParameter("Cognome");
            String data = req.getParameter("Data");
            String paese = req.getParameter("Country");


            validator.validateAll(nome, cognome, paese, email, password, data);

            if (!validator.hasErrors()) { //tutto ok per la sintassi degli input

                req.setAttribute("newUser", new User(nome, cognome, email, password, data, paese));
                RequestDispatcher dispatcher = req.getRequestDispatcher("/UserManagerRegister");
                dispatcher.forward(req, resp);

            }else { //c'è almeno un errore

                req.setAttribute("errori", validator.getErrors());
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/login.jsp?t=r");
                dispatcher.forward(req, resp);
            }


        }
    }
}

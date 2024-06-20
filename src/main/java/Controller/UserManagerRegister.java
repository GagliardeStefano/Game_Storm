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

@WebServlet(name = "UserManagerRegister", value = "/UserManagerRegister")
public class UserManagerRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        User user = (User) req.getAttribute("newUser");
        user.setTipo(TipoUtente.Semplice);
        try {
            user.setPasswordHash();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        /*TODO aggiungo l'utente nel database*/

        //creo sessione
        SessionManager sessionManager = new SessionManager(req);
        sessionManager.setAttribute("utente", user);

        //indirizzo alla home o pagina utente
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

package Controller;

import Model.DAO.UserDAO;
import Model.Prodotto;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserManager", value = "/UserManager")
public class UserManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionManager sessionManager = new SessionManager(req, false);
        UserDAO userDAO = new UserDAO();
        //userDAO.cancellaTutti();
        RequestDispatcher dispatcher;

        if (sessionManager.getSession() == null){//non esiste sessione

            new SessionManager(req, true);
            dispatcher = req.getRequestDispatcher("WEB-INF/results/login.jsp");

        }else {//esiste sessione

            if (sessionManager.getAttribute("utente") == null){//non esiste utente
                dispatcher = req.getRequestDispatcher("WEB-INF/results/login.jsp");
            }else {
                User user = (User) sessionManager.getAttribute("utente");
                List<Prodotto> wishlist = userDAO.getWishlistByEmail(user.getEmail());
                sessionManager.setAttribute("wishlist", wishlist);
                dispatcher = req.getRequestDispatcher("WEB-INF/results/account.jsp");
            }
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

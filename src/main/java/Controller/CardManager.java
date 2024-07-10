package Controller;

import Model.DAO.ProdottoDAO;
import Model.DAO.UserDAO;
import Model.Prodotto;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CardManager", value = "/CardManager")

public class CardManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        boolean preferito = false;
        UserDAO userDAO = new UserDAO();
        SessionManager sessionManager = new SessionManager(req,false);
        if(sessionManager.getSession() != null)
        {
            User user = (User) sessionManager.getAttribute("utente");
            if(user != null){
                List<Prodotto> wishlist = userDAO.getWishlistByEmail(user.getEmail());
                for (Prodotto p : wishlist){
                    if(p.getId() == Integer.parseInt(id)){
                        preferito = true;
                        req.setAttribute("preferito",preferito);
                        break;
                    }
                }
            }
        }

        if(id.matches("\\d+")){

            Prodotto prodotto = prodottoDAO.doRetrieveById(id);

            if(prodotto != null){
                req.setAttribute("prodotto",prodotto);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/game.jsp");
                dispatcher.forward(req,resp);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}

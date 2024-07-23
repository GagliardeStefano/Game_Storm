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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "CardManager", value = "/CardManager")

public class GameManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        SessionManager sessionManager = new SessionManager(req,false);

        if(sessionManager.getSession() != null)
        {
            checkIfFavorite(sessionManager, id, req);
        }

        if(id.matches("\\d+")){

            Prodotto prodotto = prodottoDAO.doRetrieveById(id);

            if(prodotto != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(prodotto.getDataRilascio());
                int annoCarta = cal.get(Calendar.YEAR);
                int annoCorrente = LocalDate.now().getYear();

                List<String> generi = prodottoDAO.getGeneriByIdProd(id);

                req.setAttribute("prodotto",prodotto);
                req.setAttribute("generi", generi);
                List<Prodotto> prodotti=prodottoDAO.getCorrelati(generi,id);
                req.setAttribute("prodotti",prodotti);
                req.setAttribute("annoCarta",annoCarta);
                req.setAttribute("annoCorrente",annoCorrente);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/game.jsp");
                dispatcher.forward(req,resp);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    public void checkIfFavorite(SessionManager sessionManager, String id, HttpServletRequest request){

        UserDAO userDAO = new UserDAO();
        User user = (User) sessionManager.getAttribute("user");
        if(user != null){
            List<Prodotto> wishlist = userDAO.getWishlistByEmail(user.getEmail());
            for (Prodotto p : wishlist){
                if(p.getId() == Integer.parseInt(id)){
                    request.setAttribute("preferito", true);
                    break;
                }
            }
        }
    }
}

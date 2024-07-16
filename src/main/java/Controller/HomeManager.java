package Controller;

import Model.DAO.ProdottoDAO;
import Model.Prodotto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeManager", value = "/index" ,loadOnStartup = 1)
public class HomeManager extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> tendenza = prodottoDAO.getTendenza();
        getServletContext().setAttribute("tendenze", tendenza);

        List<Prodotto> preordini = prodottoDAO.getPreordini();
        getServletContext().setAttribute("preordini",preordini);

        List<Prodotto> prossimeUscite = prodottoDAO.getProssimeUscite();
        getServletContext().setAttribute("prossimeUscite",prossimeUscite);

        List<Prodotto> carosello=prodottoDAO.getCarosello();
        getServletContext().setAttribute("carosello",carosello);

    }
}

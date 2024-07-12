package Controller;

import Model.DAO.ProdottoDAO;
import Model.Prodotto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class HomeManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProdottoDAO prodottoDAO=new ProdottoDAO();
        List<Prodotto> tendenza=prodottoDAO.getTendenza();
        req.setAttribute("tendenza",tendenza);

    }
}

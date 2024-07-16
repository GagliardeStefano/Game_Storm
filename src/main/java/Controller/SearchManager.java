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


@WebServlet(name = "SearchManager", value = "/SearchManager")
public class SearchManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProdottoDAO prodottoDAO=new ProdottoDAO();
        String query = req.getParameter("q");
        if (query == null || query.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<Prodotto> result = prodottoDAO.searchProducts(query);
        req.setAttribute("result",result);
        req.getRequestDispatcher("/WEB-INF/results/result.jsp");


    }
}

package Controller;

import Model.DAO.AdminDAO;
import Model.DAO.ProdottoDAO;
import Model.Prodotto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@WebServlet(name = "SearchManager", value = "/search")
public class SearchManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        AdminDAO adminDAO = new AdminDAO();
        List<Map<String, Object>> records = new ArrayList<>();


        String query = req.getParameter("q");
        String table = req.getParameter("t");

        if (query == null || query.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (table){
            case "prodotti":
                records = adminDAO.getProdottiWhere(query);
                break;

            case "utente":
                records = adminDAO.getUtentiWhere(query);
                break;

            case "ordini":
                records = adminDAO.getOridiniEffettautiWhere(query);
                break;
            case "carrello":
                records = adminDAO.getCarrelliWhere(query);
                break;
            case "genere":
                records = adminDAO.getGeneriWhere(query);
                break;

        }

        JSONObject jsonResponse = new JSONObject();
        if (records.isEmpty()){
            jsonResponse.put("records", null);
        }else {
            jsonResponse.put("records", records);   // Aggiungi i record
        }

        // Converti l'oggetto JSON in una stringa
        String jsonString = jsonResponse.toJSONString();


        // Imposta il tipo di contenuto della risposta come JSON
        resp.setContentType("application/json");

        // Scrivi la risposta JSON al client
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();


    }
}

package Controller;

import Model.DAO.ProdottoDAO;
import Model.Prodotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "CategoriaManager", value = "/CategoriaManager")
public class CategoriaManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO pDAO = new ProdottoDAO();
        String categoria = request.getParameter("categoria");
        List<String> generi = pDAO.doRetrieveGeneri();
        List<Prodotto> giochi = pDAO.doRetrieveByCategoria(categoria);
        request.setAttribute("generi", generi);
        request.setAttribute("giochi", giochi);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/results/categoria.jsp?categoria=" + categoria);
        dispatcher.forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO pDAO = new ProdottoDAO();
        int genere = Integer.parseInt(request.getParameter("genere"));
        String ordine = request.getParameter("ordine");
        int prezzoMin = Integer.parseInt(request.getParameter("minPrice"));
        int prezzoMax = Integer.parseInt(request.getParameter("maxPrice"));
        String categoria = request.getParameter("categoria");
        int offset;
        if(request.getParameter("offset") != null) {
            offset = Integer.parseInt(request.getParameter("offset"));
        }
        else{
            offset = 3;
        }
        List<Prodotto> prodotti;


        if(genere != 0 || !ordine.equals("0") || prezzoMin!= 0|| prezzoMax!= 200) {
            prodotti=pDAO.doRetrieveByFiltriAndCategoria(categoria, genere, ordine, prezzoMin, prezzoMax);
        } else{
            prodotti = pDAO.doRetrieveAllByCategoria(categoria);
        }

            JSONArray jsonProdottiArray = getJsonArray(prodotti, offset);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonProdottiArray.toJSONString());
            out.flush();
    }

    private static JSONArray getJsonArray(List<Prodotto> prodotti, int offset) {
        JSONArray jsonGamesArray = new JSONArray();
        List<Prodotto> prodottiMostrati = new ArrayList<>();
        for(int i = 0; i < offset+3; i++){
            if(i < prodotti.size()){
                prodottiMostrati.add(prodotti.get(i));
            }
        }
        for (Prodotto prodotto : prodottiMostrati) {
            JSONObject jsonGame = new JSONObject();

            jsonGame.put("id", prodotto.getId());
            jsonGame.put("nome", prodotto.getNome());
            jsonGame.put("img", prodotto.getImg());
            jsonGame.put("prezzo", prodotto.getPrezzo());
            jsonGame.put("sconto", prodotto.getSconto());
            jsonGame.put("prezzoScontato", prodotto.getPrezzoScontato());

            jsonGamesArray.add(jsonGame);
        }
        JSONObject numProdotti = new JSONObject();
        numProdotti.put("numProdotti", prodotti.size());
        jsonGamesArray.add(numProdotti);

        return jsonGamesArray;
    }


}

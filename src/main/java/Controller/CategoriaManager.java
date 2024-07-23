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
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "CategoriaManager", value = "/CategoriaManager")
public class CategoriaManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO pDAO = new ProdottoDAO();
        String categoria = request.getParameter("categoria");
        List<String> generi = pDAO.doRetrieveGeneri();
        List<Prodotto> giochi;
        RequestDispatcher dispatcher;

        if(categoria == null){
            giochi = pDAO.doRetrieveSix();
            String query = request.getParameter("search");
            dispatcher = request.getRequestDispatcher("/WEB-INF/results/categoria.jsp?categoria=search&q=" + query);
        } else {
            if (categoria.equals("search")) {
                giochi = pDAO.doRetrieveSix();
            } else {
                giochi = pDAO.doRetrieveByCategoria(categoria);
            }
            dispatcher = request.getRequestDispatcher("/WEB-INF/results/categoria.jsp?categoria=" + categoria);
        }
        request.setAttribute("generi", generi);
        request.setAttribute("giochi", giochi);
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
        String searchQuery = request.getParameter("q");

        List<Prodotto> prodotti;

        //almeno un filtro modificato senza search query o con query vuota
        if((genere != 0 || !ordine.equals("0") || prezzoMin!= 0|| prezzoMax!= 200) && searchQuery.isBlank() ) {
            if(categoria == null){
                prodotti = pDAO.doRetrieveByFilter(genere, ordine, prezzoMin, prezzoMax, searchQuery);
            } else {

                if (categoria.equals("search")) {
                    //query vuota
                    prodotti = pDAO.doRetrieveByFilter(genere, ordine, prezzoMin, prezzoMax, searchQuery);
                } else {
                    //senza query
                    prodotti = pDAO.doRetrieveByFiltriAndCategoria(categoria, genere, ordine, prezzoMin, prezzoMax);
                }
            }
        } // con search query
        else if(searchQuery != null && !searchQuery.isEmpty()) {
            request.setAttribute("q", searchQuery);
            prodotti = pDAO.doRetrieveByFilter(genere, ordine, prezzoMin, prezzoMax, searchQuery);
        } //caso base
        else{
            if(categoria == null || categoria.equals("search")){
                prodotti = pDAO.doRetrieveAll();
            } else {
                prodotti = pDAO.doRetrieveAllByCategoria(categoria);
            }

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

package Controller;

import Model.Carrello;
import Model.DAO.AdminDAO;
import Model.DAO.ProdottoDAO;
import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.ProdottoComposto;
import com.mysql.cj.Session;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "CartManager", value = "/CartManager")

public class CartManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sm = new SessionManager(req, false);
        if(sm.getSession() != null){
            Carrello carrello = (Carrello) sm.getAttribute("carrello");
            if (carrello == null) {
                carrello = new Carrello(); // Crea un nuovo carrello se non esiste
                sm.setAttribute("carrello", carrello);
            }

        }
        else {
            sm = new SessionManager(req,true);
            Carrello carrello = new Carrello();
            sm.setAttribute("carrello", carrello);
            int numProductsInCart = carrello.getProdotti().size();
            resp.setContentType("text/plain");
            resp.getWriter().write(numProductsInCart);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/carrello.jsp");
        dispatcher.forward(req,resp);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String type = req.getParameter("type");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(id);
        AdminDAO dao = new AdminDAO();
        SessionManager sm = new SessionManager(req, false);
        RequestDispatcher rd;
        String action = req.getParameter("action");

        if ("cartCount".equals(action)) {
            handleCartCount(req, resp);
            return;
        }

        // Recupera la sessione
        if (sm.getSession() != null){

            if (type.equals("add")){
                // Controlla se il carrello esiste già nella sessione
                Carrello carrello = (Carrello) sm.getAttribute("carrello");
                if (carrello == null) {
                    carrello = new Carrello();
                }

                // Aggiungi il prodotto al carrello
                if (prodotto != null) {
                    ProdottoComposto prodottoComposto = new ProdottoComposto();
                    prodottoComposto.setProdotto(prodotto);
                    carrello.addProdotto(prodottoComposto);
                    sm.setAttribute("carrello", carrello);// Salva il carrello aggiornato nella sessione


                }
            }else {
                Carrello carrello = (Carrello) sm.getAttribute("carrello");
                ProdottoComposto prodottoComposto = new ProdottoComposto();
                prodottoComposto.setProdotto(prodotto);
                carrello.removeProdotto(prodottoComposto);
                sm.setAttribute("carrello", carrello);
                // Create the response string
                double totale = carrello.getTotale();
                double scontoTotale = carrello.getScontoTotale();
                double prezzoScontatoTotale = carrello.getPrezzoScontatoTotale();
                String responseText = totale + ";" + scontoTotale + ";" + prezzoScontatoTotale;
                resp.setContentType("text/plain");
                resp.getWriter().write(responseText);
            }




        }
        else {
            sm = new SessionManager(req,true);
            if (type.equals("add")){

                // Aggiungi il prodotto al carrello
                if (prodotto != null) {
                    ProdottoComposto prodottoComposto = new ProdottoComposto();
                    prodottoComposto.setProdotto(prodotto);
                    Carrello carrello = new Carrello();

                    carrello.addProdotto(prodottoComposto);
                    sm.setAttribute("carrello", carrello);// Salva il carrello aggiornato nella sessione

                }
            }
        }
    }
    private void handleCartCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SessionManager sm = new SessionManager(req, false);
        Carrello carrello = (Carrello) sm.getAttribute("carrello");

        int numProductsInCart = 0;
        if (carrello != null) {
            numProductsInCart = carrello.getProdotti().size();
        }

        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(numProductsInCart));
    }
}

package Controller;

import Model.Carrello;
import Model.DAO.ProdottoDAO;
import Model.DAO.UserDAO;
import Model.Prodotto;
import Model.User;
import Model.Utils.ProdottoComposto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



@WebServlet(name = "CartManager", value = "/CartManager")

public class CartManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionManager sm = new SessionManager(req, false);
        UserDAO userDAO = new UserDAO();
        Carrello carrello;

        if(sm.getSession() != null){

            carrello = (Carrello) sm.getAttribute("carrello");

            if (carrello == null) {
                carrello = new Carrello();
            }

        }else {
            sm = new SessionManager(req, true);
            carrello = new Carrello();
        }

        sm.setAttribute("carrello", carrello);
        User user = (User) sm.getAttribute("user");
        if (user != null){
            sm.setAttribute("wishlist", userDAO.getWishlistByEmail(user.getEmail()));
        }


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/results/carrello.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String type = req.getParameter("type");
        String action = req.getParameter("action");

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        UserDAO userDAO = new UserDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(id);
        SessionManager sm = new SessionManager(req, false);
        Carrello carrello;

        if ("cartCount".equals(action)) {
            handleCartCount(req, resp);
            return;
        }

        if (sm.getSession() != null){


            User user = (User) sm.getAttribute("user");

            if (user != null){

                Carrello carrelloUser = userDAO.getCartByEmail(user.getEmail());
                carrello = (Carrello) sm.getAttribute("carrello");

                if (!carrelloUser.getProdotti().isEmpty() && carrelloUser.getProdotti() == carrello.getProdotti()){
                     sm.setAttribute("carrello", carrelloUser);
                    carrello = (Carrello) sm.getAttribute("carrello");
                }else {
                    carrello = (Carrello) sm.getAttribute("carrello");
                    sm.setAttribute("carrello", carrello);
                }
            }else {
                carrello = (Carrello) sm.getAttribute("carrello");
            }

            if (carrello == null){
                carrello = new Carrello();
            }

        }else {
            sm = new SessionManager(req, true);
            carrello = new Carrello();
        }

        synchronized (carrello){

            if (type.equals("add")){
                if (prodotto != null){

                    ProdottoComposto prodottoComposto = new ProdottoComposto();
                    prodottoComposto.setProdotto(prodotto);
                    carrello.addProdotto(prodottoComposto);
                }
            }else if (type.equals("remove")){


                ProdottoComposto prodottoComposto = new ProdottoComposto();
                prodottoComposto.setProdotto(prodotto);
                carrello.removeProdotto(prodottoComposto);
                // Create the response string
                double totale = carrello.getTotale();
                double scontoTotale = carrello.getScontoTotale();
                double prezzoScontatoTotale = carrello.getPrezzoScontatoTotale();
                String responseText = totale + ";" + scontoTotale + ";" + prezzoScontatoTotale;
                resp.setContentType("text/plain");
                resp.getWriter().write(responseText);
            }
        }

        sm.setAttribute("carrello", carrello);

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

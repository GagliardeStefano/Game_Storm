package Controller;

import Model.DAO.AdminDAO;
import Model.Enum.TipoUtente;
import Model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

@WebServlet(name = "Admin", value = "/AdminManager")
public class AdminManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO dao = new AdminDAO();
        SessionManager sm = new SessionManager(req, false);
        RequestDispatcher rd;

        if (sm.getSession() != null){
            User check = (User) sm.getSession().getAttribute("user");

            if (check.getTipo() == TipoUtente.Admin1 || check.getTipo() == TipoUtente.Admin2){

                int numCarrelli = dao.getNumTotCarrelli();
                getServletContext().setAttribute("totCarrelli", numCarrelli);

                int numUtenti = dao.getNumTotByTable("utente");
                getServletContext().setAttribute("totUtenti", numUtenti);

                int numOrdini = dao.getNumTotByTable("ordini");
                getServletContext().setAttribute("totOrdini", numOrdini);

                int numProdotti = dao.getNumTotByTable("prodotti");
                getServletContext().setAttribute("totProdotti", numProdotti);

                double totUltimoMese = dao.getTotUltimoMese();
                getServletContext().setAttribute("totUltimoMese", totUltimoMese);

                double guadagno = dao.getGuadagno();
                getServletContext().setAttribute("totGuadagno", guadagno);

                List<String> generi = dao.getAllGeneri();
                getServletContext().setAttribute("generi", generi);


                rd = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                rd.forward(req, resp);

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminDAO dao = new AdminDAO();
        SessionManager sm = new SessionManager(req, false);
        String tabella = req.getParameter("tabella");

        if (!tabella.isEmpty()){
            List<Map<String, Object>> records = new ArrayList<>();
            switch (tabella){
                case "prodotti":
                     records = dao.getProdotti();
                     break;

                case "utente":
                    records = dao.getUtenti();
                    break;

                case "ordini":
                    records = dao.getOridiniEffettauti();
                    break;
                case "carrello":
                    records = dao.getCarrelli();
                    break;
                case "genere":
                    records = dao.getGeneri();
                    break;
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("records", records);   // Aggiungi i record

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
}

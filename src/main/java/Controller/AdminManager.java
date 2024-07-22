package Controller;

import Model.DAO.AdminDAO;
import Model.DAO.ProdottoDAO;
import Model.DAO.UserDAO;
import Model.Enum.TipoUtente;
import Model.Prodotto;
import Model.User;
import Model.Utils.Validator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@WebServlet(name = "Admin", value = "/AdminManager")
public class AdminManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdminDAO dao = new AdminDAO();
        SessionManager sm = new SessionManager(req, false);
        RequestDispatcher rd;

        if (sm.getSession() != null) {
            User check = (User) sm.getSession().getAttribute("user");

            if (check.getTipo() == TipoUtente.Admin1 || check.getTipo() == TipoUtente.Admin2) {

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

                List<String> nomiGiochi = dao.getAllNameGames();
                getServletContext().setAttribute("nomiGiochi", nomiGiochi);


                rd = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                rd.forward(req, resp);

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AdminDAO dao = new AdminDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Validator validator = new Validator();
        RequestDispatcher dispatcher;
        SessionManager sm = new SessionManager(req, false);
        int check;

        String from = req.getParameter("from");

        switch (from) {
            case "showTable":
                String tabella = req.getParameter("tabella");

                if (!tabella.isEmpty()) {
                    String jsonString = getJSONString(tabella, dao);

                    // Imposta il tipo di contenuto della risposta come JSON
                    resp.setContentType("application/json");

                    // Scrivi la risposta JSON al client
                    PrintWriter out = resp.getWriter();
                    out.print(jsonString);
                    out.flush();
                }
                break;

            case "addProdotto":

                String nome = req.getParameter("nome");
                String descrizione = req.getParameter("descrizione");
                String dataProd = req.getParameter("dataRilascio");
                double prezzo = Double.parseDouble(req.getParameter("prezzo"));
                int sconto = Integer.parseInt(req.getParameter("sconto"));
                String urlImg = req.getParameter("urlImg");
                String urlTrailer = req.getParameter("urlTrailer");
                String[] generi = req.getParameterValues("genere");

                if (dao.existGame(nome)){

                    validator.addError(false, "Gioco già presente");
                    req.setAttribute("errori", validator.getErrors());

                }else {

                    validator = new Validator();
                    validator.validateGame(nome, descrizione, dataProd, prezzo, sconto, generi, urlImg, urlTrailer);

                    if (validator.hasErrors()){
                        req.setAttribute("errori", validator.getErrors());
                    }else {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date data;
                        try {
                            data = dateFormat.parse(dataProd);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        Prodotto prodotto = new Prodotto();

                        prodotto.setNome(nome);
                        prodotto.setDescrizione(descrizione);
                        prodotto.setDataRilascio(data);
                        prodotto.setPrezzo(prezzo);
                        prodotto.setSconto(sconto);
                        prodotto.setPrezzoScontato();
                        prodotto.setImg("/images/giochi/"+nome.replace(" ", "")+".jpg");
                        prodotto.setTrailer(urlTrailer);
                        prodotto.downloadImage(urlImg, nome, getServletContext());

                        dao.addNewGame(prodotto);
                        Prodotto gioco = prodottoDAO.getProdByName(nome);
                        dao.addGeneriAtProdById(String.valueOf(gioco.getId()), List.of(generi));

                        /*NON è un errore, solo per informare*/
                        validator.addError(false, "Gioco aggiunto");
                        req.setAttribute("errori", validator.getErrors());
                    }
                }

                req.setAttribute("type", "addProdotto");
                dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                dispatcher.forward(req, resp);
                break;

            case "addUser":

                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String nomeUser = req.getParameter("nome");
                String cognome = req.getParameter("cognome");
                String data = req.getParameter("dataNascita");
                String regione = req.getParameter("regione");
                String tipo = req.getParameter("tipo");

                validator = new Validator();
                validator.validateAll(nomeUser, cognome, regione, email, password, data);

                if (validator.hasErrors() && ( !tipo.equals("Semplice") && !tipo.equals("Admin1") && !tipo.equals("Admin2"))){
                    validator.addError(false, "Seleziona un tipo");
                    req.setAttribute("errori", validator.getErrors());
                }else {

                    UserDAO userDAO = new UserDAO();

                    if (userDAO.emailAlreadyExists(email)){
                        System.out.println("utente già presente");
                        validator = new Validator();
                        validator.addError(false, "Utente già presente");
                        req.setAttribute("errori", validator.getErrors());
                    }else {
                        User user = new User();

                        user.setNome(nomeUser);
                        user.setCognome(cognome);
                        user.setEmail(email);
                        user.setPassword(password);
                        try {
                            user.setPasswordHash();
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                        user.setData(data);
                        user.setRegione(regione);
                        user.setTipo(TipoUtente.valueOf(tipo));
                        user.setFoto("/images/avatar/avatar0.png");

                        userDAO.doSave(user);

                        /*NON è un errore, solo per informare*/
                        validator.addError(false,"Utente aggiunto");
                        req.setAttribute("errori", validator.getErrors());
                    }
                }

                req.setAttribute("type", "addUser");
                dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                dispatcher.forward(req, resp);

                break;

            case "addGenere":

                String newGenere = req.getParameter("genere");
                String[] giochi = req.getParameterValues("listGames");

                validator.validateNewGenere(newGenere, giochi);

                if (validator.hasErrors()){
                    req.setAttribute("errori", validator.getErrors());
                }else {
                    if (dao.genereAlreadyExists(newGenere)){
                        validator.addError(false, "Genere già presente");
                        req.setAttribute("errori", validator.getErrors());
                    }else {
                        dao.addNewGenere(newGenere);

                        String idGenere = String.valueOf(dao.getIdByGenere(newGenere));
                        dao.addGamesAtGenereById(idGenere, giochi);

                        /*NON è un errore, solo per informare*/
                        validator.addError(false,"Genere aggiunto");
                        req.setAttribute("errori", validator.getErrors());
                    }
                }

                req.setAttribute("type", "addGenere");
                dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                dispatcher.forward(req, resp);
                break;

            case "deleteProd":
                String nomeProd = req.getParameter("input");
                if (!nomeProd.isEmpty()){
                    check=dao.deleteProdotto(nomeProd);
                    if (check >= 1 ){
                        validator.addError(false,"Prodotto eliminato");
                        req.setAttribute("errori", validator.getErrors());
                    }else {
                        validator.addError(false,"Prodotto non eliminato,inserire correttamnete il nome");
                        req.setAttribute("errori", validator.getErrors());
                    }
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                    dispatcher.forward(req, resp);

                }
                break;


            case "deleteUser":
                String emailUser = req.getParameter("input");
                if (!emailUser.isEmpty()){
                    check=dao.deleteUser(emailUser);
                    if (check >= 1){
                        validator.addError(false,"Utente eliminato");
                        req.setAttribute("errori", validator.getErrors());
                    }
                    else {
                        validator.addError(false,"Utente non eliminato, inserire correttamente l'email");
                        req.setAttribute("errori", validator.getErrors());
                    }
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                    dispatcher.forward(req, resp);

                }

                break;


            case "deleteGenere":
                String nomeGenere = req.getParameter("input");
                if(!nomeGenere.isEmpty()){
                    check=dao.deleteGenere(nomeGenere);
                    if (check >= 1){
                        validator.addError(false,"Genere eliminato");
                        req.setAttribute("errori", validator.getErrors());
                    }else {
                        validator.addError(false,"Genere non eliminato, inserire correttamente il nome");
                        req.setAttribute("errori", validator.getErrors());
                    }
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                    dispatcher.forward(req, resp);

                }


                break;
        }
    }

    private static String getJSONString(String tabella, AdminDAO dao) {
        List<Map<String, Object>> records = new ArrayList<>();
        switch (tabella) {
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
        return jsonResponse.toJSONString();
    }

}
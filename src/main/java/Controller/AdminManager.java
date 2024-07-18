package Controller;

import Model.DAO.AdminDAO;
import Model.DAO.ProdottoDAO;
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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@WebServlet(name = "Admin", value = "/AdminManager")
public class AdminManager extends HttpServlet {

    private static final String DESTINATION_FILE = "/images/giochi/";
    private static final int WIDTH_IMG = 728;
    private static final int HEIGHT_IMG = 453;

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

        String from = req.getParameter("from");

        switch (from) {
            case "showTable":
                String tabella = req.getParameter("tabella");

                if (!tabella.isEmpty()) {
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
                    String jsonString = jsonResponse.toJSONString();


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

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date data;
                try {
                    data = dateFormat.parse(req.getParameter("data"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                String prezzo = req.getParameter("prezzo");
                String sconto = req.getParameter("sconto");

                String urlImg = req.getParameter("urlImg");
                String img = downloadImage(urlImg, nome, getServletContext());

                String urlTrailer = req.getParameter("urlTrailer");
                List<String> generi = List.of(req.getParameterValues("genere"));

                if (dao.existGame(nome)){

                    validator.addError(true, "Gioco già presente");

                    req.setAttribute("errori", validator.getErrors());
                    dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                    dispatcher.forward(req, resp);

                }else {
                    Prodotto prodotto = new Prodotto();

                    prodotto.setNome(nome);
                    prodotto.setDescrizione(descrizione);
                    prodotto.setDataRilascio(data);
                    prodotto.setPrezzo(Double.parseDouble(prezzo));
                    prodotto.setSconto(Integer.parseInt(sconto));
                    prodotto.setPrezzoScontato();
                    prodotto.setImg(img);
                    prodotto.setTrailer(urlTrailer);

                    validator.validateGame(prodotto);

                    if (validator.hasErrors()){
                        req.setAttribute("errori", validator.getErrors());
                        dispatcher = req.getRequestDispatcher("/WEB-INF/results/admin.jsp");
                        dispatcher.forward(req, resp);
                    }else {

                        dao.addNewGame(prodotto);
                        Prodotto gioco = prodottoDAO.getProdByName(nome);
                        dao.addGeneriAtProdById(String.valueOf(gioco.getId()), generi);

                    }

                }

                break;
        }
    }

    public String downloadImage(String urlString, String imageName, ServletContext servletContext) throws IOException {
        URL url = new URL(urlString);
        BufferedImage originalImage = ImageIO.read(url);

        // Ridimensiona l'immagine
        Image resizedImage = originalImage.getScaledInstance(WIDTH_IMG, HEIGHT_IMG, Image.SCALE_SMOOTH);
        BufferedImage bufferedResizedImage = new BufferedImage(WIDTH_IMG, HEIGHT_IMG, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedResizedImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Ottiene il percorso reale della directory di destinazione
        String destinationPath = servletContext.getRealPath("/images/giochi/");
        File outputDir = new File(destinationPath);

        // Assicurati che la directory esista
        /*if (!outputDir.exists()) {
            outputDir.mkdirs();  // Crea le directory se non esistono
        }*/

        // Costruisci il nome del file
        String fileName = imageName.replace(" ", "") + ".jpg";
        File outputFile = new File(outputDir, fileName);

        // Salva l'immagine ridimensionata
        ImageIO.write(bufferedResizedImage, "jpg", outputFile);

        // Restituisce il percorso relativo del file salvato
        return "/images/giochi/" + fileName;
    }

}
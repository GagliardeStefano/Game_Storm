package Controller;

import Model.DAO.UserDAO;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="UpdateUser", value = "/UpdateUser")
public class UserUpdateManager extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        UserDAO userDAO = new UserDAO();
        SessionManager sm = new SessionManager(req, false);

        if (sm.getSession() != null){
            if(sm.getAttribute("utente") != null){

                String id = req.getParameter("IdProd");
                User user = (User) sm.getAttribute("utente");

                if (id.equals("all")){
                    userDAO.removeAllFavorite(user.getEmail());
                } else if(!id.isEmpty()){
                    userDAO.removeFavorite(id, user.getEmail());
                }
            }
        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

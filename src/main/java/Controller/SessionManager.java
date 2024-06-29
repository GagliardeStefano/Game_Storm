package Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManager {

    private HttpSession session;

    public SessionManager(HttpSession session) {
        this.session = session;
    }

    public SessionManager(HttpServletRequest request, boolean createNew){

        if (request != null)
            this.session = request.getSession(createNew);

    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public void setAttribute(String key, Object value){
        this.session.setAttribute(key, value);
    }

    public Object getAttribute(String key){
        return this.session.getAttribute(key);
    }

}

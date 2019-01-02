package bsi.mpoo.istock.services;

import android.content.Context;
import bsi.mpoo.istock.data.session.SessionDAO;
import bsi.mpoo.istock.domain.Session;

public class SessionServices {
    private SessionDAO sessionDAO;
    public SessionServices(Context context){
        sessionDAO = new SessionDAO(context);
    }

    public void updateSession(Session session){
        sessionDAO.update(session);
    }

    public Session getSession(){
        return sessionDAO.get();
    }

    public void clearSession(){
        sessionDAO.clearSession();
    }
}

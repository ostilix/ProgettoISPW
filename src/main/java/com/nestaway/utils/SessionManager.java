package com.nestaway.utils;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final List<Session> activeSession = new ArrayList<>();
    private static SessionManager instance = null;
    private SessionManager() {}

    public static synchronized SessionManager getSessionManager(){
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Integer createSession(){
        Session session = new Session();
        activeSession.add(session);
        return session.hashCode();
    }

    public Session getSessionFromId(Integer id){
        for(Session session: activeSession){
            if(session.hashCode() == id){
                return session;
            }
        }
        return null;
    }

    public void removeSession(Integer id) {
        activeSession.removeIf(session -> session.hashCode() == id);
    }
}



package bsi.mpoo.istock.domain;

public class Session {
    private static Session instance = new Session();
    private static User user;
    private static int remember;
    private static long id_user;
    private static long id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRemember() {
        return remember;
    }

    public void setRemember(int remember) {
        this.remember = remember;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public static Session getInstance(){
        return instance;
    }
}

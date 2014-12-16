package id.go.jogjakota.perizinan.data;

import id.go.jogjakota.perizinan.domain.User;

public class Session {

    private static Session session;
    private User user;

    public static Session get() {
        if (session == null) {
            session = new Session();
        }

        return session;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void login(User user) {
        this.user = user;
    }

    public void logout() {
        user = null;
    }
}

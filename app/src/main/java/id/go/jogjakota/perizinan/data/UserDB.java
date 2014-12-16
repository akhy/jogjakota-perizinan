package id.go.jogjakota.perizinan.data;

import java.util.LinkedList;

import id.go.jogjakota.perizinan.domain.User;

public class UserDB {
    private static UserDB userDB;

    private LinkedList<User> users = new LinkedList<>();

    public static UserDB get() {
        if (userDB == null) {
            userDB = new UserDB();
        }

        return userDB;
    }

    public void addUser(User newUser) throws UserExistException {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(newUser.getUsername()))
                throw new UserExistException();
        }

        users.add(newUser);
    }

    public User getUser(String username, String password) throws UserNotFoundException {
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername()) && password.equals(user.getPassword()))
                return user;
        }

        throw new UserNotFoundException("Username atau password salah");
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException() {
        }

        public UserNotFoundException(String detailMessage) {
            super(detailMessage);
        }

        public UserNotFoundException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public UserNotFoundException(Throwable throwable) {
            super(throwable);
        }
    }

    public static class UserExistException extends Exception {
        public UserExistException() {
        }

        public UserExistException(String detailMessage) {
            super(detailMessage);
        }

        public UserExistException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public UserExistException(Throwable throwable) {
            super(throwable);
        }
    }
}

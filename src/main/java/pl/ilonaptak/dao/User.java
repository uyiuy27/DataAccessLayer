package pl.ilonaptak.dao;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;

    public User(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
//        this.password = password;
        setPassword(password);
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
//        this.password = password;
        setPassword(password);
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    void setId(int id) {
        this.id = id;
    }

    public void setUserName(User user) {
        UserDao.update(user);
    }

    public boolean setEmail(User user) {
        return UserDao.isEmailUnique(getEmail())? UserDao.update(user) : null;
    }

    public void setPassword(String password) {
        this.password = org.mindrot.jbcrypt.BCrypt.hashpw(password, BCrypt.gensalt());
//        UserDao.update(user);
    }
}
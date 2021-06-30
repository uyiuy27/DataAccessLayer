package pl.ilonaptak.dao;

public class MainDao {
    public static void main(String[] args) {
        User user = new User("Marta", "martusia@buzaiczek.com", "Martusia");
        User user2 = new User("Dzban", "d.zban@gmail.com", "blablabla");
        User user3 = new User("sfdsfs", "adres@o2.pl", "hasło");
        User user4 = new User("Użytkownik", "user@gmail.com", "blablabla");
        User user5 = new User("AwarJan", "jan@wp.pl", "JanAwarian123");
        User user6 = new User("Ja", "j.ka@op.pl", "kkkggglll");
        User user7 = new User(4,"NewUserName", "mójemail@op.pl", "hasełko");
        User user8 = new User(5,"Użytkownik", "user@gmail.com", "blablabla");





//        UserDao.read("d.zban@gmail.com");

//        UserDao.update(user7);
//        UserDao.delete(user8);


        UserDao.readAll();

    }
}
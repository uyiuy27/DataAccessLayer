package pl.ilonaptak.dao;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static final String EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String CREATE_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String READ_ALL = "SELECT * FROM users";

    public static boolean isEmailUnique(String email) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(EMAIL)) {
            prepSt.setString(1, email);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean create(User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (!isEmailUnique(user.getEmail())) {
                return false;
            }
            prepSt.setString(1, user.getEmail());
            prepSt.setString(2, user.getUserName());
            String password = org.mindrot.jbcrypt.BCrypt.hashpw(user.getPassword(), org.mindrot.jbcrypt.BCrypt.gensalt());
            prepSt.setString(3, password);
            if (prepSt.executeUpdate() == 1) {
                return true;
            }
            ResultSet rs = prepSt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static User read(String email) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(READ_QUERY)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("Podany email nie isntieje");
                return null;
            } else {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readAll () {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(READ_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean update(User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUserName());
            String password = org.mindrot.jbcrypt.BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            ps.setString(3, password);
            ps.setInt(4, user.getId());
            if (ps.executeUpdate() == 1) {
                System.out.println("Zmieniono dane");
                return true;
            } else {
                System.out.println("nie udało się zmienić danych");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete (User user) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_QUERY)) {
            ps.setInt(1, user.getId());
            if (ps.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
package model.DAO;

import model.Bean.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DAO.DbConnection;

public class UserDAO {

    // Kiểm tra username đã tồn tại chưa
    public boolean checkUserExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            return rs.next();  // nếu có data -> đã tồn tại

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Thêm user đăng ký
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users(username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // sau này mã hóa
            ps.setString(3, user.getEmail());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Đăng nhập
    public User getUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Lấy user theo ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getTimestamp("created_at")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

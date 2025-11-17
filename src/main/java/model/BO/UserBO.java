package model.BO;

import model.DAO.*;
import model.Bean.*;

public class UserBO {
    private UserDAO userDAO;

    public UserBO() {
        userDAO = new UserDAO();
    }

    // Xử lý đăng ký (validate + gọi DAO)
    public boolean register(User user) {
        // Kiểm tra username có tồn tại chưa
        if (userDAO.checkUserExists(user.getUsername())) {
            return false;
        }
        return userDAO.insertUser(user);
    }

    // Xử lý đăng nhập
    public User login(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    // Lấy User theo ID
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
}

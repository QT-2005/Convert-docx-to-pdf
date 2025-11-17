package controller;

import model.BO.*;
import model.Bean.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private UserBO userBO = new UserBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(username, password, email);

        boolean success = userBO.register(user);

        if (success) {
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Username đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

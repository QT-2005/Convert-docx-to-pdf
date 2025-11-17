package controller;

import model.BO.*;
import model.Bean.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/jobs")
public class JobStatusController extends HttpServlet {

    private JobBO jobBO = new JobBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("jobs", jobBO.getJobsByUserId(user.getId()));
        request.getRequestDispatcher("job_list.jsp").forward(request, response);
    }
}

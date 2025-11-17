package controller;

import model.BO.*;
import model.Bean.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/download")
public class DownloadController extends HttpServlet {

    private JobBO jobBO = new JobBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int jobId = Integer.parseInt(request.getParameter("id"));

        Job job = jobBO.getJobById(jobId);

        if (job == null || job.getOutputFile() == null) {
            response.getWriter().println("File chưa sẵn sàng!");
            return;
        }

        File file = new File(job.getOutputFile());
        if (!file.exists()) {
            response.getWriter().println("File không tồn tại!");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"output.pdf\"");

        FileInputStream fis = new FileInputStream(file);
        OutputStream os = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        fis.close();
        os.close();
    }
}

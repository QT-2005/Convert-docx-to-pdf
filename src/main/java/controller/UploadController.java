package controller;

import model.BO.JobBO;
import model.BO.JobQueue;
import model.Bean.Job;
import model.Bean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 50,       // 50MB
    maxRequestSize = 1024 * 1024 * 100    // 100MB
)
public class UploadController extends HttpServlet {

    private static final String UPLOAD_DIR = "D:/convert-uploads/"; // ĐƯỜNG DẪN CỐ ĐỊNH
    private final JobBO jobBO = new JobBO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        Part filePart = request.getPart("file");
        if (filePart == null || filePart.getSubmittedFileName() == null || filePart.getSubmittedFileName().trim().isEmpty()) {
            setError(request, response, "Vui lòng chọn file để upload.");
            return;
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (!originalFileName.toLowerCase().endsWith(".docx")) {
            setError(request, response, "Chỉ hỗ trợ file .docx");
            return;
        }

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                setError(request, response, "Không thể tạo thư mục upload.");
                return;
            }
        }

        // Tạo tên file duy nhất: UUID + .docx
        String uniqueFileName = UUID.randomUUID().toString() + ".docx";
        String filePath = UPLOAD_DIR + uniqueFileName;

        // Lưu file
        try {
            filePart.write(filePath);
            System.out.println("File uploaded: " + filePath);
        } catch (IOException e) {
            System.err.println("Lỗi lưu file: " + e.getMessage());
            setError(request, response, "Lỗi lưu file: " + e.getMessage());
            return;
        }

        // Kiểm tra file có tồn tại không
        if (!Files.exists(Paths.get(filePath))) {
            setError(request, response, "File không được lưu đúng cách.");
            return;
        }

        // Tạo job
        Job job = new Job();
        job.setUserId(user.getId());
        job.setInputFile(filePath);
        job.setStatus("PENDING");

        Job savedJob = jobBO.createJob(job);
        if (savedJob == null || savedJob.getId() == 0) {
            setError(request, response, "Tạo job thất bại. Vui lòng thử lại.");
            return;
        }

        // Thêm vào queue để JobProcessor xử lý
        JobQueue.addJob(savedJob);
        System.out.println("Job ID " + savedJob.getId() + " đã được thêm vào queue.");

        // Chuyển sang trang trạng thái
        request.setAttribute("job", savedJob);
        request.getRequestDispatcher("job_status.jsp").forward(request, response);
    }

    // Helper: Hiển thị lỗi và quay lại upload.jsp
    private void setError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }
}
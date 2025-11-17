package model.BO;

import model.DAO.JobDAO;
import model.Bean.Job;

import java.util.List;

public class JobBO {
    private JobDAO jobDAO;

    public JobBO() {
        jobDAO = new JobDAO();
    }

    // Tạo Job mới khi user upload file, trả về Job đã có ID
    public Job createJob(Job job) {
        return jobDAO.insertJob(job);
    }

    // Lấy danh sách Job của user
    public List<Job> getJobsByUserId(int userId) {
        return jobDAO.getJobsByUserId(userId);
    }

    // Lấy Job theo ID
    public Job getJobById(int jobId) {
        return jobDAO.getJobById(jobId);
    }

    // Cập nhật toàn bộ Job (status + output_file + finished_at)
    public boolean updateJob(Job job) {
        if (job == null) return false;
        return jobDAO.updateJob(job);
    }
}

package model.DAO;

import model.Bean.Job;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    // Thêm Job mới và trả về Job đã có ID
    public Job insertJob(Job job) {
        String sql = "INSERT INTO jobs(user_id, input_file, status) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, job.getUserId());
            ps.setString(2, job.getInputFile());
            ps.setString(3, job.getStatus());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    job.setId(rs.getInt(1));
                    return job;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả Job của 1 user
    public List<Job> getJobsByUserId(int userId) {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToJob(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy Job theo ID
    public Job getJobById(int jobId) {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToJob(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // SỬA: XÓA finished_at → DÙNG updated_at
    public boolean updateJob(Job job) {
        String sql = "UPDATE jobs SET status = ?, output_file = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, job.getStatus());
            ps.setString(2, job.getOutputFile());
            ps.setInt(3, job.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm tiện ích: chuyển ResultSet → Job
    private Job mapResultSetToJob(ResultSet rs) throws SQLException {
        return new Job(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("input_file"),
                rs.getString("output_file"),
                rs.getString("status"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("finished_at") // giữ lại nếu bảng có, không ảnh hưởng
        );
    }
}
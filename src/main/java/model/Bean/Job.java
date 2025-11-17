package model.Bean;

import java.sql.Timestamp;

public class Job {
    private int id;
    private int userId;
    private String inputFile;
    private String outputFile;
    private String status;    // PENDING, PROCESSING, DONE, FAILED
    private Timestamp createdAt;
    private Timestamp finishedAt;

    public Job() {
    }

    public Job(int id, int userId, String inputFile, String outputFile, String status,
               Timestamp createdAt, Timestamp finishedAt) {
        this.id = id;
        this.userId = userId;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.status = status;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
    }

    public Job(int userId, String inputFile, String status) {
        this.userId = userId;
        this.inputFile = inputFile;
        this.status = status;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", userId=" + userId +
                ", inputFile='" + inputFile + '\'' +
                ", outputFile='" + outputFile + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}

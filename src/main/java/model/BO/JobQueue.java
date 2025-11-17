package model.BO;

import model.Bean.Job;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JobQueue {

    // Queue chứa Job chờ xử lý
    private static BlockingQueue<Job> queue = new LinkedBlockingQueue<>();

    // Thêm Job vào queue
    public static void addJob(Job job) {
        queue.offer(job);
    }

    // Lấy Job ra để xử lý
    public static Job takeJob() throws InterruptedException {
        return queue.take(); // block nếu queue rỗng
    }

    // Kiểm tra số Job đang chờ
    public static int size() {
        return queue.size();
    }
}

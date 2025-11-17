package listener;

import model.BO.JobProcessor;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    private JobProcessor processor;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== AppStartupListener: Khởi động JobProcessor ===");
        processor = new JobProcessor();
        // XÓA DÒNG NÀY: JobProcessor.setServletContext(sce.getServletContext());
        processor.start();
        System.out.println("JobProcessor background thread STARTED.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (processor != null) {
            processor.shutdown();
            try {
                processor.join(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("JobProcessor STOPPED.");
        }
    }
}
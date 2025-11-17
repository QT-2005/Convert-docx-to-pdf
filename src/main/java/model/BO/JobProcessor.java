package model.BO;

import model.Bean.Job;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.*;

public class JobProcessor extends Thread {

    private volatile boolean running = true;

    @Override
    public void run() {
        System.out.println("JobProcessor background thread STARTED.");

        while (running) {
            try {
                Job job = JobQueue.takeJob();
                processJob(job);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            } catch (Exception e) {
                System.err.println("Lỗi trong JobProcessor: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("JobProcessor background thread STOPPED.");
    }

    private void processJob(Job job) {
        System.out.println(">>> Đang xử lý Job ID: " + job.getId() + " | File: " + job.getInputFile());

        try {
            String outputFile = convertDocxToPdf(job.getInputFile());

            job.setOutputFile(outputFile);
            job.setStatus("SUCCESS");
            new JobBO().updateJob(job);

            System.out.println(">>> Job " + job.getId() + " THÀNH CÔNG: " + outputFile);

        } catch (Exception e) {
            job.setStatus("FAILED");
            new JobBO().updateJob(job);

            System.err.println(">>> Job " + job.getId() + " THẤT BẠI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String convertDocxToPdf(String inputFile) throws IOException {
        String outputFile = inputFile.replace(".docx", ".pdf");
        File file = new File(inputFile);
        if (!file.exists()) {
            throw new FileNotFoundException("Không tìm thấy file: " + inputFile);
        }

        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
             PDDocument pdfDoc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            pdfDoc.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(pdfDoc, page);

            // LOAD FONT TIẾNG VIỆT TỪ WEB-INF/classes/fonts/
            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/DejaVuSans.ttf");
            if (fontStream == null) {
                throw new FileNotFoundException("Không tìm thấy font: fonts/DejaVuSans.ttf trong WEB-INF/classes/fonts/");
            }
            PDType0Font font = PDType0Font.load(pdfDoc, fontStream);
            contentStream.setFont(font, 12);

            float margin = 50;
            float y = page.getMediaBox().getHeight() - margin;
            float leading = 15; // Khoảng cách dòng

            for (XWPFParagraph p : doc.getParagraphs()) {
                String text = p.getText();

                // BỎ HOẶC THAY THẾ KÝ TỰ ĐẶC BIỆT KHÔNG HỖ TRỢ TRONG PDFBox
                if (text != null && !text.trim().isEmpty()) {
                    text = text.replace("\t", "    ")  // Tab → 4 spaces
                               .replace("\n", " ")     // Line Feed → space
                               .replace("\r", " ")     // Carriage Return → space
                               .replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", ""); // Xóa control chars
                } else {
                    continue;
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(margin, y);
                contentStream.showText(text);
                contentStream.endText();

                y -= leading;

                // TẠO TRANG MỚI NẾU HẾT CHỖ
                if (y < margin) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    pdfDoc.addPage(page);
                    contentStream = new PDPageContentStream(pdfDoc, page);
                    contentStream.setFont(font, 12);
                    y = page.getMediaBox().getHeight() - margin;
                }
            }

            contentStream.close();
            pdfDoc.save(outputFile);
        }

        return outputFile;
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}
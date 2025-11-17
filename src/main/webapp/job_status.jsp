<%@ page import="model.Bean.Job" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Job Status</title>
</head>
<body>

<%
    // Lấy đối tượng Job từ request
    Job job = (Job) request.getAttribute("job");
%>

<% 
    // KIỂM TRA NULL ở đây: Nếu job là null, hiển thị thông báo lỗi
    if (job == null) { 
%>
    <h1>⚠️ Lỗi: Không tìm thấy thông tin Job!</h1>
    <p>Vui lòng đảm bảo rằng Job object đã được đặt vào request attribute với key "job" trong Controller trước khi forward tới trang này.</p>
<% 
    // Nếu job không phải là null, tiến hành hiển thị trạng thái
    } else { 
%>
    <h2>Trạng thái Job #<%= job.getId() %></h2>

    <p><b>Input:</b> <%= job.getInputFile() %></p>
    <p><b>Status:</b> <%= job.getStatus() %></p>

    <% 
        // Logic hiển thị theo trạng thái chỉ được thực hiện khi job != null
        if ("DONE".equals(job.getStatus()) && job.getOutputFile() != null) { 
    %>
        <p>
            <b>Output:</b>
            <a href="download?id=<%= job.getId() %>">Tải PDF</a>
        </p>
    <% } else if ("PROCESSING".equals(job.getStatus())) { %>
        <p>Đang xử lý, vui lòng chờ...</p>
    <% } else if ("PENDING".equals(job.getStatus())) { %>
        <p>Job đang chờ xử lý...</p>
    <% } %>
<% } %>

<br>
<a href="home">Quay lại</a>

</body>
</html>
<%@ page import="java.util.*, model.Bean.Job" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h2>Xin chào!</h2>

<a href="upload.jsp">Upload DOCX để chuyển PDF</a> |
<a href="logout">Đăng xuất</a>

<h3>Danh sách công việc của bạn:</h3>

<%
    List<Job> jobs = (List<Job>) request.getAttribute("jobs");
    if (jobs != null && !jobs.isEmpty()) {
%>
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Input</th>
            <th>Status</th>
            <th>Output</th>
        </tr>

        <% for (Job j : jobs) { %>
        <tr>
            <td><%= j.getId() %></td>
            <td><%= j.getInputFile() %></td>
            <td><%= j.getStatus() %></td>
            <td>
                <% if ("DONE".equals(j.getStatus()) && j.getOutputFile() != null) { %>
                    <a href="download?file=<%= j.getOutputFile() %>">Tải PDF</a>
                <% } else { %>
                    ---
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
<%
    } else {
%>
    <p>Chưa có job nào.</p>
<%
    }
%>

</body>
</html>
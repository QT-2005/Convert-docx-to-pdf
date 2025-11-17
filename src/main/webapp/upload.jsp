<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Upload DOCX</title>
</head>
<body>
<h2>Upload file DOCX để chuyển sang PDF</h2>

<form action="upload" method="post" enctype="multipart/form-data">
    Chọn file DOCX: <input type="file" name="file" accept=".docx" required><br><br>
    <button type="submit">Upload</button>
</form>

<br>
<a href="home">Quay lại trang chủ</a>

</body>
</html>

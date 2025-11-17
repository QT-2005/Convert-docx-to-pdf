<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h2 style="color:red;">Có lỗi xảy ra!</h2>

<p><%= request.getAttribute("error") %></p>

<br>
<a href="home">Quay lại trang chủ</a>

</body>
</html>

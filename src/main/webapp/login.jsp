<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Đăng nhập</h2>

<form action="login" method="post">
    Username: <input type="text" name="username" required><br><br>
    Password: <input type="password" name="password" required><br><br>
    <button type="submit">Login</button>
</form>

<br>
<a href="register.jsp">Chưa có tài khoản? Đăng ký</a>

<% String error = (String) request.getAttribute("error"); 
   if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <c:if test="${not empty param.error and param.error eq '1'}">
        <p style="color: red;">Invalid credentials. Please try again.</p>
    </c:if>
    <form action="login" method="post">
        Login: <input type="text" name="login"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Login">
    </form>
<a href="${pageContext.request.contextPath}/register.jsp">Register</a>
</body>
</html>
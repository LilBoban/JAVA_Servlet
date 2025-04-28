<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>File Explorer</title>
</head>
<body>
    <h1>File Explorer</h1>
    <p>Generated at: ${generatedTime}</p>
    <p>Current Directory: ${currentPath}</p>

    <div style="position: absolute; top: 10px; right: 10px;">
        <form action="logout" method="get">
            <input type="submit" value="Logout">
        </form>
    </div>

    <c:if test="${not empty parentPath}">
        <a href="explorer?path=${parentPath}">Up one level</a><br>
    </c:if>

    <ul>
        <c:forEach var="file" items="${files}">
            <li>
                <c:choose>
                    <c:when test="${file.isDirectory()}">
                        <a href="explorer?path=${file.getAbsolutePath()}">📁 ${file.getName()}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="download?path=${file.getAbsolutePath()}">📄 ${file.getName()}</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
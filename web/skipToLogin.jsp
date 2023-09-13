<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/9/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>您还未登录</h1>
<h2>即将在<b class="countDOM">3</b>秒钟后跳转至登陆页面</h2>
<br>
<span><a href="${pageContext.request.contextPath}/login.jsp">手动跳转到登陆页面</a></span>
<br>
</body>
</html>
<script>
    var count = 3;
    var countDOM = document.querySelector(".countDOM");
    window.addEventListener("load", function () {
        setInterval(function () {
            count--;
            countDOM.innerHTML = count;
        },
            1000);
        setTimeout(function () {
            window.location.href = "${pageContext.request.contextPath}/login.jsp";
        }, 3000)
    });
</script>

<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/20
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>付款成功</title>
</head>
<body>
<h1>操作成功<b class="countDOM">3</b>秒钟后跳转至原页面</h1>
<br>
<span><a href="${pageContext.request.contextPath}">跳转到购买记录页面</a></span>
</body>
</html>
<script>
    var count = 3;
    var countDOM = document.querySelector(".countDOM");
    window.addEventListener("load", function () {
        setInterval(function () {
                count--;
                countDOM.innerHTML=count;
            },
            1000);
        setTimeout(function () {
            history.back();
        }, 3000)
    });
</script>

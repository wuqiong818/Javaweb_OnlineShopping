<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/5
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>跳转页面</title>
</head>
<body>
<h2>商品添加成功，页面将在5秒中后进行跳转</h2>
<script>
    document.addEventListener("load", function () {
// 在点击事件的处理函数中，设置一个定时器，延迟5秒后进行页面跳转
        setTimeout(function () {
// 这里设置跳转的URL，可以替换成你想要跳转的页面URL
            window.location.href = "${pageContext.request.contextPath}/index.jsp";
        }, 5000);
    });
</script>

</body>
</html>

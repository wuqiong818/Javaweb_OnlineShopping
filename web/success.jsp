<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/10
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
<h1>操作成功<b class="countDOM">3</b>秒钟后跳转至原页面</h1>
<br>
<span><a href="${pageContext.request.contextPath}/shoppingCart.jsp">跳转到购物车进行结算</a></span>
<span><a href="${pageContext.request.contextPath}/index.jsp">跳转到首页</a></span>
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

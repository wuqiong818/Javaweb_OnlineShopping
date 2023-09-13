<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/9/8
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>跳转至首页</title>
</head>
<body>
<h1>操作成功<b class="countDOM">3</b>秒钟后跳转至首页</h1>
<br>
<span><a href="${pageContext.request.contextPath}/display/allMerchandise">跳转到商品页</a></span>
<br>
<span><a href="${pageContext.request.contextPath}/shoppingCart.jsp">跳转到购物车</a></span>
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
            window.location.href = "${pageContext.request.contextPath}/index.jsp";
        }, 3000)
    });
</script>

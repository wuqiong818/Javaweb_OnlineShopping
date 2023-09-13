<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/22
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Success</title>
</head>
<body>
<h1>操作成功<b class="countDOM">3</b>秒钟后跳转至主页</h1>
<br>
<span><a href="${pageContext.request.contextPath}/index.jsp">手动跳转到主页</a></span>
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
      // history.back();
      window.location.href = "${pageContext.request.contextPath}";
    }, 3000)
  });
</script>

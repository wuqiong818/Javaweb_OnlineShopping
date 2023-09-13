<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/7/31
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传商品页</title>
</head>
<body>
<h1>上传商品页面</h1>
<form method="post" action="${pageContext.request.contextPath}/uploading/merchandise" enctype="multipart/form-data">
    <label for="merchandise_name">商品名称:</label>
    <input type="text" id="merchandise_name" name="merchandise_name" required>
    <br>

    <label for="merchandise_document">交易的虚拟资料：</label>
    <input type="file" id="merchandise_document" name="merchandise_document" required>
    <br>

    <label for="merchandise_image">用户上传图片：</label>
    <input type="file" id="merchandise_image" name="merchandise_photoPath" accept="image/*" multiple required>
    <br>

    <label for="feature1">商品特征1：</label>
    <input type="text" id="feature1" name="merchandise_feature1" required>
    <br>

    <label for="feature2">商品特征2：</label>
    <input type="text" id="feature2" name="merchandise_feature2" required>
    <br>

    <label for="feature3">商品特征3：</label>
    <input type="text" id="feature3" name="merchandise_feature3" required>
    <br>

    <label for="merchandise_description">商品详情描述：</label>
    <textarea id="merchandise_description" name="merchandise_description" required></textarea>
    <br>

    <label for="merchandise_category">商品的所属类别：</label>
    <select id="merchandise_category" name="merchandise_category" required>
        <option value="paper_data">纸质材料</option>
        <option value="virtual_data">虚拟资料</option>
    </select>
    <br>
    <label for="merchandise_price">商品价格：</label>
    <input type="number" id="merchandise_price" name="merchandise_price" min="0" step="0.01" required>
    <br>
    <label for="merchandise_quantity">商品数量：</label>
    <input type="number" id="merchandise_quantity" name="merchandise_quantity" min="1" required>
    <br>
    <input class="submit_bottom" type="submit" value="提交">
</form>
<button class="button">取消上传，返回主页</button>
<script>
    var bottom = document.querySelector(".button");
    bottom.addEventListener("click", function () {
        window.location.href = "${pageContext.request.contextPath}/index.jsp";
    });
</script>

</body>
</html>

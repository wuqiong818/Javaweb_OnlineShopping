<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/22
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改商品页</title>
</head>
<body>
<h1>修改商品页面</h1>

<%--这里买呢有一个form表单--%>

<script>
    window.addEventListener("load", function () {
        // 获取查询字符串部分
        const queryString = window.location.search;
        // 创建 URLSearchParams 对象来解析查询字符串
        const searchParams = new URLSearchParams(queryString);
        // 获取特定参数的值
        const merchandiseId = searchParams.get('merchandiseId');
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var merchandiseJson = JSON.parse(xhr.responseText);
                console.log(merchandiseJson);
                var body = document.querySelector("body");
                body.insertAdjacentHTML(`beforeend`,`
<form method="post" action="${pageContext.request.contextPath}/update/merchandise?merchandiseId=`+merchandiseId+`" enctype="multipart/form-data"
      class="form">
                <label for="merchandise_name">商品名称:</label>
                <input type="text" id="merchandise_name" name="merchandise_name" value="`+merchandiseJson.name+`" required>
                <br>

                <label for="merchandise_document">交易的虚拟资料：</label>
                <input type="file" id="merchandise_document" name="merchandise_document" value="`+merchandiseJson.documentPath+`" required>
                <br>

                <label for="merchandise_image">用户上传图片：</label>
                <input type="file" id="merchandise_image" name="merchandise_photoPath" accept="image/*" value="`+merchandiseJson.photoArray+`" multiple required>
                <br>

                <label for="feature1">商品特征1：</label>
                <input type="text" id="feature1" name="merchandise_feature1" value="`+merchandiseJson.featureArray[0]+`" required>
                <br>

                <label for="feature2">商品特征2：</label>
                <input type="text" id="feature2" name="merchandise_feature2" value="`+merchandiseJson.featureArray[1]+`" required>
                <br>

                <label for="feature3">商品特征3：</label>
                <input type="text" id="feature3" name="merchandise_feature3" value="`+merchandiseJson.featureArray[2]+`" required>
                <br>

                <label for="merchandise_description">商品详情描述：</label>
                <textarea id="merchandise_description" name="merchandise_description"  required>`+merchandiseJson.description+`</textarea>
                <br>

                <label for="merchandise_category">商品的所属类别：</label>
                <select id="merchandise_category" name="merchandise_category" required>
                    <option value="paper_data">纸质材料</option>
                    <option value="virtual_data">虚拟资料</option>
                </select>
                <br>
                <label for="merchandise_price">商品价格：</label>
                <input type="number" id="merchandise_price" name="merchandise_price" value="`+merchandiseJson.price+`" min="0" step="0.01" required>
                <br>
                <label for="merchandise_quantity">商品数量：</label>
                <input type="number" id="merchandise_quantity" name="merchandise_quantity" min="1" value="`+merchandiseJson.amount+`" required>
                <br>
                <input class="submit_bottom" type="submit" value="提交">
                </form>`);
            }
        }
        xhr.open("GET", "${pageContext.request.contextPath}/update/myGoods?merchandiseId=" + merchandiseId, true);
        xhr.send();
    })
</script>

</body>
</html>
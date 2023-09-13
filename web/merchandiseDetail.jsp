<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/6
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品详情页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/merchandiseDetail.css">
</head>
<body>
<header></header>
<main>
    <div class="left">
        <%--这里图片的加载使用动态生成，获取图片路径中数组中的数据--%>
        <%--        ${merchandise.featureArray}${merchandise.photoArray}--%>
        <span class="button_left carousel-button"><img
                src="${pageContext.request.contextPath}/upload/轮播图左右按键.png"></span>
        <div class="exhibition">
            <ul class="container">
                <c:forEach items="${merchandise.photoPathArray}" var="item">
                    <li><img src="${pageContext.request.contextPath}/user_upload/images/${item}"></li>
                </c:forEach>
            </ul>
        </div>
        <span class="button_right carousel-button"><img
                src="${pageContext.request.contextPath}/upload/轮播图左右按键.png"></span>
        <span class="page">1/4</span>
    </div>
    <div class="right">
        <h1>${merchandise.name}</h1>
        <p>${merchandise.featureArray[0]}&nbsp;|&nbsp;${merchandise.featureArray[1]}&nbsp;|&nbsp;${merchandise.featureArray[2]}</p>
        <p>${merchandise.description}</p>
        <c:if test="${merchandise.category=='virtual_data'}">
            <p>类别:虚拟资料</p>
        </c:if>
        <c:if test="${merchandise.category=='paper_data'}">
            <p>类别:纸质资料</p>
        </c:if>
        <p>价格:${merchandise.price}元</p>
        <p>上架日期:${merchandise.issuedDate}</p>
        <input class="purchaseButton" type="button" onclick="purchaseSingleMerchandise(${merchandise.id})" value="购买">
        <input class="shoppingCartButton" type="button" value="加入购物车"><br><br>
        <span><a href="${pageContext.request.contextPath}/shoppingCart.jsp">跳转到购物车进行结算</a></span>
    </div>
</main>
</body>
<script>
    var purchaseButton = document.querySelector(".purchaseButton");
    var shoppingCartButton = document.querySelector(".shoppingCartButton");
    purchaseButton.addEventListener("click", function () {

    });
    shoppingCartButton.addEventListener("click", function () {
        window.location.href = "${pageContext.request.contextPath}/add/shoppingCart?merchandiseId=" +${merchandise.id};
    });

    //商品详情页中的图片切换
    var button_left = document.querySelector(".button_left");
    var button_right = document.querySelector(".button_right");
    var container = document.querySelector(".left .exhibition ul");
    var imgLength = document.querySelector(".left .exhibition ul li img").width;
    var liAmount = container.querySelectorAll("li").length;
    var page = document.querySelector(".page");
    var count = 1;
    page.innerHTML = count + "/" + liAmount;
    updateButtonStatus();//提前进行初始化，直接禁用左按钮;
    button_left.addEventListener("click", function () {
        var computedStyle = getComputedStyle(container);//获取left的值
        var leftValue = parseInt(computedStyle.left, 10); //将获取到的left的值，从字符串转换为整数
        container.style.left = leftValue + imgLength + "px";
        count--;
        page.innerHTML = count + "/" + liAmount;
        updateButtonStatus();
    })
    button_right.addEventListener("click", function () {
        var computedStyle = getComputedStyle(container);//获取left的值
        var leftValue = parseInt(computedStyle.left, 10); //将获取到的left的值，从字符串转换为整数
        container.style.left = leftValue - imgLength + "px";
        count++;
        page.innerHTML = count + "/" + liAmount;
        updateButtonStatus();
    })

    // 更新按钮状态
    function updateButtonStatus() {
        const carouselCount = liAmount; // 假设轮播图总数为 carouselCount
        // 检查并添加/移除 disabled 类
        if (count <= 1) {
            button_left.style.pointerEvents = "none";
        } else {
            button_left.style.pointerEvents = "auto";
        }
        if (count >= carouselCount) {
            button_right.style.pointerEvents = "none";
        } else {
            button_right.style.pointerEvents = "auto";
        }
    }

    function purchaseSingleMerchandise(id) {
        //先向后端发送请求，获取商品的最大数量，不能操作这一个数量
        //小于等于最大值,大于0;
        var purchaseSingleMerchandise = new XMLHttpRequest();
        var JSONResponse;

        purchaseSingleMerchandise.onreadystatechange = function () {
            if (purchaseSingleMerchandise.status === 200 && purchaseSingleMerchandise.readyState === 4) {
                JSONResponse = JSON.parse(purchaseSingleMerchandise.responseText);
            }
        }
        purchaseSingleMerchandise.open("GET", "${pageContext.request.contextPath}/select/SingleMerchandise?merchandiseId=" + id, false);
        purchaseSingleMerchandise.send();
        wait(JSONResponse);
    }

    function wait(JSONResponse) {
        var merchandiseId = JSONResponse.id;
        var merchandiseAmount = JSONResponse.amount;
        var purchaseAmount = prompt("请输入您想要购买的数量(您最多可购买的数量为" + merchandiseAmount + ")");
        if (purchaseAmount > merchandiseAmount) {
            alert("您购买的商品已达上限，您最终可购买" + merchandiseAmount);
        } else if (purchaseAmount < 1) {
            alert("请填写大于1的购买数量");
        } else if (purchaseAmount === undefined) {
            // alert("");
        } else {
            console.log("输入的商品数量符合条件");
            window.location.href = "${pageContext.request.contextPath}/order.jsp?merchandiseId=" + merchandiseId + "&" + "purchaseAmount=" + purchaseAmount;
        }
    }

</script>
</html>

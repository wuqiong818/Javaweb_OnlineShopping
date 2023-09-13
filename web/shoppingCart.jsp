<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/9
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="zh-CN" xml:lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta charset="UTF-8"/>
    <title>我的购物车-小米商城</title>
    <link rel="shortcut icon" href="//s01.mifile.cn/favicon.ico" type="image/x-icon"/>
    <link rel="icon" href="//s01.mifile.cn/favicon.ico" type="image/x-icon"/>
    <meta name="description" content=""/>
    <meta name="keywords" content=""/>
    <link href="${pageContext.request.contextPath}/CSS/cart2.382798e6.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/CSS/base.css" rel="stylesheet">
    <meta name="viewport" content="width=1226"/>
    <meta http-equiv="x-dns-prefetch-control" content="on">

    <link href="//cdn.cnbj1.fds.api.mi-img.com/mi.com-assets/shop/pro/css/buy/cart2.382798e6.css" rel="preload"
          as="style">
    <link href="//cdn.cnbj1.fds.api.mi-img.com/mi.com-assets/shop/pro/css/buy/cart2.382798e6.css" rel="stylesheet">
</head>
<body>
<div id="app">
    <div ref="J_siteHeader">
        <div class="site-header site-mini-header">
            <div class="container">
                <div class="header-logo">
                    <a class="logo ir" href="${pageContext.request.contextPath}/index.jsp" title="小米官网">小米官网</a>
                </div>
                <div class="header-title has-more" id="J_miniHeaderTitle">
                    <h2>
                        我的购物车
                    </h2>
                    <p>
                    </p>
                </div>
                <div class="site-topbar ">
                    <div class="topbar-info" id="J_siteUserInfo">
                        <a rel="nofollow" class="link" href="${pageContext.request.contextPath}/login.jsp"
                           data-agreement="true"
                           data-login="true"></a>
                        <span class="sep"></span>
                        <a rel="nofollow" class="link" href="${pageContext.request.contextPath}/signUp.jsp"
                           data-agreement="true"
                           data-register="true"></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="mi-cart page-main">
        <div class="container">
            <cart-main>
                <div class="cart_title">
                    <li class="checkBoxAll"><input type="checkbox" class="selectAllCheckbox">全选</li>
                    <li class="itemImg"><img></li>
                    <li class="name"><p>商品名称</p></li>
                    <li class="price">单价</li>
                    <li class="amount">数量</li>
                    <li class="count">小计</li>
                    <li class="cancel">操作</li>
                </div>
                <div class="replace">
                    <%--这里的id是购物车_商品表中的id,不是商品表中的id,我们通过这一个id,直接对相应商品数量进行修改即可--%>

                </div>
            </cart-main>
            <div class="purchase-bar clearfix">
                <li><a href="${pageContext.request.contextPath}">继续购物</a></li>
                <li class="selectedTotal">已选择<p>0</p>件</li>
                <li class="totalCostDom">合计<p>0</p>元</li>
                <li class="purchase">去结算</li>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    //对用户是否勾选了商品进行检查，如果勾选了,就让用户进入结算页面，如果没有勾选,则提醒用户勾选;
    var purchase = document.querySelector(".purchase");
    purchase.addEventListener("click", function () {
        var inputAll = document.querySelectorAll("input");
        var flag = false;
        inputAll.forEach((input) => {
            if (input.checked) {
                flag = true;
                return;
            }
        });
        if (flag) {
            window.location.href = "${pageContext.request.contextPath}/purchase/shoppingCart";
        } else {
            alert("请勾选商品");
        }
    });

    window.addEventListener("load", function () {
        var jsonDisplayMerchandiseInfo;
        var replace = document.querySelector(".replace");
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                jsonDisplayMerchandiseInfo = JSON.parse(xhr.responseText);
                jsonDisplayMerchandiseInfo.forEach(item => {
                        var isChecked;
                        if (item.isChecked === "1") {
                            isChecked = "checked";
                        }

                        replace.insertAdjacentHTML(`beforeend`, `
                        <div class="cart_item cart_title" data-status =` + item.merchandiseStatus + `>
                        <li class="checkBox"><input type="checkbox" class="inputCheckbox"  ` + isChecked + ` data-input="` + item.id + `"></li>
                        <li class="itemImg"><img alt="加载错误"
                                                 src="${pageContext.request.contextPath}/user_upload/images/` + item.photoPathArray[0] + `">
                        </li>
                        <li class="name"><p>` + item.name + `</p></li>
                        <li class="price">` + item.price + `</li>
                        <div class="amount_count">
                            <li id="` + item.id + `" class="amount itemAmount">
                                <i class="minus"></i>
                                <input type="text" value="` + item.amount + `">
                                <i class="addition"></i>
                                <span class="item_count"><p>0</p>元</span>
                            </li>
                        </div>
                        <li class="cancel" data-info="` + item.id + `"></li>
                    </div>
                    `);
                    });
            }
        }
        xhr.open("GET", "${pageContext.request.contextPath}/display/shoppingCart", false);
        xhr.send();
        then(jsonDisplayMerchandiseInfo);
    });


    function then(jsonDisplayMerchandiseInfo) {
        var selectedTotalDom = document.querySelector(".selectedTotal").querySelector("p");
        var totalCostDom = document.querySelector(".totalCostDom").querySelector("p");
        var cart_items = document.querySelectorAll(".cart_item");//每个商品，div
        var selectedTotal = 0;
        var totalCost = 0;
        var tempTotalCost = 0;


        cart_items.forEach(div => {
            var merchandiseStatus = parseInt(div.getAttribute("data-status"));
            var merchandiseName = div.querySelector(".name").textContent;
            var input = div.querySelector(".inputCheckbox");
            if (merchandiseStatus === 0) {
                // alert(merchandiseName + " 商品售空");
                var name = div.querySelector(".name");
                name.insertAdjacentHTML(`beforeend`,`
                <img alt="加载错误" src="${pageContext.request.contextPath}/images/soldOut.jpg">`);
                input.disabled = true;
                input.removeAttribute("checked");
            } else if (merchandiseStatus === 2) {
                // alert(merchandiseName + "商品临时下架");
                var name = div.querySelector(".name");
                name.insertAdjacentHTML(`beforeend`,`
                <img alt="加载错误" src="${pageContext.request.contextPath}/images/TemporaryRemoval.jpg">`);
                input.disabled = true;
                input.removeAttribute("checked");
            }
        })



        //现在需要做一个东西，在这个给每一个按钮绑定事件,他们的每一个点击都会使价格的总计和数量发送变化
        cart_items.forEach(div => {//这里这一个就相当于把所有的都循环过来一遍了
            var inputCheckbox = div.querySelector(".checkBox").querySelector(".inputCheckbox");
            inputCheckbox.addEventListener("click", function () {
                var id = div.querySelector(".itemAmount").id;
                var flag = inputCheckbox.checked;
                const count_pValue = div.querySelector(".item_count").querySelector("p").textContent;
                if (flag) {
                    selectedTotal++;
                    totalCost += parseFloat(count_pValue);
                    tempTotalCost = parseFloat(totalCost.toFixed(2));
                    selectedTotalDom.innerHTML = selectedTotal;
                    totalCostDom.innerHTML = tempTotalCost;
                    //向后端发送AJAX请求，进行数据的变更;id加上状态true,将数据库中的代码设置为1
                    updateStatusAjax(id, 1);
                } else {
                    selectedTotal--;
                    totalCost -= parseFloat(count_pValue);
                    tempTotalCost = parseFloat(totalCost.toFixed(2));
                    selectedTotalDom.innerHTML = selectedTotal;
                    totalCostDom.innerHTML = tempTotalCost;
                    //向后端发送AJAX请求，进行数据的变更;id加上状态true,将数据库中的代码设置为1
                    updateStatusAjax(id, 0);
                }
            });
        });

        const liElements = document.querySelectorAll('.itemAmount');
        const priceElements = document.querySelectorAll('.price');
        var i = 1;
        //这一个forEach是进行数据的预加载，刷新数据
        liElements.forEach(li => {
            const input = li.querySelector('input');
            let inputValue = parseInt(input.value);
            var price = parseFloat(priceElements.item(i++).textContent);
            inputValue = parseInt(input.value);
            const count_p = li.querySelector(".item_count").querySelector("p")
            count_p.innerHTML = (price * inputValue).toFixed(2);
        });

        cart_items.forEach(div => {
            var inputCheckbox = div.querySelector(".checkBox").querySelector(".inputCheckbox");
            if (inputCheckbox.checked) {
                const count_pValue = div.querySelector(".item_count").querySelector("p").textContent;
                selectedTotal++;
                totalCost += parseFloat(count_pValue);
                selectedTotalDom.innerHTML = selectedTotal;
                totalCostDom.innerHTML = totalCost.toFixed(2);
            }
        });
        /*
        * 购物车的增加和减少功能想简单了，不仅仅是其前端页面的变化，
        * 更加深层次的是后端数据库的变化，每一次点击加号和减号，
        * 都是前端向后端发送的一次AJAX请求，用来数据库中的数据，在用新的数据进行渲染.
        * */
        // 获取所有的 <li> 元素
        var j = 1;
        liElements.forEach(li => {
            var id = li.id;
            const input = li.querySelector('input');
            const minus = li.querySelector('.minus');
            const addition = li.querySelector('.addition');
            let inputValue = parseInt(input.value);
            const count_p = li.querySelector(".item_count").querySelector("p");
            var price = parseFloat(priceElements.item(j++).textContent);
            var start1 = 0;
            var end1 = 0;
            // 减号按钮点击事件
            minus.addEventListener('click', function () {
                var isChecked = li.parentNode.parentNode.querySelector(".inputCheckbox").checked;
                if (isChecked) {
                    var start = parseFloat(count_p.textContent);
                    inputValue = calculate(input, inputValue, -1, id);
                    var end = parseFloat((price * inputValue).toFixed(2));
                    totalCost -= start;
                    totalCost += end;
                    tempTotalCost = totalCost.toFixed(2);
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                    totalCostDom.innerHTML = tempTotalCost;
                } else {
                    inputValue = calculate(input, inputValue, -1, id);
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                }
            });

            // 加号按钮点击事件
            addition.addEventListener('click', function () {
                var isChecked = li.parentNode.parentNode.querySelector(".inputCheckbox").checked;
                if (isChecked) {
                    var start = (parseFloat(count_p.textContent));
                    inputValue = calculate(input, inputValue, 1, id);//这里的inputVuale,其实就是承接值的，更新值的；
                    var end = parseFloat((price * inputValue).toFixed(2));
                    totalCost -= start;
                    totalCost += end;
                    tempTotalCost = totalCost.toFixed(2);
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                    totalCostDom.innerHTML = tempTotalCost;
                } else {
                    inputValue = calculate(input, inputValue, 1, id);
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                }
            });
            input.addEventListener('focus', function () {
                start1 = input.value;
            })
            input.addEventListener('blur', function () {
                var inputCheck = li.parentNode.parentNode.querySelector(".inputCheckbox");
                var isChecked = inputCheck.checked;
                var id = inputCheck.getAttribute("data-input");
                var limitedAmount;
                var merchandiseName;
                /*
                * 这里用foreach，通过id去查找那个限额的数量
                * */
                jsonDisplayMerchandiseInfo.forEach(item => {
                    if (item.id === (parseInt(id))) {
                        limitedAmount = item.merchandiseAmount;
                        merchandiseName = item.name;
                    }
                });

                inputValue = input.value;//input是价格计算的数
                console.log("最初的inputValue" + inputValue);
                var pattern = /^[1-9]\d*$/;
                if (!pattern.test(input.value)) {
                    alert("非法字符,请输入数字");
                    input.value = "1";
                    inputValue = 1;
                }
                if (input.value == "") {
                    input.value = "1";
                    inputValue = 1;
                }
                if (input.value > limitedAmount) {
                    input.value = limitedAmount;
                    inputValue = limitedAmount;
                    alert(merchandiseName + "  这个商品已添加到最大数量");
                }
                if (isChecked) {
                    inputValue = parseInt(input.value);
                    end1 = inputValue;
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                    calculate(input, inputValue, 0, id);
                    totalCost -= start1 * price;
                    totalCost += end1 * price;
                    tempTotalCost = totalCost.toFixed(2);
                    totalCostDom.innerHTML = tempTotalCost;
                } else {
                    inputValue = parseInt(input.value);
                    count_p.innerHTML = (price * inputValue).toFixed(2);
                    calculate(input, inputValue, 0, id);
                }
            });
        });

        // 计算函数，处理增加和减少逻辑
        function calculate(input, currentValue, change, id) {
            var limitedAmount;
            var merchandiseName;
            /*
            * 这里用foreach，通过id去查找那个限额的数量
            * */
            jsonDisplayMerchandiseInfo.forEach(item => {
                if (item.id === (parseInt(id))) {
                    limitedAmount = item.merchandiseAmount;
                    merchandiseName = item.name;
                }
            });

            if (change == 1 || change == -1 || change == 0) {
                currentValue += change;
                if (currentValue < 1) {
                    currentValue = 1;
                } else if (currentValue > limitedAmount) {
                    currentValue = limitedAmount;
                    alert(merchandiseName + "此商品已添加到最大数量");
                }
                input.value = currentValue;
            }

            //在这里编写AJAX的代码
            //在这里向后台发送数据 id，amount即可，仅仅发送数据即可，并不进行修改和页面的跳转
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    //其实后端需要像前端发送数据，判断购物车中的商品是否还可以继续添加，如果可以继续添加的话，不能继续添加的话，弹出警告框，已达到最大额度。
                    //这里是需要发挥数据的，id,商品名，该商品的最大值;
                    console.log("向后端发送请求成功,进行购物车数量的更改");
                }
            }
            xhr.open("POST", "${pageContext.request.contextPath}/updateAmount/shoppingCart", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
            var data = "id=" + encodeURIComponent(id) + "&amount=" + encodeURIComponent(currentValue);
            xhr.send(data);
            return currentValue;
        }

        //全选按钮
        var selectAllCheckbox = document.querySelector(".selectAllCheckbox");
        var Checkboxs = document.querySelectorAll(".inputCheckbox");
        var cart_items = document.querySelectorAll(".cart_item");
        // 当全选按钮被点击时
        selectAllCheckbox.addEventListener("click", function () {
            var isChecked = selectAllCheckbox.checked;
            var selectedTotal_p = document.querySelector(".selectedTotal").querySelector("p");
            var checkBoxCount = 0;
            if (isChecked) {
                // 遍历所有商品选择按钮，设置它们的状态为全选按钮的状态
                Checkboxs.forEach(function (checkbox) {
                    const count_pValue = checkbox.parentNode.parentNode.querySelector(".item_count").querySelector("p").textContent;
                    if (!checkbox.checked) {
                        totalCost += parseFloat(count_pValue);
                    }
                    checkbox.checked = isChecked;//返回true和false;
                    var id = checkbox.getAttribute("data-input");
                    //封装一个AJAX请求，发送id和checked的状态，0为取消，1为勾选
                    updateStatusAjax(id, 1);
                    checkBoxCount++;
                });
                selectedTotal = checkBoxCount;
                selectedTotal_p.innerHTML = selectedTotal;
                totalCostDom.innerHTML = totalCost.toFixed(2);
            } else {
                Checkboxs.forEach(function (checkbox) {
                    checkbox.checked = isChecked;//返回true和false;
                    var id = checkbox.getAttribute("data-input");
                    //封装一个AJAX请求，发送id和checked的状态，0为取消，1为勾选
                    updateStatusAjax(id, 0);
                    console.log("发送到AJAX中的id=" + id);
                });
                selectedTotal = 0;
                selectedTotal_p.innerHTML = selectedTotal;
                totalCost = 0;
                totalCostDom.innerHTML = totalCost.toFixed(2);
            }
        });

        //目前缺少一个功能，向后端发送请求，实现isChecked的同步勾选;id，isChecked;
        //在这里封装一个AJAX,用于数据库表isChecked列状态的更新
        function updateStatusAjax(id, status) {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    console.log("请求发送成功" + id);
                }
            }
            xhr.open("POST", "${pageContext.request.contextPath}/updateStatus/shoppingCart", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            var data = 'id=' + encodeURIComponent(id) + '&status=' + encodeURIComponent(status);
            xhr.send(data);
        }

        //用户取消按钮，删除购物车中的商品
        const cancels = document.querySelectorAll('.cancel');
        console.log(cancels);
        cancels.forEach(li => {
            li.addEventListener("click", function () {
                if (confirm("确定要删除该商品吗？")) {
                    // 在这里执行删除商品的操作，可以是发送请求到服务器删除数据等操作
                    alert("商品正在删除中！");
                    var id = li.getAttribute("data-info");
                    //发送post请求，进行商品数据的修改
                    var xhr = new XMLHttpRequest();
                    xhr.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            console.log("向后端发送请求，进行数据删除成功了");
                            window.location.reload();
                        }
                    }
                    xhr.open("POST", "${pageContext.request.contextPath}/delete/shoppingCart", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
                    var data = "id=" + encodeURIComponent(id);
                    xhr.send(data);
                } else {
                    // 用户点击了取消，不执行任何操作
                    alert("取消删除！");
                }
            })
        });
    }
</script>
</html>
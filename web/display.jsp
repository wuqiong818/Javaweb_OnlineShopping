<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/5
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品展示</title>
    <base href="http://localhost:8080/OnlineShopping/">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/display.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/base.css">
    <script src="${pageContext.request.contextPath}/JS/display.js"></script>
</head>
<body>
<header class="header">
    <div class="w">
        <div class="logo">
            <h1>
                <a href="${pageContext.request.contextPath}/index.jsp">小米商城</a>
            </h1>
        </div>
        <div class="header_nav">
            <li><a href="${pageContext.request.contextPath}/index.jsp">首页 &nbsp; &gt; </a></li>
            <li><a href="javascript:;">商品展示</a></li>
        </div>
        <div class="search">
            <input type="search" placeholder="音乐" class="input_search">
            <input type="submit" value="" class="input_sbumit">
            <div class="searchSuggestions">
            </div>
        </div>
    </div>
</header>
<div class="screen">
    <script>
        function searchDetails(id) {
            window.location.href = "${pageContext.request.contextPath}/detail/merchandise?merchandiseId=" + id;
        }
    </script>
    <script>
        window.addEventListener("load", function () {
            // 这个是模糊搜索的AJAX;
            var inputSearch = document.querySelector(".input_search");
            var searchSuggestions = document.querySelector(".searchSuggestions");
            var input_sbumit = document.querySelector(".input_sbumit");
            var keyword;
            var SearchResultJson;
            inputSearch.addEventListener("input", function () {
                keyword = inputSearch.value.trim();
                searchSuggestions.innerHTML = "";
                // 当输入框不为空时发送 AJAX 请求
                if (keyword !== "" && keyword != null) {
                    if (xhr) {
                        // 取消之前的请求，以防止并发请求,因为用户可能连续键入,我们请求最新的结果；
                        xhr.abort();
                    }
                }
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // 解析服务器返回的 JSON 数据
                        SearchResultJson = JSON.parse(xhr.responseText);
                        SearchResultJson.forEach(function (item) {
                            function createLiElement(item) {
                                const li = document.createElement('li');
                                const link = document.createElement('a');
                                link.textContent = item.name;
                                li.appendChild(link);
                                li.addEventListener('click', function () {
                                    window.location.href = "${pageContext.request.contextPath}/detail/merchandise?merchandiseId=" + item.id;
                                });
                                return li;
                            }

                            searchSuggestions.style.borderTop = "1px";
                            const li = createLiElement(item);
                            searchSuggestions.appendChild(li);
                        });
                    }
                }

                if ((keyword !== "" && keyword != null)) {//消除空白关键词问题
                    xhr.open("GET", "${pageContext.request.contextPath}/search/merchandise?keyword=" + keyword, true);
                    xhr.send();
                }
            });


            //点击事件的结果，在新的一页展示是比较合理舒服的，可以试一试;
            var searchBut = document.querySelector(".input_sbumit");
            searchBut.addEventListener("click", function () {
                if ((keyword !== "" && keyword != null)) {
                    window.location.href = "${pageContext.request.contextPath}/search.jsp?keyword=" + keyword;
                } else {
                    alert("请输入内容");
                }
            });
            // 获取输入框元素
            var inputElement = document.querySelector(".input_search"); // 替换为实际的输入框ID
            // 添加键盘事件监听器
            inputElement.addEventListener("keyup", function(event) {
                // 检查是否按下的是Enter键 (键码13)
                if (event.keyCode === 13) {
                    if ((keyword !== "" && keyword != null)) {
                        window.location.href = "${pageContext.request.contextPath}/search.jsp?keyword=" + keyword;
                    } else {
                        alert("请输入内容");
                    }
                }
            });


            inputSearch.addEventListener("focus", function () {
                inputSearch.style.border = '1px solid #ff6700';
                input_sbumit.style.border = '1px solid #ff6700';
                input_sbumit.style.borderLeft = '0px';
                searchSuggestions.style.display = 'block';
            });
            inputSearch.addEventListener("blur", function () {
                setTimeout(function () {
                    inputSearch.style.border = '1px solid #e0e0e0';
                    input_sbumit.style.border = '1px solid #e0e0e0';
                    input_sbumit.style.borderLeft = '0px';
                    searchSuggestions.style.display = 'none';
                }, 150);
            });
        });
    </script>
</div>
<div class="exhibition clearfix">
    <div class="w">
        <div class="exhibition_header">
            <div class="exhibition_tab">
                <ul>
                    <li class="tab_title">
                        <div class="tab_container">
                            <ul class="tab_content">
                                <%--在这里进行数据的动态加载--%>
                                <c:forEach items="${allMerchandise}" var="item" varStatus="index">
                                    <div class="merchandiseItem">
                                        <li onclick="details(${item.id}) "><img
                                                src="${pageContext.request.contextPath}/user_upload/images/${item.photoPathArray[0]}"
                                                alt="稍后再试">
                                            <h3>${item.name}</h3>
                                            <p>${item.featureArray[0]} &nbsp;|&nbsp;${item.featureArray[1]}
                                                &nbsp;|&nbsp;${item.featureArray[2]}</p>
                                            <span>${item.price}元</span>
                                        </li>
                                        <span>
                                            <%--使用用户购买功能是一定是需要检查用户是否登录的，如果用户登录了，就跳转到购买的连接，如果没有登录，就登录去
                                                用户在未登录状态下，可以添加商品到购物车里面，未登录是添加的商品以cookie的形式存在，如果以登录的话或登录后，将Cookie中的商品添加到数据库的购物车里面
                                            --%>
                                            <a class="merchandisePurchase"
                                               onclick="purchaseSingleMerchandise(${item.id})">购买</a>
                                            <a class="addShoppingCart"
                                               href="${pageContext.request.contextPath}/add/shoppingCart?merchandiseId=${item.id}">加入购物车</a>
                                        </span>
                                    </div>
                                </c:forEach>
                                <script>
                                    function details(id) {
                                        window.location.href = "${pageContext.request.contextPath}/detail/merchandise?merchandiseId=" + id;
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

                                    //现在这里堆得问题太多了，需要一点一点地去解决，而不是全部都往后面拖;
                                    /*
                                    * 1.先将点击购买之后，页面进行跳转的问题给解决了;
                                    * 2.如果用户直接购买力，我一定是需要向后端发送商品的id和用户所填写的数量，后端处理一下，前端在展示
                                    * 由于现在的结算页面是使用的是ajax,直接返回的是数据库中的数据，不太对头；
                                    * */
                                    function wait(JSONResponse) {
                                        var merchandiseId = JSONResponse.id;
                                        var merchandiseAmount = JSONResponse.amount;
                                        var purchaseAmount = prompt("请输入您想要购买的数量(您最多可购买的数量为" + merchandiseAmount+")");
                                        if (purchaseAmount > merchandiseAmount) {
                                            alert("您购买的商品已达上限，您最终可购买" + merchandiseAmount);
                                        } else if (purchaseAmount < 1) {
                                            alert("请填写大于1的购买数量");
                                        } else if (purchaseAmount === undefined) {
                                            // alert("");
                                        } else {
                                            console.log("输入的商品数量符合条件");
                                            window.location.href = "${pageContext.request.contextPath}/order.jsp?merchandiseId="+merchandiseId+"&"+"purchaseAmount="+purchaseAmount;
                                        }
                                    }


                                </script>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<footer>

</footer>
</body>
</html>
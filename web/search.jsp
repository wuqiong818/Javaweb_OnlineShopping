<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/9/6
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>搜索展示</title>
    <base href="http://localhost:8080/OnlineShopping/">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/display.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/base.css">
    <script src="${pageContext.request.contextPath}/JS/display.js"></script>
    <style>


    </style>

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
            <li><a href="javascript:;">搜索结果</a></li>
        </div>
        <div class="search">
            <input type="search" placeholder="显示器" class="input_search">
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
                            <ul class="tab_content" style="position: absolute ;top: 10px">
                                <script>
                                    <%--在这里进行数据的动态加载--%>
                                    window.addEventListener("load", function () {
                                        var currentURL = window.location.href;
                                        // 使用 URL 对象解析 URL
                                        var url = new URL(currentURL);
                                        // 获取 keyword 参数的值
                                        var keywordValue = url.searchParams.get("keyword");

                                        var main = document.querySelector("main");
                                        var xhrDisplay = new XMLHttpRequest();
                                        xhrDisplay.onreadystatechange = function () {
                                            if (xhrDisplay.readyState === 4 && xhrDisplay.status === 200) {
                                                var str = xhrDisplay.responseText;
                                                var SearchResultJson = JSON.parse(str);
                                                var searchSug = document.querySelector(".tab_content");
                                                searchSug.innerHTML = "";
                                                console.log("SearchResultJson=" + SearchResultJson);
                                                console.log("str"+str);
                                                console.log("str.length"+str.length);
                                                if (str!=="[]" || str.length !== 2) {
                                                    searchSug.insertAdjacentHTML(`beforeend`, `
                                                    <h1 style="margin-bottom: 10px;font-weight:300">全部搜索结果</h1>`)
                                                    SearchResultJson.forEach((item) => {
                                                        searchSug.insertAdjacentHTML(`beforeend`, `
                                                        <div class="merchandiseItem">
                                                        <li onclick="details(` + item.id + `) ">
                                                            <img src="${pageContext.request.contextPath}/user_upload/images/` + item.photoPathArray[0] + `"
                                                                 alt="稍后再试">
                                                            <h3>` + item.name + `</h3>
                                                            <p>` + item.featureArray[0] + ` &nbsp;|&nbsp;` + item.featureArray[1] + `
                                                                &nbsp;|&nbsp;` + item.featureArray[2] + `</p>
                                                            <span>` + item.price + `元</span>
                                                            </li>
                                                            <span>
                                                                <a href="javascript:;">购买</a>
                                                                <a href="javascript:;">加入购物车</a>
                                                            </span>

                                                        </div>
                                                        `)
                                                    });
                                                } else {
                                                    searchSug.innerHTML = "<h1>对不起,暂时没有您搜索的商品</h1>";
                                                }
                                            }
                                        }
                                        xhrDisplay.open("GET", "${pageContext.request.contextPath}/search?keyword=" + keywordValue, true);
                                        xhrDisplay.send();
                                    })
                                </script>

                                <script>
                                    function details(id) {
                                        window.location.href = "${pageContext.request.contextPath}/detail/merchandise?merchandiseId=" + id;
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

<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/16
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人中心</title>
    <link href="CSS/personalHomepage.css" rel="stylesheet">
    <link href="CSS/base.css" rel="stylesheet">
</head>
<body>
<div class="w">
    <header>
        <div class="header">
            <a class="logo" href="${pageContext.request.contextPath}/index.jsp" title="小米官网">小米官网</a>
            <div class="header-title">
                <h2>
                    个人中心
                </h2>
            </div>
        </div>
    </header>
    <main>
        <div class="tab_title">
            <li id="page1Btn">个人信息</li>
            <br>
            <li id="page2Btn">购买记录</li>
            <br>
            <li id="page3Btn">我的商品</li>
            <br>
            <li id="page4Btn">我的订单</li>
        </div>
        <div class="tab_content active" id="userInfo">
            <%--    //这里个人信息的提交，不应该使用form表单,可以用户到用户的id,这个是没有问题的，使用用户的id，来保存这些数据--%>
            <form method="post" action="${pageContext.request.contextPath}/user/information"
                  enctype="multipart/form-data" id="form">
            </form>
        </div>

        <div class="tab_content" id="purchaseHistory">
        </div>

        <div class="tab_content" id="myGoods">
            <h1>我的商品</h1>
            <%--            <div class="cart_title">--%>
            <%--                <li class="itemImg"><img style="visibility: hidden"></li>--%>
            <%--                <li class="name"><p>商品名称</p></li>--%>
            <%--                <li class="price">单价</li>--%>
            <%--                <li class="amount">剩余数量</li>--%>
            <%--                <li class="operate">操作</li>--%>
            <%--            </div>--%>
            <%--    AJAX自动异步加载     --%>
        </div>

        <div class="tab_content" id="myOrder">

        </div>


        <script>
            var inputAll = null;
            window.addEventListener("load", function () {
                var xhr = new XMLHttpRequest();
                var form = document.querySelector("#form");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var jsonResponse = JSON.parse(xhr.responseText);
                        var telephone = jsonResponse.telephone;
                        var address = jsonResponse.address;

                        // console.log("telephone="+typeof telephone);
                        // console.log("telephone="+telephone);

                        if (telephone === undefined){
                            telephone="";
                        }
                        if (address === undefined){
                            address="";
                        }

                        form.insertAdjacentHTML(`beforeend`, `
                    <label for="userImage"></label>
                    <div><img class="displayImage" src="user_upload/images/` + jsonResponse.profilePath + `" alt="图片未加载"></div>
                    <input type="file" id="userImage" name="userImage" accept="image/*" required>
                    <div id="message"></div>
                    <br>
                    <br>
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" value = "` + jsonResponse.username + `" required>
                    <br>
                    <br>
                    <label for="telephone">手机号:</label>
                    <input type="text" id="telephone" name="telephone" value = "` + telephone + `" required>
                    <br>
                    <br>
                    <label for="address">地址:</label>
                    <input type="text" id="address" name="address"  value="` + address + `" required>
                    <br>
                    <br>
                    <button>修改密码</button>
                    <label for="updatePassword">修改密码:</label>
                    <input type="text" id="updatePassword" name="password" value="` + jsonResponse.password + `">
                    <br>
                    <br>
                    `)
                    }
                }
                xhr.open("GET", "${pageContext.request.contextPath}/display/information", false);
                xhr.send();
                then1();
            });


            window.addEventListener("load", function () {
                var xhr2 = new XMLHttpRequest();
                xhr2.onreadystatechange = function () {
                    if (xhr2.readyState === 4 && xhr2.status === 200) {
                        var jsonResponse = JSON.parse(xhr2.responseText);
                        var index = 0;
                        var purchaseHistory = document.getElementById("purchaseHistory");
                        purchaseHistory.insertAdjacentHTML(`beforeend`, `
                            <h1>购买记录</h1>
                             `);
                        jsonResponse.forEach((item) => {//没有办法一次拼接而成只能多次拼接;
                            purchaseHistory.insertAdjacentHTML(`beforeend`, `
                               <div class="history_item">
                                   <li class="orderId">购买编号:` + "0000" + item.id + `</li>
                                    <div class="orderMerchandiseInfo"></div>
                                   <li class="count">总计:` + item.orderTotal + `</li>
                               </div>
                               `);

                            var orderMerchandiseInfo = document.querySelectorAll(".orderMerchandiseInfo")[index];
                            index++;

                            var merchandises = item.merchandises;
                            var amountArray = item.merchandiseAmountArray;
                            for (var k = 0; k < merchandises.length; k++) {
                                var merchandise = merchandises[k];
                                var amount = amountArray[k];
                                orderMerchandiseInfo.insertAdjacentHTML(`beforeend`, `
                                <li class="name">商品名 :` + merchandise.name + `</li>
                                <li class="price">价格:` + merchandise.price + `</li>
                                <li class="amount">数量` + amount + `</li>
                                <hr>
                            `);
                            }
                        }
                            )
                    }
                }
                //向后端发送数据进行数据的查找;
                xhr2.open("GET", "${pageContext.request.contextPath}/order/display", true);
                xhr2.send();
                // then2();
            });

            window.addEventListener("load", function () {
                var xhr3 = new XMLHttpRequest();
                xhr3.onreadystatechange = function () {
                    if (xhr3.readyState == 4 && xhr3.status == 200) {
                        var jsonMyGoods = JSON.parse(xhr3.responseText);
                        var myGoods = document.querySelector("#myGoods");

                        if (jsonMyGoods.length !== 0) {
                            myGoods.insertAdjacentHTML(`beforeend`, `
                            <div class="cart_title">
                                <li class="itemImg"><img style="visibility: hidden"></li>
                                <li class="name"><p>商品名称</p></li>
                                <li class="price">单价</li>
                                <li class="amount">剩余数量</li>
                                <li class="operate">操作</li>
                            </div>
                        `)
                        } else {
                            myGoods.insertAdjacentHTML(`beforeend`, `
                            <br>
<br>
                            <h2>请上传商品后再来试试吧!</h2>
                        `)
                        }
                        /*上传商品的用户可以对商品进行三个操作 商品对商品进行修改、下架(点击按钮之后，下架变成上架)、删除*/
                        jsonMyGoods.forEach((item) => {
                            myGoods.insertAdjacentHTML(`beforeend`, `
                                <div class="myGoods_item cart_title" id="` + item.id + `">
                                    <li class="itemImg"><img alt="加载错误"
                                                             src="${pageContext.request.contextPath}/user_upload/images/` + item.photoPathArray[0] + `">
                                    </li>
                                    <li class="name"><p>` + item.name + `</p></li>
                                    <li class="price">` + item.price + `元</li>
                                    <li class="amount_count">` + item.amount + `个</li>
                                    <div class="operate">
                                        <div class ="shelf" data-status="` + item.merchandiseStatus + `">
                                            <button class="newArrival" disabled = true >上架</button>
                                            <button class="stopMerchandise">下架</button>
                                        </div>
                                        <div class="update_delete">
                                            <button class="update">修改</button>
                                            <button class="deleteMerchandise">删除</button>
                                        </div>
                                    </div>
                                </div>
                            `);
                        })
                    }
                }
                xhr3.open("GET", "${pageContext.request.contextPath}/display/myGoods", false);
                xhr3.send();
                then3();
            });


            window.addEventListener("load", function () {
                var xhr4 = new XMLHttpRequest();
                xhr4.onreadystatechange = function () {
                    if (xhr4.readyState == 4 && xhr4.status == 200) {
                        var list = JSON.parse(xhr4.responseText);
                        var index = 0;
                        var myOrder = document.getElementById("myOrder");
                        myOrder.insertAdjacentHTML(`beforeend`, `
                            <h1>订单记录</h1>
                             `);
                        list.forEach((item) => {//没有办法一次拼接而成只能多次拼接;
                            myOrder.insertAdjacentHTML(`beforeend`, `
                               <div class="history_item">
                                   <li class="orderId">订单编号:` + "0000" + item.id + `</li>

                                    <div class="orderMerchandiseInfo"></div>
                                      <div class="expressInformation">
                                        <li class="receiverName">收货人:` + item.receiverName + `</li>
                                        <li class="telephone">联系电话:` + item.telephone + `</li>
                                        <li class="address">收货地址:` + item.address + `</li>
                                      </div>
                               </div>
                               `);

                            var orderMerchandiseInfo = document.querySelectorAll(".orderMerchandiseInfo")[index];
                            index++;
                            var merchandises = item.merchandises;
                            var amountArray = item.merchandiseAmountArray;
                            for (var k = 0; k < merchandises.length; k++) {
                                var merchandise = merchandises[k];
                                var amount = amountArray[k];
                                orderMerchandiseInfo.insertAdjacentHTML(`beforeend`, `
                                <li class="name">商品名 :` + merchandise.name + `</li>
                                <li class="price">价格:` + merchandise.price + `</li>
                                <li class="amount">数量` + amount + `</li>
                                <hr>
                            `);
                            }
                        }
                            )
                    }
                }
                xhr4.open("GET", "${pageContext.request.contextPath}/order/displayByMerchantId", false);
                xhr4.send();
            });


            function then3() {
                //一个是商品的下架，对商品的状态状态进行更改，merchandiseStatus = 2;
                //先获取所有的元素，进行事件的绑定，两个点击事件的绑定;

                //预加载，对一开始的商品状态进行预加载，禁用不同的上架和下架的按钮;
                var uploadedMerchandise = document.querySelectorAll(".myGoods_item");
                uploadedMerchandise.forEach((item) => {
                    var newArrivalButton = item.querySelector(".newArrival");
                    var stopButton = item.querySelector(".stopMerchandise");
                    var merchandiseStatus = parseInt(item.querySelector(".shelf").getAttribute("data-status"));
                    var merchandiseName = item.querySelector(".name").querySelector("p").textContent;
                    if (merchandiseStatus === 1) {
                        newArrivalButton.disabled = true;
                        stopButton.removeAttribute("disabled");
                    } else if (merchandiseStatus === 2) {
                        stopButton.disabled = true;
                        newArrivalButton.removeAttribute("disabled");
                    } else if (merchandiseStatus === 0) {
                        // alert("恭喜您,您的 "+merchandiseName+"商品已经售空");
                        item.style.color = "#63d24c"
                    }
                });


                uploadedMerchandise.forEach((item) => {
                    var newArrivalButton = item.querySelector(".newArrival");
                    var stopButton = item.querySelector(".stopMerchandise");
                    var updateButton = item.querySelector(".update");
                    var deleteButton = item.querySelector(".deleteMerchandise");
                    var merchandiseId = item.id;
                    var merchandiseName = item.querySelector(".name").querySelector("p").textContent;

                    newArrivalButton.addEventListener('click', function () {
                        //商品的状态从2改成1即可;
                        var flag = confirm("确定重新上架 " + merchandiseName + " 这个商品吗？");
                        if (flag) {
                            newArrivalButton.disabled = true;
                            stopButton.removeAttribute("disabled");
                            var xhrNewArrival = new XMLHttpRequest();
                            xhrNewArrival.onreadystatechange = function () {
                                if (xhrNewArrival.readyState === 4 && xhrNewArrival.status === 200) {

                                }
                            }
                            xhrNewArrival.open("POST", "${pageContext.request.contextPath}/newArrival/merchandise", true);
                            xhrNewArrival.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                            xhrNewArrival.send("merchandiseId=" + merchandiseId);
                        }
                    });


                    stopButton.addEventListener('click', function () {
                        var flag = confirm("确定下架 " + merchandiseName + " 这个商品吗？");
                        if (flag) {
                            stopButton.disabled = true;
                            newArrivalButton.removeAttribute("disabled");
                            console.log("下架商品了");
                            var xhrStop = new XMLHttpRequest();
                            xhrStop.onreadystatechange = function () {
                                if (xhrStop.readyState === 4 && xhrStop.status === 200) {

                                }
                            }
                            xhrStop.open("POST", "${pageContext.request.contextPath}/stop/merchandise", true);
                            xhrStop.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                            xhrStop.send("merchandiseId=" + merchandiseId);
                        }
                        //商品的状态从1改成2即可;
                    });

                    //merchandiseID已经实现了，不用管了
                    updateButton.addEventListener('click', function () {
                        window.location.href = "${pageContext.request.contextPath}/updateMyGoods.jsp?merchandiseId=" + merchandiseId;

                    });
                    //删除这个也简单
                    deleteButton.addEventListener('click', function () {
                        var flag = confirm("确定删除 " + merchandiseName + " 这个商品吗？");
                        if (flag) {
                            var xhrDelete = new XMLHttpRequest();
                            xhrDelete.onreadystatechange = function () {
                                if (xhrDelete.readyState === 4 && xhrDelete.status === 200) {

                                }
                            }
                            xhrDelete.open("POST", "${pageContext.request.contextPath}/delete/merchandise", true);
                            xhrDelete.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                            xhrDelete.send("merchandiseId=" + merchandiseId);
                            alert("删除成功");
                            location.reload();
                            //为什么不能删除呢？原因，外键的约束，需要先改子表，再改父表;
                            //商品为什么不能删除，因为位order表中的字段引用了,所以没有办法去删除;
                        }
                    });
                })
            }

            /*梳理一下：这里的东西有一点点乱
            *难的不是代码问题，难的是思路问题，思路并不会因你是否使用框架而变得简单;思路问题很重要，逻辑
            * 1.当我进入这一个页面后，首先进行DOM树构建，如果之前完善过信息，这里就从后端数据库中取数据，没有完善信息的话就显示空白，而不是underfined;
            * 2.当我再次选择图片的时候，应该连接数据库，进行数据库图片的切换，为什么，因为不会再有提交按钮了，所以你需要通过ajax进行事件的变换
            * 3.当修改其他信息时，也同样执行这一个东西，但是值得注意的是，当修改密码时，需要进行提醒、
            * 如果用户仅仅修改了一项信息，那么其他信息保持原样，不要直接覆盖为空白，这个很可怕。
            * 图片更改和其他信息更改，这些东西，使用一个AJAX吗？思考一下？？？
            * */
            function then1() {
                inputAll = document.querySelectorAll("input");
                var userImageInput = document.getElementById("userImage");
                var messageDiv = document.getElementById("message");
                inputAll.forEach((input) => {
                    if (input.id !== "updatePassword") {
                        input.addEventListener("change", function () {
                            if (input.id === "userImage") {
                                var selectedFile = userImageInput.files[0];
                                if (selectedFile && selectedFile.type.startsWith("image/")) {
                                    messageDiv.textContent = "头像更新完成";
                                    messageDiv.style.color = "green";
                                    //在这里向后端发送ajax请求，相应回来的数据为text即可;
                                    var xhr = new XMLHttpRequest();
                                    xhr.onreadystatechange = function () {
                                        if (xhr.readyState === 4 && xhr.status === 200) {
                                            var responseText = JSON.parse(xhr.responseText);
                                            document.querySelector(".displayImage").src = "${pageContext.request.contextPath}/user_upload/images/" + responseText.profilePath;
                                        }
                                    }
                                    var formData = new FormData(document.querySelector("#form"));
                                    xhr.open("POST", "${pageContext.request.contextPath}/update/information", true);
                                    xhr.send(formData);
                                } else {
                                    messageDiv.textContent = "请选择图片文件。";
                                    messageDiv.style.color = "red";
                                }
                            } else if (input.id === "username" || input.id === "telephone" || input.id === "address") {
                                //在这里向后端发送ajax请求，相应回来的数据为text即可;
                                xhr = new XMLHttpRequest();
                                xhr.onreadystatechange = function () {
                                    if (xhr.readyState === 4 && xhr.status === 200) {
                                        //这里还是需要进行页面数据的更新的，获取form表单中的内容,更新前端的页面。
                                    }
                                }
                                var formData = new FormData(document.querySelector("#form"));
                                formData.delete("userImage");
                                xhr.open("POST", "${pageContext.request.contextPath}/update/information", true);
                                xhr.send(formData);
                            }
                        });
                    }

                    //这个是对密码进行修改
                    if (input.id === "updatePassword") {
                        input.addEventListener("focus", function () {
                            input.blur();
                            var newPassword;
                            if (confirm("确定修改密码吗?")) {
                                newPassword = prompt("自动删除空格和非法字符").trim();
                                if (newPassword === "" || newPassword === " ") {
                                    alert("非法输入，请重新修改");
                                } else {
                                    input.value = newPassword;
                                    var xhr = new XMLHttpRequest();
                                    xhr.onreadystatechange = function () {
                                        if (xhr.readyState === 4 && xhr.status === 200) {
                                            //这里不接收数据，而是让用户进行重新登录；
                                            window.alert("密码更新成功，请重新登录");
                                            window.location.href = "${pageContext.request.contextPath}/user/exit";
                                            //需要销毁会话机制;销毁id名为JSESSIONID的cookie;
                                            //前端JS代码无法销毁"JSESSIONID" Cookie,通过向后端发送请求，进行Cookie的销毁。
                                        }
                                    }

                                    var formData = new FormData(document.querySelector("#form"));
                                    formData.delete("userImage");
                                    xhr.open("POST", "${pageContext.request.contextPath}/update/information", true);
                                    xhr.send(formData);
                                }
                            }
                        });
                    }
                });
            }

            var page1Btn = document.getElementById("page1Btn");
            var page2Btn = document.getElementById("page2Btn");
            var page3Btn = document.getElementById("page3Btn");
            var page4Btn = document.getElementById("page4Btn");

            var page1 = document.getElementById("userInfo");
            var page2 = document.getElementById("purchaseHistory");
            var page3 = document.getElementById("myGoods");
            var page4 = document.getElementById("myOrder");


            page1Btn.addEventListener("click", function () {
                page1.classList.add("active");
                page2.classList.remove("active");
                page3.classList.remove("active");
                page4.classList.remove("active");

            });

            page2Btn.addEventListener("click", function () {
                page2.classList.add("active");
                page1.classList.remove("active");
                page3.classList.remove("active");
                page4.classList.remove("active");

            });

            page3Btn.addEventListener("click", function () {
                page3.classList.add("active");
                page1.classList.remove("active");
                page2.classList.remove("active");
                page4.classList.remove("active");
            });

            page4Btn.addEventListener("click", function () {
                page4.classList.add("active");
                page1.classList.remove("active");
                page2.classList.remove("active");
                page3.classList.remove("active");
            });
        </script>
    </main>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/9/10
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改密码</title>
    <link rel="stylesheet" href="./CSS/login_sign.css">
</head>

<body>
<div class="shell">
    <div class="container a-container is-txl" id="a-container">
        <form action="${pageContext.servletContext.contextPath}/user/signUp" method="post" class="form" id="a-form">
            <h2 class="form_title title">注册账号</h2>
            <span class="form_span"></span>
            <input type="text" class="form_input" placeholder="Username" name="username">
            <input type="password" class="form_input" placeholder="Password" name="password">
            <input type="text" class="form_input" placeholder="Telephone" name="telephone">
            <input class="form_button button" type="submit" value="SIGN UP">
        </form>
    </div>

    <div class="container b-container is-txl is-z" id="b-container">
        <form action="${pageContext.servletContext.contextPath}/user/login" method="post" class="form" id="b-form">
            <h2 class="form_title title">登录账号</h2>
            <input type="text" class="form_input" placeholder="Username" name="username">
            <input type="password" class="form_input" placeholder="Password" name="password">
            <span style="width: 350px ; height:40px ;overflow: hidden">
                <input class="form_input" style="float: left;width: 267px" type="text" placeholder="verification code"
                       name="verificationCode">
                <img id="codeImg" style="margin-top: 3px;margin-left: 3px; width:80px;height: 60px"
                     src="${pageContext.servletContext.contextPath}/AuthCode/code" onclick="refreshCode()">
            </span>
            <input style="margin-left:-326px;margin-top:12px; width:20px;height:20px" type="checkbox" name="checkbox">
            <p style="margin-top: -18px;margin-left: -210px;font-size: 13px">十天内免登录</p>
            <input class="form_button button" type="submit" value="LOGIN">
            <script type="text/javascript">
                function refreshCode() {
                    var codeImg = document.getElementById("codeImg");
                    var d = new Date();
                    codeImg.src = "${pageContext.servletContext.contextPath}/AuthCode/code?s=" + d;
                }
            </script>
        </form>
    </div>

    <div class="switch is-txr" id="switch-cnt">
        <div class="switch_circle"></div>
        <div class="switch_circle switch_circle-t"></div>
        <div class="switch_container" id="switch-c1">
            <h2 class="switch_title title" style="letter-spacing: 0;">Welcome Back！</h2>
            <p class="switch_description description">已经有账号了，去登陆账号进入奇妙世界吧！！！</p>
            <button class="switch_button button switch-btn">SIGN IN</button>
        </div>

        <div class="switch_container is-hidden" id="switch-c2">
            <h2 class="switch_title title" style="letter-spacing: 0;">Hello Friend！</h2>
            <p class="switch_description description">去注册一个账号，成为尊贵的粉丝会员，让我们踏入奇妙的旅途！</p>
            <button class="switch_button button switch-btn">SIGN UP</button>
        </div>
    </div>
</div>
<script src="./JS/login_sign.js"></script>
</body>
</html>

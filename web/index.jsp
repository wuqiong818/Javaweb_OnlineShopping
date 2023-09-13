<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/7/28
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="http://localhost:8080/OnlineShopping/">
    <title>小米商城 - Xiaomi 12、Redmi K50、MIX FOLD，小米电视官方网站</title>
    <meta name="description" content="小米官网直营小米公司旗下所有产品，包括Xiaomi手机系列Xiaomi 12、MIX、Redmi 红米系列、
    Redmi Note 11、K50、Redmi Note、小米电视、笔记本、
    米家智能家居等，同时提供小米官方服务及售后支持.">
    <meta name="keywords" content="Xiaomi,redmi,Xiaomi12,Redmi Note 11,Xiaomi MIX Alpha,小米商城">
    <meta name="viewport" content="width=1226">
    <link rel="shortcut icon" href="/images/小米商城圆标.ico"/>
    <link id="style1" rel="stylesheet" href="CSS/index.css">
    <link id="style2" rel="stylesheet" href="CSS/base.css">
    <%--    <script src="JS/animation.js"></script>--%>
    <%--    <script src="JS/index.js"></script>--%>
</head>

<body>
<!-- 顶部快捷栏开始的，迈上小米官网的进程 -->
<div class="shortcut" id="top">
    <div class="w">
        <div class="register">
            <ul>
                <c:if test="${!empty sessionScope.ready_user}">
                    <li><a href="javascript:;">${ready_user.username}   </a>
                        <span class="spacer">
                        </span>
                    </li>
                </c:if>

                <%--安全退出功能--%>
                <c:if test="${!empty sessionScope.ready_user}">
                    <li><a href="${pageContext.request.contextPath}/safe/exit">安全退出</a>
                        <span class="spacer">
                        </span>
                    </li>
                </c:if>

                <c:if test="${empty sessionScope.ready_user}">
                    <li><a href="${pageContext.servletContext.contextPath}/login.jsp">登录${ready_user.username}</a>
                        <span class="spacer">|
                        </span>
                    </li>
                    <li><a href="${pageContext.servletContext.contextPath}/signUp.jsp">注册</a>
                        <span class="spacer">
                        </span>
                    </li>
                </c:if>

            </ul>
        </div>
    </div>
</div>
<!-- 顶部导航栏结束了 -->
<!-- 头部header开始了 -->
<header class="header">
    <div class="w">
        <div class="logo">
            <h1>
                <a href="index.jsp">小米商城</a>
            </h1>
        </div>
        <div class="theme">
            <h1>淘转二手商品交易市场</h1>
        </div>
        <%--<div class="header_nav">
            <ul class="dropdown">
                <li class="dropdown_titile"><a href="#">Xiaomi手机</a>
                    <div class="dropdown_container">
                        <ul class="drowdown_content">
                            <a href="">
                                <img src="upload/导航栏下拉边框图片.webp" alt="">
                                <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                                <p>2399元起</p>
                            </a>
                            <a href="">
                                <img src=" upload/导航栏下拉边框图片.webp" alt="">
                                <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                                <p>2399元起</p>
                            </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a>
                        </ul>
                    </div>
                </li>
                <li class="dropdown_titile"><a href="#">Redmi手机</a>
                    <div class="dropdown_container">
                        <ul class="drowdown_content">
                            <a href="">
                                <img src=" upload/导航栏下拉边框图片.webp" alt="">
                                <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                                <p>2399元起</p>
                            </a>
                            <a href="">
                                <img src=" upload/导航栏下拉边框图片.webp" alt="">
                                <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                                <p>2399元起</p>
                            </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a><a href="">
                            <img src=" upload/导航栏下拉边框图片.webp" alt="">
                            <h6>Xiaomi&nbsp;Civi&nbsp;2</h6>
                            <p>2399元起</p>
                        </a>
                        </ul>
                    </div>
                </li>
                <li><a href="#">电视</a></li>
                <li><a href="#">笔记本</a></li>
                <li><a href="#">平板</a></li>
                <li><a href="#">家电</a></li>
                <li><a href="#">路由器</a></li>
                <li><a href="#">服务中心</a></li>
                <li><a href="#">社区</a></li>
            </ul>
        </div>--%>
        <%--        搜索框--%>
        <%--        <div class="search">--%>
        <%--            <input type="search" placeholder="显示器" class="input_search">--%>
        <%--            <input type="submit" value="" class="input_sbumit">--%>
        <%--        </div>--%>
    </div>
</header>
<!-- 头部header结束了 -->
<!-- main页面要开始了 -->
<%--<section class="main">--%>
<%--    <div class="w">--%>
<%--        <div class="slideshow">--%>
<%--            <div class="slideshow_container">--%>
<%--                <li><a href="#">--%>
<%--                    <img src=" upload/米家wiha家用工具箱.webp" alt="">--%>
<%--                </a></li>--%>
<%--                <li><a href="#">--%>
<%--                    <img src=" upload/轮播图小米双十一焕新季.jpg" alt="">--%>
<%--                </a></li>--%>
<%--                <li><a href="#">--%>
<%--                    <img src=" upload/轮播图智能双控水暖毯.webp" alt="">--%>
<%--                </a></li>--%>
<%--                <li><a href="#">--%>
<%--                    <img src=" upload/轮播图小米双十一焕新季.jpg" alt="">--%>
<%--                </a></li>--%>
<%--            </div>--%>
<%--            <ol class="circle">--%>
<%--            </ol>--%>
<%--            <div class="key_LR">--%>
<%--                <li class="btL"></li>--%>
<%--                <li class="btR"></li>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="main_above">--%>
<%--            <div class="vertical_nav">--%>
<%--                <ul>--%>
<%--                    <li class="dp_title"><a href="#">手机</a>--%>
<%--                        <div class="dp_container">--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>

<%--                        </div>--%>

<%--                    </li>--%>
<%--                    <li class="dp_title"><a href="#">手机</a>--%>
<%--                        <div class="dp_container">--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>

<%--                        </div>--%>

<%--                    </li>--%>
<%--                    <li class="dp_title"><a href="#">手机</a>--%>
<%--                        <div class="dp_container">--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>
<%--                            <ul class="dp_content">--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                                <li><a href="#">--%>
<%--                                    <img src=" upload/小米12s垂直列表.webp" alt="">--%>
<%--                                    <span>Xiaomi&nbsp;12S&nbsp;Pro</span>--%>
<%--                                </a></li>--%>
<%--                            </ul>--%>

<%--                        </div>--%>

<%--                    </li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                    <li><a href="#">手机</a></li>--%>
<%--                </ul>--%>
<%--            </div>--%>

<%--        </div>--%>
<%--        <div class="main_down">--%>
<%--            <div class="service">--%>
<%--                <ul>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" images/小钟表透明图.png" alt="">--%>
<%--                        <p>保障服务</p>--%>
<%--                    </a>--%>
<%--                        <div class="spacer_left"></div>--%>
<%--                        <div class="spacer_top"></div>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </div>--%>
<%--            <div class="production">--%>
<%--                <ul>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" upload/redmiNote12.jpg" alt="">--%>
<%--                    </a></li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" upload/redmiNote12.jpg" alt="">--%>
<%--                    </a></li>--%>
<%--                    <li><a href="#">--%>
<%--                        <img src=" upload/redmiNote12.jpg" alt="">--%>
<%--                    </a></li>--%>

<%--                </ul>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</section>--%>
<!-- main页面要结束了 -->
<!-- ———————————————————————————————————————————————————— -->
<!-- 商品展示页面开始了 -->
<%--<div class="tab_container">--%>
<div class="exhibition">
    <div class="w">
        <div class="exhibition_header">
            <h2>二手商品</h2>
            <div class="tab_title">
                <%--                <div style="width: 80px;height: 20px ; color:#bfb828" id="tab_switch1">纸质资料</div>--%>
                <%--                <div style="width: 80px;height: 20px" id="tab_switch2">虚拟资料</div>--%>
            </div>
        </div>
        <ul class="tab_content">
            <li><a href="#"><img src=" upload/tab栏电视大图.webp" alt=""></a></li>
            <%-- 这里改成动态加载数据库中的数据 根据这一个格式进行展示，用户点击后可以进行到商品的详情页--%>
        </ul>
    </div>
</div>
<script>
    window.onload = function () {
        var xmlhttp;
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                var data = JSON.parse(xmlhttp.responseText);
                displayData(data);
            }
        }
        xmlhttp.open("GET", "${pageContext.request.contextPath}/display/merchandise", true);
        xmlhttp.send();
    }

    function details(id) {
        window.location.href = "${pageContext.request.contextPath}/detail/merchandise?merchandiseId=" + id;
    }

    function displayData(data) {
        var container = document.querySelector(".tab_content");
        var more = document.querySelector(".more");
        var count;
        if (data.length < 8) {
            count = data.length;
        } else {
            count = 8;
        }
        for (var i = 0; i < count; i++) {
            var item = data[i];
            container.insertAdjacentHTML(`beforeend`, `
                                                <li onclick="details(` + item.id + `)"><img src="user_upload/images/` + item.photoPathArray[0] + `" alt="">
                                                    <h3> ` + item.name + `&nbsp;</h3>
                                                    <p> ` + item.featureArray[0] + `&nbsp;|&nbsp;` + item.featureArray[1] + `</p>
                                                    <span>` + item.price + ` <del></del></span>
                                                </li>`);
        }

        container.insertAdjacentHTML(`beforeend`, `<li>
                <span class="more">
                    <a href="${pageContext.request.contextPath}/display/allMerchandise">浏览更多</a> <br>
                    <p>更多商品</p>
                </span>
        </li>`);
    }
</script>
<!-- /* +++++++++++++++++++++++++++++++++++++++++++++++ */
/* +++++++++++++++++++++++++++++++++++++++++++++++ */
/* footer部分开始了 */ -->
<%--<footer class="footer">
    <div class="w">
        <div class="serive">
            <div class="serive_title">
                <ul>
                    <li><a href="#">预约维修服务</a></li>
                    <li><a href="#">预约维修服务</a></li>
                    <li><a href="#">预约维修服务</a></li>
                    <li><a href="#">预约维修服务</a></li>
                    <li><a href="#">预约维修服务</a></li>
                </ul>
            </div>
            <div class="serive_detail">
                <dl>
                    <dt>选购指南</dt>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                </dl>
                <dl>
                    <dt>选购指南</dt>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                </dl>
                <dl>
                    <dt>选购指南</dt>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                </dl>
                <dl>
                    <dt>选购指南</dt>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                </dl>
                <dl>
                    <dt>选购指南</dt>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                    <dd><a href="#">手机</a></dd>
                </dl>
                <dl>
                    <h1>400-100-5678</h1>
                    <p>8:00-18:00&nbsp;&nbsp;&nbsp;(仅收市话费)</p>
                    <button>人工客服</button>
                </dl>
            </div>
        </div>
    </div>
    <div class="about">
        <div class="w">
            <div class="about_top">
                <div class="about_logo">
                    <img src=" images/logo-mi2.png" alt="">
                </div>
                <div class="about_text">
                    <p>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">小米商城</a><span class="spacer"></span>
                    </p>
                    <p>
                        <a href=" index.html" target="_blank">北京互联网法院法律服务工作站</a><span
                            class="spacer">|</span>
                        <a href=" index.html" target="_blank">中国消费者协会</a><span class="spacer">|</span>
                        <a href=" index.html" target="_blank">北京市消费者协会</a><span class="spacer"></span>
                    </p>
                    <p>
                        © mi.com 京ICP证110507号 京ICP备10046444号 京公网安备11010802020134号 京网文[2020]0276-042号<br>
                        （京）网械平台备字（2018）第00005号 互联网药品信息服务资格证 (京)-非经营性-2014-0090 营业执照
                        医疗器械质量公告<br>
                        增值电信业务许可证 网络食品经营备案 京食药网食备202010048 食品经营许可证<br>
                        违法和不良信息举报电话：171-5104-4404 知识产权侵权投诉 本网站所列数据，除特殊说明，所有数据均出自我司实验室测试
                    </p>
                </div>
                <div class="certification">
                    <img src=" images/诚信认证.png" alt="">
                    <img src=" images/诚信认证.png" alt="">
                    <img src=" images/诚信认证.png" alt="">
                    <img src=" images/诚信认证.png" alt="">
                    <img src=" images/诚信认证.png" alt="">
                </div>
            </div>
            <div class="about_buttom">
                <div></div>
            </div>
        </div>
    </div>
</footer>--%>
<!-- ---------------------------------------------------------- -->
<!-- 右侧悬浮菜单栏 -->
<div class="menu_items">
    <li><a href="${pageContext.request.contextPath}/uploading.jsp">上传商品</a></li>
    <li><a href="${pageContext.request.contextPath}/personalHomepage.jsp">个人中心</a></li>
<%--    <li><a href="#">人工客服</a></li>--%>
    <li><a href="${pageContext.request.contextPath}/shoppingCart.jsp">购物车</a></li>
    <li class="goBack"><a href="script:;">回到顶部</a></li>
</div>


</body>
</html>
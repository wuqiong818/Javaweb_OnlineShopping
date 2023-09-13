<%--
  Created by IntelliJ IDEA.
  User: wang'ye
  Date: 2023/8/19
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <meta charset="UTF-8"/>
    <title>确定订单信息</title>
    <link rel="shortcut icon" href="//s01.mifile.cn/favicon.ico" type="image/x-icon"/>
    <link rel="icon" href="//s01.mifile.cn/favicon.ico" type="image/x-icon"/>
    <link href="CSS/order.css" rel="stylesheet">
    <link href="CSS/base.css" rel="stylesheet">
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
                        确认订单
                    </h2>
                    <p>

                    </p>
                </div>

                <div class="site-topbar ">
                    <div class="topbar-info" id="J_siteUserInfo">
                        <a rel="nofollow" class="link" href="//order.mi.com/site/login" data-agreement="true"
                           data-login="true"></a>
                        <span class="sep"></span>
                        <a rel="nofollow" class="link" href="//account.xiaomi.com/pass/register?sid=mi_eshop"
                           data-agreement="true"
                           data-register="true"></a>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="mi-checkout page-main">
        <div class="container">
            <checkout-detail>
                <h1>收货人信息</h1>
                <hr>
                <form method="post"
                      class="takeDeliveryInfo">
                    <label for="receive_name">接收人姓名:</label>
                    <input type="text" id="receive_name" name="receive_name" required>
                    <br>
                    <label for="telephone">手机号:</label>
                    <input type="text" id="telephone" name="telephone" required>
                    <br>
                    <label for="address">收获地址:</label>
                    <input type="text" id="address" name="address" style="width: 300px" required>
                    <br>
                    <label for="notes">备注:</label>
                    <input type="text" id="notes" name="notes" style="width: 300px">
                    <br>
                    <button type="submit" id="submitBtn" style="display: none">Submit</button>
                </form>
                <h1>商品信息</h1>
                <hr>
                <div class="cart_title">
                    <li class="img_title">图片</li>
                    <li class="name"><p>商品名称</p></li>
                    <li class="price">单价</li>
                    <li class="amount">数量</li>
                    <li class="count">小计</li>
                </div>
                <div class="replace">
                </div>
                <div class="purchase-bar clearfix">
                </div>
            </checkout-detail>
        </div>
    </div>
    <script>
        var orderForm = document.querySelector(".takeDeliveryInfo");
        window.addEventListener("load", function () {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    //返回JSON类型的数据，里面封装了顾客的信息，我们需要去用户名 手机号和地址。
                    var responseText = xhr.responseText;//字符形式的null;"null"
                    if (responseText !== "null") {
                        var jsonUserInfo = JSON.parse(responseText);
                        var name = document.getElementById("receive_name");
                        var telephone = document.getElementById("telephone");
                        var address = document.getElementById("address");

                        console.log(jsonUserInfo.telephone);
                        console.log(typeof jsonUserInfo.telephone);

                        var telephoneValue = jsonUserInfo.telephone;
                        var addressValue = jsonUserInfo.address;
                        if (addressValue === undefined) {
                            addressValue = "";
                        }
                        if (telephoneValue === undefined) {
                            telephoneValue = "";
                        }
                        name.value = jsonUserInfo.username;
                        telephone.value = telephoneValue;
                        address.value = addressValue;
                    } else {
                        window.location.href = "${pageContext.request.contextPath}/skipToLogin.jsp";
                    }
                }
            }
            xhr.open("GET", "${pageContext.request.contextPath}/order/receiverInfo", true)
            xhr.send();
        });


        window.addEventListener("load", function () {
            var currentURL = window.location.href;
            var url = new URL(currentURL);
            var merchandiseId = url.searchParams.get("merchandiseId");
            var purchaseAmount = url.searchParams.get("purchaseAmount");

            //通过AJAX,向后端发送请求，获取已经勾选，将要购买的商品信息;
            var merchandiseItem = document.querySelector(".replace");
            var purchase_bar = document.querySelector(".purchase-bar")
            var allTotal = 0;
            var xhr1 = new XMLHttpRequest();
            xhr1.onreadystatechange = function () {
                if (xhr1.readyState == 4 && xhr1.status == 200) {
                    var jsonMerchandise = JSON.parse(xhr1.responseText);
                    jsonMerchandise.forEach((item) => {
                        allTotal += item.price * item.amount;
                        merchandiseItem.insertAdjacentHTML(`beforeend`, `
                        <div class="cart_item cart_title">
                            <li class="itemImg"><img alt="加载错误"
                                                 src="${pageContext.request.contextPath}/user_upload/images/` + item.photoPathArray[0] + `">
                            </li>
                            <li class="name"><p>` + item.name + `</p></li>
                            <li class="price">` + item.price + `</li>
                            <li class="amount_count">` + item.amount + `件</li>
                            <li class="item_count">` + (item.price * item.amount).toFixed(2) + `元</li>
                            </div>
                        `);
                    })
                    purchase_bar.insertAdjacentHTML(`beforeend`, `
                        <li><a href="${pageContext.request.contextPath}/shoppingCart.jsp">返回上一级</a></li>
                        <li class="selectedTotal">共<p>` + jsonMerchandise.length + `</p>件</li>
                        <li class="totalCostDom">合计<p>` + allTotal.toFixed(2) + `</p>元</li>
                        <li class="purchase">去付款</li>
                    `);
                }
            }
            if (merchandiseId !== null) {
                xhr1.open("GET", "${pageContext.request.contextPath}/order/merchandiseInfo?merchandiseId=" + merchandiseId + "&purchaseAmount=" + purchaseAmount, false);
                xhr1.send();
            } else {
                xhr1.open("GET", "${pageContext.request.contextPath}/order/merchandiseInfo", false);
                xhr1.send();
            }
            then();

            function then() {
                var purchase = document.querySelector(".purchase");
                purchase.addEventListener("click", function () {
                    //向后端传数据呀,把订单信息存到购物车里面
                    const form = document.querySelector(".takeDeliveryInfo");
                    var formData = new FormData(form);
                    /*
                    * 问题：form表达中的数据不全，稍微解决一下就可以了，append()方法进行数据的增加;
                    * */
                    formData.append("merchandiseId", merchandiseId);
                    formData.append("purchaseAmount", purchaseAmount);
                    console.log("formData=" + formData);
                    //对form表单中的数据进行检验，控制空值;
                    var receive_name = formData.get("receive_name");
                    var telephone = formData.get("telephone");
                    var address = formData.get("address");
                    console.log(receive_name)
                    console.log(telephone)
                    console.log(address)
                    console.log(typeof address);

                    if (receive_name === "" || telephone === "" || address === "") {
                        alert("请完善信息");
                    } else {
                        var xhr = new XMLHttpRequest();
                        xhr.onreadystatechange = function () {
                            if (xhr.status === 200 && xhr.readyState === 4) {
                                window.location.href = "${pageContext.request.contextPath}/skipToIndex.jsp";
                            }
                        }
                        xhr.open("POST", "${pageContext.request.contextPath}/order/generate", true);
                        xhr.send(formData);

                        /*
                        * 这里有一个东西很蹊跷，自己没有搞明白
                        * 使用formData模拟form表单是不需要自己设置请求头，
                        * 会自动进行转化成二进制流的形式了,view Source和view parsed可以来回进行切换，这才是正确的形式;
                        *
                        * */
                    }
                })
            }
        });


    </script>
    <div ref="J_siteFooter">
        <div class="site-footer">
            <div class="container">
                <div class="footer-service">
                    <ul class="list-service clearfix">
                        <li><a rel="nofollow" href="//www.mi.com/service/aftersale/front" target="_blank"><em
                                class="iconfont-tool"></em>预约维修服务</a></li>
                        <li><a rel="nofollow" href="//www.mi.com/service/exchange#back" target="_blank"><em
                                class="iconfont-circle-7"></em>7天无理由退货</a></li>
                        <li><a rel="nofollow" href="//www.mi.com/service/exchange#back" target="_blank"><em
                                class="iconfont-circle-15"></em>15天免费换货</a></li>
                        <li><a rel="nofollow" href="//www.mi.com/service/buy/Postal%20policy" target="_blank"><em
                                class="iconfont-gift"></em><span id="J_footerPrice">满69元包邮</span></a></li>
                        <li><a rel="nofollow" href="//www.mi.com/service/sitelist" target="_blank"><em
                                class="iconfont-location"></em>1100余家售后网点</a></li>
                    </ul>
                </div>
                <div class="footer-links clearfix">
                    <dl class="col-links col-links-first">
                        <dt></dt>
                    </dl>
                    <dl class="col-links ">
                        <dt>选购指南</dt>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E6%89%8B%E6%9C%BA"
                               target="_blank">手机</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E7%94%B5%E8%A7%86"
                               target="_blank">电视</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E7%AC%94%E8%AE%B0%E6%9C%AC"
                               target="_blank">笔记本</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E5%B9%B3%E6%9D%BF"
                               target="_blank">平板</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E6%99%BA%E8%83%BD%E7%A9%BF%E6%88%B4"
                               target="_blank">穿戴</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E8%80%B3%E6%9C%BA"
                               target="_blank">耳机</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E5%AE%B6%E7%94%B5"
                               target="_blank">家电</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E8%B7%AF%E7%94%B1%E5%99%A8"
                               target="_blank">路由器</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E9%9F%B3%E7%AE%B1"
                               target="_blank">音箱</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/search?keyword=%E9%85%8D%E4%BB%B6"
                               target="_blank">配件</a></dd>
                    </dl>

                    <dl class="col-links ">
                        <dt>服务中心</dt>
                        <dd><a rel="nofollow" href="//www.mi.com/service/aftersale/front" target="_blank">申请售后</a>
                        </dd>
                        <dd><a rel="nofollow" href="//www.mi.com/service/exchange#phone" target="_blank">售后政策</a>
                        </dd>
                        <dd><a rel="nofollow" href="//www.mi.com/service/materialprice" target="_blank">维修服务价格</a>
                        </dd>
                        <dd><a rel="nofollow" href="//www.mi.com/user/orderList" target="_blank">订单查询</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/a/h/16769.html" target="_blank">以旧换新</a></dd>
                        <dd><a rel="nofollow" href="//api.jr.mi.com/activity/scene/scenePCsearch.html?from=search"
                               target="_blank">保障服务</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/service/imei" target="_blank">防伪查询</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/order/fcode" target="_blank">F码通道</a></dd>
                    </dl>

                    <dl class="col-links ">
                        <dt>线下门店</dt>
                        <dd><a rel="nofollow" href="//www.mi.com/service/mihome/list" target="_blank">小米之家</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/service/sitelist" target="_blank">服务网点</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/service/family-location"
                               target="_blank">授权体验店/专区</a></dd>
                    </dl>

                    <dl class="col-links ">
                        <dt>关于小米</dt>
                        <dd><a rel="nofollow" href="//www.mi.com/about/" target="_blank">了解小米</a></dd>
                        <dd><a rel="nofollow" href="//hr.xiaomi.com/" target="_blank">加入小米</a></dd>
                        <dd><a rel="nofollow" href="//ir.mi.com/zh-hans" target="_blank">投资者关系</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/csr" target="_blank">可持续发展</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/integrity" target="_blank">廉洁举报</a></dd>
                    </dl>

                    <dl class="col-links ">
                        <dt>关注我们</dt>
                        <dd><a rel="nofollow" href="//weibo.com/xiaomishangcheng" target="_blank">新浪微博</a></dd>
                        <dd><a rel="nofollow" href="javascript:void(0);" id="J_siteWeixinCode">官方微信</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/about/contact/" target="_blank">联系我们</a></dd>
                        <dd><a rel="nofollow" href="//www.mi.com/foundation/index" target="_blank">公益基金会</a></dd>
                    </dl>

                    <div class="col-contact">
                        <p class="phone">400-100-5678</p>
                        <p>8:00-18:00（仅收市话费）</p>
                        <a rel="nofollow" class="btn btn-line-primary btn-small J_contactBtn"> <em
                                class="iconfont-message2"></em> 人工客服 </a>
                    </div>
                </div>
            </div>
        </div>


        <div class="site-info">
            <div class="container">
                <div class="logo ir">小米官网</div>
                <div class="info-text">
                    <p class="sites">
                        <a rel="nofollow" href="//www.mi.com/shop" target="_blank">小米商城</a>
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//www.miui.com/" target="_blank">MIUI</a>
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//home.mi.com/index.html" target="_blank"
                        >米家</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="http://www.miliao.com/" target="_blank">米聊</a>
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//www.duokan.com/" target="_blank">多看</a>
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//game.xiaomi.com/" target="_blank">游戏</a>
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//www.mi.com/music" target="_blank">音乐</a>
                        <span class="sep">|</span>
                        <a
                                rel="nofollow"
                                href="//b.mi.com/?client_id=180100031058&masid=17409.0358"
                                target="_blank"
                        >政企服务</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//xiaomi.tmall.com/" target="_blank"
                        >小米天猫店</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//privacy.mi.com/all/zh_CN/" target="_blank"
                        >小米集团隐私政策</a
                        >
                        <span class="sep">|</span>
                        <a
                                rel="nofollow"
                                href="//cdn.cnbj1.fds.api.mi-img.com/mi-mall/f516fe9e2c01.html"
                                target="_blank"
                        >小米公司儿童信息保护规则</a
                        >
                        <span class="sep">|</span>
                        <a
                                rel="nofollow"
                                href="//m.mi.com/support/module?id=63&headless=1"
                                target="_blank"
                        >小米商城隐私政策</a
                        >
                        <span class="sep">|</span>
                        <a
                                rel="nofollow"
                                href="//www.mi.com/aptitude/list?id=62"
                                target="_blank"
                        >小米商城用户协议</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="//static.mi.com/feedback/" target="_blank"
                        >问题反馈</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="javascript:void(0);" class="J_siteGlobalRegion"
                        >Select Location</a
                        >
                    </p>
                    <p class="sites">
                        <a rel="nofollow" href="http://www.mi.com/beihu" target="_blank"
                        >北京互联网法院法律服务工作站</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="http://www.cca.org.cn/" target="_blank"
                        >中国消费者协会</a
                        >
                        <span class="sep">|</span>
                        <a rel="nofollow" href="http://www.bj315.org/" target="_blank"
                        >北京市消费者协会</a
                        >
                    </p>
                    <p>
                        &copy;
                        <a href="javascript:;" title="mi.com">mi.com</a> 京ICP证110507号
                        <a href="http://beian.miit.gov.cn/" target="_blank" rel="nofollow"
                        >京ICP备10046444号</a
                        >
                        <a
                                rel="nofollow"
                                href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010802020134"
                                target="_blank"
                        >京公网安备11010802020134号</a
                        >
                        <a href="//www.mi.com/culture-license/" target="_blank"
                        >京网文[2023]0169-004号</a
                        >
                        <br/>
                        <a href="//www.mi.com/medical/record/" target="_blank"
                        >（京）网械平台备字（2018）第00005号</a
                        >
                        <a href="//www.mi.com/medical/qualification/" target="_blank"
                        >互联网药品信息服务资格证 (京)-非经营性-2014-0090</a
                        >
                        <a href="//www.mi.com/business-license/" target="_blank">营业执照</a>
                        <a href="//www.mi.com/medical/list/" target="_blank"
                        >医疗器械质量公告</a
                        >
                        <br/>
                        <a
                                href="https://cdn.cnbj1.fds.api.mi-img.com/staticsfile/pc/5f93056c-bc30-401e-9526-66839114ffb9.png"
                                target="_blank"
                        >增值电信业务许可证</a
                        >&nbsp;网络食品经营备案 京食药网食备202010048 &nbsp;
                        <a
                                href="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/c6859168166651511897f54fa1047fe3.png"
                                target="_blank"
                        >食品经营许可证</a
                        >
                        <br/>
                        违法和不良信息举报电话：171-5104-4404&nbsp;<a
                            href="https://www.mi.com/intellectual"
                            target="_blank"
                    >知识产权侵权投诉</a
                    >&nbsp;本网站所列数据，除特殊说明，所有数据均出自我司实验室测试
                    </p>
                </div>
                <div class="info-links">
                    <a
                            rel="nofollow"
                            href="//privacy.truste.com/privacy-seal/validation?rid=4fc28a8c-6822-4980-9c4b-9fdc69b94eb8&lang=zh-cn"
                            target="_blank"
                    >
                        <img
                                rel="nofollow"
                                src="//i1.mifile.cn/f/i/17/site/truste.png"
                                alt="TRUSTe Privacy Certification"
                        />
                    </a>
                    <a
                            rel="nofollow"
                            href="//search.szfw.org/cert/l/CX20120926001783002010"
                            target="_blank"
                    >
                        <img
                                rel="nofollow"
                                src="//s01.mifile.cn/i/v-logo-2.png"
                                alt="诚信网站"
                        />
                    </a>
                    <a
                            id="_xinchacharenzheng_cert_vip_kexinweb"
                            rel="nofollow"
                            href="//xyt.xinchacha.com/pcinfo?sn=523009637759455232&certType=6"
                            target="_blank"
                    >
                        <img
                                rel="nofollow"
                                src="//cdn.cnbj1.fds.api.mi-img.com/mi-mall/icon3.png"
                                alt="可信网站"
                        />
                    </a>
                    <a
                            rel="nofollow"
                            href="//www.mi.com/service/buy/commitment/"
                            target="_blank"
                    >
                        <img
                                rel="nofollow"
                                src="//i8.mifile.cn/b2c-mimall-media/ba10428a4f9495ac310fd0b5e0cf8370.png"
                                alt="诚信经营 放心消费"
                        />
                    </a>
                </div>
            </div>
            <div class="slogan ir">探索黑科技，小米为发烧而生</div>
        </div>
    </div>
</div>
</body>
</html>

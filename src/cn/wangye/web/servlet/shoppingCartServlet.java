package cn.wangye.web.servlet;


import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.User;
import cn.wangye.service.MerchandiseService;
import cn.wangye.service.ShoppingCartService;
import cn.wangye.service.impl.MerchandiseServiceImpl;
import cn.wangye.service.impl.ShoppingCartServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/add/shoppingCart", "/display/shoppingCart", "/updateAmount/shoppingCart", "/delete/shoppingCart", "/purchase/shoppingCart", "/updateStatus/shoppingCart"})
public class shoppingCartServlet extends HttpServlet {
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();

    MerchandiseService merchandiseService = new MerchandiseServiceImpl();
    List<Merchandise> listCookie = new ArrayList<>();//用户未登录状态下，进行id的存储

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");
        String servletPath = request.getServletPath();
        //获取Cookie
        listCookie = transform(request, response);

        if (servletPath.equals("/add/shoppingCart")) {
            addMerchandise(request, response);
        } else if (servletPath.equals("/display/shoppingCart")) {
            displayMerchandise(request, response);
        } else if (servletPath.equals("/updateAmount/shoppingCart")) {
            updateAmount(request, response);
        } else if (servletPath.equals("/delete/shoppingCart")) {
            deleteMerchandise(request, response);
        } else if (servletPath.equals("/purchase/shoppingCart")) {
            doPurchaseShoppingCart(request, response);
        } else if (servletPath.equals("/updateStatus/shoppingCart")) {
            updateStatus(request, response);
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));
        shoppingCartService.updateStatus(id, status);
    }

    private void doPurchaseShoppingCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("ready_user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/skipToLogin.jsp");
//            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/order");
        }
    }


    private void deleteMerchandise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("ready_user");
        int id = Integer.parseInt(request.getParameter("id"));
        if (user != null) {
            int count = shoppingCartService.deleteShoppingCartMerchandiseById(id);
        } else {
            /*这里就是用户还没有登录，用户没有登录，进行增删改查，用的是使用Cookie和List<Merchandise>组成的一个临时变量
             *实际上代替了临时对数据库的操控*/
            for (int i = 0; i < listCookie.size(); i++) {
                if (id == listCookie.get(i).getId()) {
                    listCookie.remove(i);//这里可以删除成功，目前关键的是数据没有同步，原因：没有更新Cookie
                    break;
                }
            }
            updateCookie(request, response, listCookie);
        }
    }

    private void updateAmount(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //在这里结构前端发送过来的AJAX请求，进行数据中购物车商品表商品数量的修改
        User user = (User) request.getSession().getAttribute("ready_user");
        int id = Integer.parseInt(request.getParameter("id"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        //用户未登录状态下，对购物车的数量进行更改;
        if (user != null) {
            //一个是商品id,一个是前端已经更改的数量，通过前端发送的请求，更改数据库中购物车商品的数量;
            shoppingCartService.changeAmount(id, amount);
        } else {
            //直接根据merchandiseId更改对应商品的数量;
            for (Merchandise item : listCookie) {
                int merchandiseId = item.getId();
                if (id == merchandiseId) {
                    item.setAmount(amount);
                }
            }
            //进行Cookie中数据的更新;
            updateCookie(request, response, listCookie);
        }
    }

    private void displayMerchandise(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        /* 小米商城Cookie存储商品实现逻辑:
        cookie保存的时间为一周，一旦用户完成登录，就读取cookie,并向数据库中进行存储。
        1. 如果用户未登录，创建cookie,将用户添加的商品以List集合的形式，转为JSON的形式存储下来.List<Merchandise>
        2. 未登录状态下商品id的展示，通过读取Cookie中的id和amount,向后端查询数据，和正常一样返回即可；
        3. 未登录如果用户取消购物车的商品，直接删除对应的Cookie，只有一个Cookie,后来删除的话，删除list集合中的数据即可
        用户登录了:
        1. 如果用户登录了，就轮流读取JSON中的id,并且向数据库进行商品id的插入，完成持久化存储。
        2. 如果用户登录了，查看购物车获取的是通过id数据库查询的数据
        3. 删除是对数据库中的数据进行的删除*/
        User user = (User) request.getSession().getAttribute("ready_user");
        List<Merchandise> merchandiseList = new ArrayList<>();
        if (user == null) {//未登录
            merchandiseList = merchandiseService.selectCookieById_Amount(listCookie);
            //现在这里面的数量，是购物车里面的数量，并不是数据库中某一个商品的数量;
        } else {//已经登录了
            //这里用户登陆有两种情况，一种是用户之前没有登陆，等到购买的时候才进行登陆，这里需要读取Cookie中的数据，向购物车中进行数据的添加;
            int userId = user.getId();
            //先查看是否存在Cookie,如果有的话,就先读取添加;
            if (listCookie != null) {//listCookie = []
                for (int i = 0; i < listCookie.size(); i++) {
                    int merchandiseId = listCookie.get(i).getId();
                    int amount = listCookie.get(i).getAmount();
                    //当用户第一次向购物车中添加商品的时候，根据用户的id,创建购物车的id
                    shoppingCartService.addToShoppingCart(merchandiseId, userId, amount);
                }
                for (int i = listCookie.size() - 1; i >= 0; i--) {
                    listCookie.remove(i);
                }
                updateCookie(request, response, listCookie);
            }
            //这里的id,封账的是商品购物车表中的id
            merchandiseList = shoppingCartService.displayShoppingCartByUserId(userId);
        }
        //这里不是存到request域中了，而是要封装成JSON字符串
        String jsonMerchandiseList = JSON.toJSONString(merchandiseList);
        // 设置相应是json格式
        response.setContentType("application/json;charset=UTF-8");//设置响应的格式
        // 将JSON数据相应到前端中；
        PrintWriter out = response.getWriter();
        out.print(jsonMerchandiseList);
        out.flush();
    }

    private void addMerchandise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //在这里进行List集合的初始化
        Cookie[] initializeCookies = request.getCookies();
        if (initializeCookies != null) {
            for (Cookie cookie : initializeCookies) {
                if (cookie.getName().equals("listCookieJson")) {
                    //获取JSON
                    String initializeEncodedListCookieJson = cookie.getValue();
                    // 解码 Cookie 中的 JSON 字符串
                    String decodedListCookieJson = URLDecoder.decode(initializeEncodedListCookieJson, "UTF-8");
                    // 将 JSON 字符串转化为 List 集合
                    listCookie = JSON.parseArray(decodedListCookieJson, Merchandise.class);
                    break;
                }
            }
        }
        //先不考虑运行内存问题，我们直接用merchandise对象进行存储
        //目前还是有一个问题，一旦我们的项目重新启动了，那么Cookie就会重置，只会进行一次重启是的更新
        //原因:listCookie的新建,因为我们的新Cookie是有listCookie转化过来的，所以listCookie重置，Cookie也就会重置。
        User user = (User) request.getSession().getAttribute("ready_user");
        boolean condition = true;
        if (user == null) {
            int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
            for (int i = 0; i < listCookie.size(); i++) {
                if (listCookie.get(i).getId() == merchandiseId) {
                    listCookie.get(i).setAmount(listCookie.get(i).getAmount() + 1);
                    condition = false;
                    break;
                }
            }
            if (condition) {//当添加的商品购物车中未存在时，就新建一个Merchandise对象，向List集合中进行添加
                Merchandise merchandise = new Merchandise();
                merchandise.setId(merchandiseId);
                merchandise.setAmount(1);
                listCookie.add(merchandise);
            }
            //在这里将List<Merchandise>转化为Cookie,保持Cookie的更新,先删除之前的Cookie,在创建新的Cookie
            updateCookie(request, response, listCookie);
            response.sendRedirect(request.getContextPath() + "/success.jsp");
        } else {
            int userId = user.getId();
            int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
            //当用户第一次向购物车中添加商品的时候，根据用户的id,创建购物车的id
            boolean flag = shoppingCartService.addToShoppingCart(merchandiseId, userId, 1);
            if (flag) {
                response.sendRedirect(request.getContextPath() + "/success.jsp");
            }
        }
    }

    private List<Merchandise> transform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //这里将前端存储购物车商品信息的Cookie转化List<merchandise>;
        List<Merchandise> temp = null;
        boolean flag = true;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("listCookieJson")) {
                    flag = false;
                    //获取JSON
                    String initializeEncodedListCookieJson = cookie.getValue();
                    // 解码 Cookie 中的 JSON 字符串
                    String decodedListCookieJson = URLDecoder.decode(initializeEncodedListCookieJson, "UTF-8");
                    // 将 JSON 字符串转化为 List 集合
                    temp = JSON.parseArray(decodedListCookieJson, Merchandise.class);//每一次查询，更新后端的list集合。
                    break;
                }
            }
        }
        if (flag) {//这个是冗余的，如果用户因自己的操作，删除了Cookie,那么这里还会再次创建一个，只不过会报错；
            List<Merchandise> merchandiseList = new ArrayList<>();
            String listCookieJson = JSON.toJSONString(merchandiseList);
            String encodedListCookieJson = URLEncoder.encode(listCookieJson, "UTF-8"); // 进行编码
            Cookie cookie = new Cookie("listCookieJson", encodedListCookieJson);
            cookie.setMaxAge(60 * 60 * 24 * 10);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
        return temp;
    }

    private void updateCookie(HttpServletRequest request, HttpServletResponse response, List<Merchandise> listCookie) throws IOException {
        //在这里将List<Merchandise>转化为Cookie,保持Cookie的更新,先删除之前的Cookie,在创建新的Cookie
        Cookie[] oldCookies = request.getCookies();
        if (oldCookies != null) {
            for (Cookie cookie : oldCookies) {
                if (cookie.getName().equals("listCookieJson")) {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        //在这里进行Cookie创建和更新
        String listCookieJson = JSON.toJSONString(listCookie);
        String encodedListCookieJson = URLEncoder.encode(listCookieJson, "UTF-8"); // 进行编码
        Cookie cookie = new Cookie("listCookieJson", encodedListCookieJson);
        cookie.setMaxAge(60 * 60 * 24 * 10);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

}
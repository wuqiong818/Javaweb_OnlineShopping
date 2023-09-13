package cn.wangye.web.servlet;

import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.User;
import cn.wangye.service.UserService;
import cn.wangye.service.impl.UserServiceImpl;
import cn.wangye.utils.encryption;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/welcome"})
public class welcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
/*        //不管用户登陆没有登陆，都先创建一共Cookie
        List<Merchandise> merchandiseList = new ArrayList<>();
        String listCookieJson = JSON.toJSONString(merchandiseList);
        String encodedListCookieJson = URLEncoder.encode(listCookeJson, "UTF-8"); // 进行编码
        Cookie cookie3 = new Cookie("listCookieJson", encodedListCiookieJson);
        cookie3.setMaxAge(60 * 60 * 24 * 10);
        cookie3.setPath(request.getContextPath());
        response.addCookie(cookie3);*/
        /*在这里进行一个分流操作
         * 如果有cookie,并且Cookie正确,就直接现实登录的状态
         * 如果没有Cookie,或者cookie错误,就让用户自己登录;
         * */
        UserService userService = new UserServiceImpl();
        Cookie[] cookies = request.getCookies();
        boolean flag = true;
        if (cookies != null) {
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                } else if (cookie.getName().equals("listCookieJson")) {
                    flag = false;
                    //获取JSON
                }
            }
            //由于没有查询到名为listCookieJson的Cookie,在这里进行创建;
            if (flag) {
                List<Merchandise> merchandiseList = new ArrayList<>();
                String listCookieJson = JSON.toJSONString(merchandiseList);
                String encodedListCookieJson = URLEncoder.encode(listCookieJson, "UTF-8"); // 进行编码
                Cookie cookie = new Cookie("listCookieJson", encodedListCookieJson);
                cookie.setMaxAge(60 * 60 * 24 * 10);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }

            Integer identity = 0;//普通用户
            if (username != null && password != null) {
                String encryptedPassword = encryption.stringMD5(password);//这里的password获取不到;
                User user = userService.login(username, encryptedPassword, identity);
                if (user != null) {//密码正确
                    request.getSession().setAttribute("ready_user", user);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");//这个是让前端跳转的，需要带上项目名字
                } else {//现有的密码和Cookie中保存的密码不一致，登录失败，未登录状态
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            }else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            };
        } else {//没有找到对应的Cookie，登录失败，未登录状态;
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}

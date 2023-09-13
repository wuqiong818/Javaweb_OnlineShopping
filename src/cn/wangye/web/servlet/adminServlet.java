package cn.wangye.web.servlet;

import cn.wangye.pojo.User;
import cn.wangye.service.UserService;
import cn.wangye.service.impl.UserServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@WebServlet({"/admin/check", "/generate/verificationCode", "/get/usersInfo"})
public class adminServlet extends HttpServlet {
    private String verificationCode = "";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/generate/verificationCode")) {
            generateVerificationCode(request, response);
        } else if (servletPath.equals("/admin/check")) {
            adminCheck(request, response);
        } else if (servletPath.equals("/get/usersInfo")) {
            getAllUserInfo(request, response);
        }
    }

    private void getAllUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = new UserServiceImpl();
        List<User> usersList = userService.getAllUserInfo();
        String jsonList = JSON.toJSONString(usersList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonList);
        out.flush();
    }

    private void adminCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");
        String inputVerificationCode = request.getParameter("verificationCode");
        if (inputVerificationCode.equals(verificationCode)) {
            UserService userService = new UserServiceImpl();
            Integer identity = 1;
            User admin = userService.login(adminName, password, identity);
            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("ready_admin", admin);
                //登录成功，将资源直接跳转到管理员的主页面
                response.sendRedirect(request.getContextPath() + "/adminIndex.html");
            } else {
                //向前台发送密码错误;
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("<h1>密码错误!重新输入</h1>");
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("<h1>验证码错误!重新输入</h1>");
        }
    }

    private void generateVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        //这里自己写一个随机数和大小写字母的拼接;
//        随机出来数字,大小写字母的任意组合;
        char[] candidate = new char[62];
        for (int i = 48; i < 58; i++) {
            candidate[i - 48] = (char) i;
        }
        for (int i = 65; i < 91; i++) {
            candidate[i - 65 + 10] = (char) i;
        }
        for (int i = 97; i < 123; i++) {
            candidate[i - 97 + 36] = (char) i;
        }
        String str = "";
//        这里产生六个随机数，用领导教的那个方法试试
        Random randomNum = new Random();
        for (int i = 0; i < 6; i++) {
            int num = randomNum.nextInt(candidate.length + 1);
            str += candidate[num];
        }
        verificationCode = str;
        System.out.println("后台验证码" + verificationCode);
    }
}
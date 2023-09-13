package cn.wangye.web.servlet;

import cn.wangye.pojo.User;
import cn.wangye.service.UserService;
import cn.wangye.service.impl.UserServiceImpl;
import cn.wangye.utils.encryption;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet({"/user/login", "/user/signUp", "/user/exit", "/safe/exit", "/check/username", "/update/information", "/display/information", "/change/userStatus"})
public class userServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        request.setCharacterEncoding("UTF-8");//可以在前端处理也可以在后端处理，但是注意不能设置为text/UTF-8;
        if (servletPath.equals("/user/signUp")) {//注册功能
            doSignUp(request, response);
        } else if (servletPath.equals("/user/login")) {//登录功能
            doLogin(request, response);
        } else if (servletPath.equals("/check/username")) {//
            doCheck(request, response);
        } else if (servletPath.equals("/update/information")) {//用户信息的修改
            try {
                updateInfo(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (servletPath.equals("/display/information")) {
            displayInfo(request, response);
        } else if (servletPath.equals("/user/exit")) {
            exit(request, response);
        } else if (servletPath.equals("/safe/exit")) {
            safeWithdrawing(request, response);
        } else if (servletPath.equals("/change/userStatus")) {
            changeStatus(request, response);
        }
    }

    private void changeStatus(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        Integer userStatus = Integer.valueOf(request.getParameter("userStatus"));
        User user = userService.selectById(userId);
        user.setUserStatus(userStatus);
        userService.updateById(user);
    }

    private void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("ready_user");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    private void safeWithdrawing(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("ready_user");
            session.invalidate();
            System.out.println("session销毁了");
            //将cookie进行销毁操作
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    if ("username".equals(name) || "password".equals(name)) {
                        System.out.println(name);
                        cookie.setMaxAge(0);
                        cookie.setPath(request.getContextPath());//路径一般写"/"或者项目名称
                        response.addCookie(cookie);
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private void displayInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("ready_user");//用户登录了，这里通过ready_user获取用户的id
        user = userService.selectById(user.getId());//通过用户的id，向数据库中进行信息的查询；
        String userJson = JSON.toJSONString(user);//将查询到的信息封装成JSON字符串
        // 设置相应是json格式
        response.setContentType("application/json;charset=UTF-8");//设置响应的格式
        // 将JSON数据相应到前端中；
        PrintWriter out = response.getWriter();
        out.print(userJson);
        out.flush();
    }

    private void updateInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = null;
        String password = null;
        String profilePath = null;
        String telephone = null;
        String address = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            List<FileItem> list = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    if (fileItem.getFieldName().equals("username")) {
                        username = fileItem.getString("UTF-8");
                    } else if (fileItem.getFieldName().equals("password")) {
                        password = fileItem.getString("UTF-8");
                    } else if (fileItem.getFieldName().equals("telephone")) {
                        telephone = fileItem.getString("UTF-8");
                    } else if (fileItem.getFieldName().equals("address")) {
                        address = fileItem.getString("UTF-8");
                    }
                } else {
                    if (fileItem.getFieldName().equals("userImage")) {
                        profilePath = fileItem.getName();//如果没有图片的修改，在这里又提交了图片，就报错，报错就不执行下面的代码了
                        fileItem.write(new File("D:\\SoftwareDevelop\\Idea\\IdeaProjects\\Javaweb_OnlineShopping\\web\\user_upload\\images\\" + fileItem.getName()));
                    }
                }
            }
        }
        //进行数据库的更新和向前端相应最新的数据;
        User user = (User) request.getSession().getAttribute("ready_user");
        user = userService.selectById(user.getId());
        if (username != null && username != "") {
            user.setUsername(username);
        }
        if (password != null && password != "") {
            user.setPassword(encryption.stringMD5(password));
        }
        if (profilePath != null && profilePath != "") {
            user.setProfilePath(profilePath);
        }
        if (telephone != null && telephone != "") {
            user.setTelephone(telephone);
        }
        if (address != null && address != "") {
            user.setAddress(address);
        }
        //这里向前端相应，其实仅仅就返回图片的信息即可，不需要发挥JSON，因为前端是input页面，所以不需要进行数据的更新，用户自己就会对数据进行更新
        userService.updateById(user);
        String userJson = JSON.toJSONString(user);
        // 设置相应是json格式
        response.setContentType("application/json;charset=UTF-8");
        // 将JSON数据相应到前端中；
        PrintWriter out = response.getWriter();
        out.print(userJson);
        out.flush();
    }

    private void doCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        if (username == null || username.isEmpty()) {
            out.print("<span>用户名不能为空白哦！</span>");
        } else {
            User user = userService.checkSignUp(username);
            if (user != null) {
                out.print("<span color = 'red'>用户已存在！！</span>");
            } else {
                out.print("<span color = 'green'>可以使用该用户名</span>");
            }
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verificationCode = request.getParameter("verificationCode");
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (verificationCode.equals(code)) {
            Integer identity = 0;//0表示普通的用户，1表示管理员
            String encryptedPassword = encryption.stringMD5(password);
            User user = userService.login(username, encryptedPassword, identity);
            if (user != null) {
                /*
                 * 1.用户登录成功后，判断时候勾选了checkbox，如果勾选了，就开始进行Cookie的创建和保存。
                 * 2.正常保存session即可，里面存储用户id和用户名，方便后期的获取;
                 * */
                //通过用户名，获取用户的id,用来做后期的session验证
                //将用户名和密码存入cookie中，用来做十天的免登录；
                String checkbox = request.getParameter("checkbox");
                if (checkbox != null) {
                    if (checkbox.equals("on")) {
                        Cookie cookie1 = new Cookie("id", String.valueOf(user.getId()));
                        Cookie cookie2 = new Cookie("username", username);
                        Cookie cookie3 = new Cookie("password", password);
                        cookie1.setMaxAge(60 * 60 * 24 * 10);
                        cookie2.setMaxAge(60 * 60 * 24 * 10);
                        cookie3.setMaxAge(60 * 60 * 24 * 10);
                        cookie1.setPath(request.getContextPath());
                        cookie2.setPath(request.getContextPath());
                        cookie3.setPath(request.getContextPath());
                        response.addCookie(cookie1);
                        response.addCookie(cookie2);
                        response.addCookie(cookie3);
                    }
                }
                //直接把user对象存到session域当中，想获取什么获取什么
                //在这里判断用户的状态，是否被禁用，如果被禁用了，则跳转到禁用页面，不进行改用户session的创建。
                if (user.getUserStatus() == 1) {
                    request.getSession().setAttribute("ready_user", user);
                    response.sendRedirect(request.getContextPath() + "/index.jsp");//加上下一个路径，不然就是死循环
                } else {
                    response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private void doSignUp(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telephone");
        String encryptedPassword = encryption.stringMD5(password);
        System.out.println("encryptedPassword" + encryptedPassword);

        if (username != null && encryptedPassword != null && telephone != null) {
//            int flag = userService.signUp(username, encryptedPassword);

            int flag = userService.signUp(username, encryptedPassword,telephone);
            if (flag == 1) {
                //注册成功，直接跳转到登录页面，也就是我们的welcome外层页面；
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }

}


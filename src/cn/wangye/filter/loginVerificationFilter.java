package cn.wangye.filter;

import cn.wangye.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/*"})
public class loginVerificationFilter implements Filter {

    /* 过滤器的编写:
     * 1.未登录状态下，用户不能访问:个人中心，上传商品，不能进行商品的购买;
     * 拦截全部请求，后面遇到该放行的就放行
     */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        /* 过滤器的逻辑翻一下，如果访问的不是限定的资源，就放行，是的话，就需要就行session的验证了;
         */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        if (!servletPath.equals("/uploading.jsp") && !servletPath.equals("/personalHomepage.jsp")
                && !servletPath.equals("/display/information") && !servletPath.equals("/order/display")
                && !servletPath.equals("/display/myGoods")
                && !servletPath.equals("/order/displayByMerchantId")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("ready_user");//可以为空值？
            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }
}
package cn.wangye.web.servlet;

import cn.wangye.dao.MerchandiseDao;
import cn.wangye.dao.OrderDao;
import cn.wangye.dao.impl.MerchandiseDaoImpl;
import cn.wangye.dao.impl.OrderDaoImpl;
import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.Order_OrderMerchandise;
import cn.wangye.pojo.User;
import cn.wangye.service.OrderService;
import cn.wangye.service.UserService;
import cn.wangye.service.impl.OrderServiceImpl;
import cn.wangye.service.impl.UserServiceImpl;
import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/order", "/order/receiverInfo", "/order/merchandiseInfo", "/order/generate", "/order/display", "/order/displayByMerchantId"})
public class orderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if (servletPath.equals("/order")) {
            response.sendRedirect(request.getContextPath() + "/order.jsp");
        } else if (servletPath.equals("/order/receiverInfo")) {
            getReceiverInfo(request, response);
        } else if (servletPath.equals("/order/merchandiseInfo")) {
            getMerchandiseInfo(request, response);
        } else if (servletPath.equals("/order/generate")) {
            generateOrder(request, response);
        } else if (servletPath.equals("/order/display")) {
            displayOrder(request, response);
        } else if (servletPath.equals("/order/displayByMerchantId")) {
            displayOrderByMerchantId(request, response);
        }
    }


    private void displayOrderByMerchantId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User userSession = (User) request.getSession().getAttribute("ready_user");
        int merchantId = userSession.getId();
        OrderService orderService = new OrderServiceImpl();
        //这是一个封装好的订单和商品关系的list集合，有所有需要的数据;
        List<Order_OrderMerchandise> list = orderService.displayOrderByMerchant(merchantId);
        String orderOrderMerchandiseListJson = JSON.toJSONString(list);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(orderOrderMerchandiseListJson);
        out.flush();
    }

    private void displayOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User userSession = (User) request.getSession().getAttribute("ready_user");
        int userId = userSession.getId();
        OrderService orderService = new OrderServiceImpl();
        //这是一个封装好的订单和商品关系的list集合，有所有需要的数据;
        List<Order_OrderMerchandise> orderOrderMerchandiseList = orderService.displayOrderByUserId(userId);
        String orderOrderMerchandiseListJson = JSON.toJSONString(orderOrderMerchandiseList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(orderOrderMerchandiseListJson);
        out.flush();
    }

    private void generateOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        User userSession = (User) request.getSession().getAttribute("ready_user");
        int userId = userSession.getId();
        String receiveName = null;
        String telephone = null;
        String address = null;
        String notes = null;
        String merchandiseId = null;
        String purchaseAmount = null;
        //1 先判断上传的数据是否多段数据（只有是多段的数据，才是文件上传的）
        if (ServletFileUpload.isMultipartContent(request)) {
            // 创建 FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类 ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项 FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                // 循环判断，每一个表单项，是普通类型，还是上传的文件
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        // 普通表单项和解决乱码问题;
                        String fieldName = fileItem.getFieldName();
                        if (fieldName.equals("receive_name")) {
                            receiveName = fileItem.getString("UTF-8");
                        } else if (fieldName.equals("telephone")) {
                            telephone = fileItem.getString("UTF-8");
                        } else if (fieldName.equals("address")) {
                            address = fileItem.getString("UTF-8");
                        } else if (fieldName.equals("notes")) {
                            notes = fileItem.getString("UTF-8");
                        } else if (fieldName.equals("merchandiseId")) {
                            merchandiseId = fileItem.getString("UTF-8");
                        } else if (fieldName.equals("purchaseAmount")) {
                            purchaseAmount = fileItem.getString("UTF-8");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        OrderService orderService = new OrderServiceImpl();
        orderService.insertOrder_OrderMerchandise(userId, receiveName, telephone, address, notes, merchandiseId, purchaseAmount);
    }

    private void getMerchandiseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User userSession = (User) request.getSession().getAttribute("ready_user");
        //这里通过参数进行分流,参数为所想要购买的商品id和数量，如果存在一个参数，则记性单个商品的购买;
        String merchandiseId = request.getParameter("merchandiseId");
        String purchaseAmount = request.getParameter("purchaseAmount");

        List<Merchandise> merchandiseList = new ArrayList<>();
        if (merchandiseId == null) {
            int userId = userSession.getId();
            OrderService orderService = new OrderServiceImpl();
            merchandiseList = orderService.selectByIsChecked(userId);
        } else {
            MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();
            Merchandise merchandise = merchandiseDao.selectById(Integer.parseInt(merchandiseId));
            merchandise.setAmount(Integer.valueOf(purchaseAmount));
            merchandiseList.add(merchandise);
        }
        String jsonMerchandiseList = JSON.toJSONString(merchandiseList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonMerchandiseList);
        out.flush();

    }

    private void getReceiverInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User userSession = (User) request.getSession().getAttribute("ready_user");
        String jsonUserInfo = null;
        if (userSession != null) {
            int userId = userSession.getId();
            UserService userService = new UserServiceImpl();
            User user = userService.selectById(userId);
            jsonUserInfo = JSON.toJSONString(user);
        }
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonUserInfo);
        out.flush();
    }

}

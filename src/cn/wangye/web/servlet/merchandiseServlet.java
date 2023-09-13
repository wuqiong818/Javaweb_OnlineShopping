package cn.wangye.web.servlet;

import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.User;
import cn.wangye.service.MerchandiseService;
import cn.wangye.service.impl.MerchandiseServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/display/merchandise", "/uploading/merchandise", "/display/allMerchandise",
        "/detail/merchandise", "/search/merchandise","/search", "/display/myGoods", "/update/myGoods",
        "/update/merchandise","/stop/merchandise","/newArrival/merchandise","/delete/merchandise","/select/SingleMerchandise"})
public class merchandiseServlet extends HttpServlet {
    MerchandiseService merchandiseService = new MerchandiseServiceImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");
        String servletName = request.getServletPath();
        if (servletName.equals("/display/merchandise")) {
            doSelectMerchandise(request, response);
        } else if (servletName.equals("/uploading/merchandise")) {
            try {
                doUploadingMerchandise(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (servletName.equals("/display/allMerchandise")) {
            doAllSelectMerchandise(request, response);
        } else if (servletName.equals("/detail/merchandise")) {
            doDetailMerchandise(request, response);
        } else if (servletName.equals("/search/merchandise")) {
            doSearchMerchandise(request, response);
        }else if (servletName.equals("/search")) {
            doSearch(request, response);
        } else if (servletName.equals("/display/myGoods")) {
            displayAllMyGoods(request, response);
        } else if (servletName.equals("/update/myGoods")) {
            displayMyGoods(request, response);
        } else if (servletName.equals("/update/merchandise")) {
            try {
                updateMerchandise(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (servletName.equals("/stop/merchandise")) {
            stopMerchandise(request, response);
        }else if (servletName.equals("/newArrival/merchandise")) {
            newArrivalMerchandise(request, response);
        }else if (servletName.equals("/delete/merchandise")) {
            deleteMerchandise(request, response);
        }
        else if (servletName.equals("/select/SingleMerchandise")) {
            selectSingleMerchandise(request, response);
        }


    }

    private void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        List<Merchandise> merchandiseList = merchandiseService.searchAllMerchandise(keyword);
        String searchResultJson = JSON.toJSONString(merchandiseList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(searchResultJson);
        out.flush();
    }

    private void deleteMerchandise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //原因:这所以删除不了，是因为外键约束的存在，改用软删除实现,在商品表中，将merchandiseStatus改为3即可;
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        int merchandiseStatus = 3;//0售空，1正常，2下架，3删除，逻辑一样,不过是过程不可逆：
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        merchandise.setMerchandiseStatus(merchandiseStatus);
        int count = merchandiseService.updateById(merchandise);
        if (count ==1){
            response.setContentType("text/html;charset = UTF-8");
            PrintWriter out = response.getWriter();
            out.print("删除成功");
            out.flush();
        }
    }

    private void newArrivalMerchandise(HttpServletRequest request, HttpServletResponse response) {
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        int merchandiseStatus = 1;//1是回复正常，也就是重新上架;
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        merchandise.setMerchandiseStatus(merchandiseStatus);
        merchandiseService.updateById(merchandise);
    }

    private void stopMerchandise(HttpServletRequest request, HttpServletResponse response) {
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        int merchandiseStatus = 2;//2是下架
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        merchandise.setMerchandiseStatus(merchandiseStatus);
        merchandiseService.updateById(merchandise);
    }
    private void updateMerchandise(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //用户对已经上传的数据进行修改;
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        String name = null;
        String documentPath = null;
        String photoPath = "";
        String feature1 = null;
        String feature2 = null;
        String feature3 = null;
        String description = null;
        String category = null;
        Double price = null;
        Integer amount = null;
        String feature;
        Integer merchandiseStatus;
        Timestamp timestamp;
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if (fileItem.getFieldName().equals("merchandise_name")) {
                            name = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature1")) {
                            feature1 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature2")) {
                            feature2 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature3")) {
                            feature3 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_description")) {
                            description = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_category")) {
                            category = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_price")) {
                            price = Double.valueOf(fileItem.getString("UTF-8"));
                        } else if (fileItem.getFieldName().equals("merchandise_quantity")) {
                            amount = Integer.valueOf(fileItem.getString("UTF-8"));
                        }
                    } else {
                        if (fileItem.getFieldName().equals("merchandise_document")) {
                            documentPath = fileItem.getName();
                            fileItem.write(new File("D:\\SoftwareDevelop\\Idea\\IdeaProjects\\Javaweb_OnlineShopping\\web\\user_upload\\document\\" + fileItem.getName()));

                        } else if (fileItem.getFieldName().equals("merchandise_photoPath")) {
                            photoPath += fileItem.getName() + ";";//这里之所以这样处理，是因为图片可能不止有一个;
                            fileItem.write(new File("D:\\SoftwareDevelop\\Idea\\IdeaProjects\\Javaweb_OnlineShopping\\web\\user_upload\\images\\" + fileItem.getName()));
                        }
                    }
                }
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        }
        feature = feature1 + ";" + feature2 + ";" + feature3;//一定要注意符号,是半角分号;;;;;
        merchandiseStatus = 1;//这个路径;
        timestamp = new Timestamp(System.currentTimeMillis());
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        merchandise.setName(name);
        merchandise.setDocumentPath(documentPath);
        merchandise.setPhotoPath(photoPath);
        merchandise.setFeature(feature);
        merchandise.setDescription(description);
        merchandise.setCategory(category);
        merchandise.setPrice(price);
        merchandise.setAmount(amount);
        merchandise.setMerchandiseStatus(merchandiseStatus);
        merchandiseService.updateById(merchandise);
        response.sendRedirect(request.getContextPath()+"/GoToIndexPage.jsp");
    }



    private void selectSingleMerchandise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        //先向数据库中根据商品的id,去获取数据，打回前端，创造出来一个新页面，原有的input框中填写上数据
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        String merchandiseJson = JSON.toJSONString(merchandise);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(merchandiseJson);
        out.flush();
    }

    private void displayMyGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int merchandiseId = Integer.parseInt(request.getParameter("merchandiseId"));
        //先向数据库中根据商品的id,去获取数据，打回前端，创造出来一个新页面，原有的input框中填写上数据
        Merchandise merchandise = merchandiseService.selectById(merchandiseId);
        String merchandiseJson = JSON.toJSONString(merchandise);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(merchandiseJson);
        out.flush();
    }

    private void displayAllMyGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("ready_user");
        int userId = user.getId();
        List<Merchandise> merchandiseList = merchandiseService.selectMerchandiseByMerchantId(userId);
        String merchandiseListJson = JSON.toJSONString(merchandiseList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(merchandiseListJson);
        out.flush();
    }


    private void doSearchMerchandise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Merchandise> merchandiseList = merchandiseService.searchAllMerchandise(keyword);
        //把这一个给刷新了
        //搜索结果出来之后，点击搜索框，重置页面,将搜索结果都展示出来;
        String searchResultJson = JSON.toJSONString(merchandiseList);
        response.setContentType("application/json;charset = UTF-8");
        PrintWriter out = response.getWriter();
        out.print(searchResultJson);
        out.flush();
    }

    private void doDetailMerchandise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("merchandiseId");
        Merchandise merchandise = merchandiseService.selectById(Integer.parseInt(id));
        request.setAttribute("merchandise", merchandise);
        request.getRequestDispatcher("/merchandiseDetail.jsp").forward(request, response);
    }

    private void doAllSelectMerchandise(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Merchandise> allMerchandise = merchandiseService.selectAllMerchandise();
        request.setAttribute("allMerchandise", allMerchandise);
        request.getRequestDispatcher("/display.jsp").forward(request, response);
    }

    private void doUploadingMerchandise(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //在这里接受用户上传的商品信息，将其保存在数据库中
        //这里之所以获取null的原因是因为用户上传的是以二进制的形式进行的上传，通过参数无法进行正常地获取；
        String name = null;
        String documentPath = null;
        String photoPath = "";
        String feature1 = null;
        String feature2 = null;
        String feature3 = null;
        String description = null;
        String category = null;
        Double price = null;
        Integer amount = null;
        String feature;
        Integer status;
        Timestamp timestamp;
        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if (fileItem.getFieldName().equals("merchandise_name")) {
                            name = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature1")) {
                            feature1 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature2")) {
                            feature2 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_feature3")) {
                            feature3 = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_description")) {
                            description = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_category")) {
                            category = fileItem.getString("UTF-8");
                        } else if (fileItem.getFieldName().equals("merchandise_price")) {
                            price = Double.valueOf(fileItem.getString("UTF-8"));
                        } else if (fileItem.getFieldName().equals("merchandise_quantity")) {
                            amount = Integer.valueOf(fileItem.getString("UTF-8"));
                        }
                    } else {
                        if (fileItem.getFieldName().equals("merchandise_document")) {
                            documentPath = fileItem.getName();
                            fileItem.write(new File("D:\\SoftwareDevelop\\Idea\\IdeaProjects\\Javaweb_OnlineShopping\\web\\user_upload\\document\\" + fileItem.getName()));

                        } else if (fileItem.getFieldName().equals("merchandise_photoPath")) {
                            photoPath += fileItem.getName() + ";";//这里之所以这样处理，是因为图片可能不止有一个;
                            fileItem.write(new File("D:\\SoftwareDevelop\\Idea\\IdeaProjects\\Javaweb_OnlineShopping\\web\\user_upload\\images\\" + fileItem.getName()));
                        }
                    }
                }
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        }
        feature = feature1 + ";" + feature2 + ";" + feature3;//一定要注意符号,是半角分号;;;;;
        status = 1;
        timestamp = new Timestamp(System.currentTimeMillis());
        User user = (User) request.getSession().getAttribute("ready_user");
        int userId = user.getId();
        merchandiseService.addMerchandise(name, documentPath, photoPath, feature, description, category, price, amount, status, timestamp, userId);
    }

    private void doSelectMerchandise(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Merchandise> list = merchandiseService.selectAllMerchandise();
        String merchandiseData = JSON.toJSONString(list);
        // 设置相应是json格式
        response.setContentType("application/json;charset=UTF-8");
        // 将JSON数据相应到前端中；
        PrintWriter out = response.getWriter();
        out.print(merchandiseData);
        out.flush();
    }
}

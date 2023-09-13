package cn.wangye.dao.impl;

import cn.wangye.pojo.Order_Merchandise;
import cn.wangye.pojo.Order_OrderMerchandise;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Order_OrderMerchandiseDaoImpl {


    public List<Order_OrderMerchandise> selectAllByPurchaseId(int purchaseId) {
        List<Order_OrderMerchandise> allOrder_Merchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_OrderMerchandise order_orderMerchandise = null;
        Integer condition = 0;//用来判断orderId是否相同


        List<Integer> merchandiseIdArray = new ArrayList<>();
        List<Integer> merchandiseAmountArray = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select orderTotal,order_merchandise.* from `order` INNER JOIN `order_merchandise` on `order`.id = order_merchandise.orderId where purchaserId = ?";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();



            while (rs.next()) {
                Integer orderId = rs.getInt("orderId");
                Double orderTotal = rs.getDouble("orderTotal");
                Integer merchandiseId = rs.getInt("merchandiseId");
                Integer amount = rs.getInt("amount");

                //我需要得到一个什么样的集合，最理想的是，orderId,total list,list
                order_orderMerchandise = new Order_OrderMerchandise();
                order_orderMerchandise.setOrderTotal(orderTotal);
                order_orderMerchandise.setId(orderId);
//                order_orderMerchandise.setMerchandiseId(merchandiseId);
//                order_orderMerchandise.setAmount(amount);
                merchandiseIdArray = new ArrayList<>();
                merchandiseIdArray.add(merchandiseId);
                merchandiseAmountArray = new ArrayList<>();
                merchandiseAmountArray.add(amount);


                if (condition == 0){
                    condition = orderId;//为0的话，给予初始赋值;
                }
                if (condition == orderId){

                    order_orderMerchandise.setMerchandiseIdArray(merchandiseIdArray);
                    order_orderMerchandise.setMerchandiseAmountArray(merchandiseAmountArray);
                }else {
                    condition = orderId;
                }
                allOrder_Merchandise.add(order_orderMerchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrder_Merchandise;
    }


}

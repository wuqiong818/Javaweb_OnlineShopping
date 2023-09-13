package cn.wangye.dao;

import cn.wangye.pojo.Order_Merchandise;

import java.util.List;

public interface Order_MerchandiseDao {

    public int insertOrder_Merchandise(Integer orderId, Integer merchandiseId, Integer amount, Double total,Integer merchantId);

    public Order_Merchandise selectById(int id);

    public List<Order_Merchandise> selectAllByOrderId(int orderId);

    public List<Order_Merchandise> selectAll();

}

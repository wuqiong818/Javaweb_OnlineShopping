package cn.wangye.dao;

import cn.wangye.pojo.Order;
import cn.wangye.pojo.Order_Merchandise;

import java.util.List;

public interface OrderDao {
    public int insertOrder(Integer purchaseId, String receiveName, String telephone, String address, String note ,Double orderTotal);

    public int[] insertOrderArray(Integer purchaseId, String receiveName, String telephone, String address, String note ,Double orderTotal);

    public int deleteById(int id);

    public int updateById(Order order);

    public Order selectById(int id);

    public List<Order> selectAll();

    public List<Order> selectByPurchaserId(int purchaserId);

    public List<Order_Merchandise> selectByMerchantId(int merchantId);
}

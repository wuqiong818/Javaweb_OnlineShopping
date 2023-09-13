package cn.wangye.service;

import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.Order_OrderMerchandise;

import java.util.List;

public interface OrderService {
    public List<Merchandise> selectByIsChecked(int userId);

    public int insertOrder(Integer purchaseId, String receiveName, String address, String telephone, String note, Double orderTotal);

    public void insertOrder_OrderMerchandise(int userId, String receiveName, String telephone, String address, String notes, String merchandiseId, String purchaseAmount);

    public List<Order_OrderMerchandise> displayOrderByUserId(int userId);

    public List<Order_OrderMerchandise> displayOrderByMerchant(int merchantId);
}

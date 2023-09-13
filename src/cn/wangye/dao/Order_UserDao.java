package cn.wangye.dao;

import cn.wangye.pojo.Order_User;

import java.util.List;

public interface Order_UserDao {
    public int insertOrder_User(Integer userId, Integer orderId);

    public int deleteOrder_UserById(int id);
    public Order_User selectById(int id);
    public List<Order_User> selectAll();

}

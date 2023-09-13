package cn.wangye.dao.impl;

import cn.wangye.dao.Order_MerchandiseDao;
import cn.wangye.dao.Order_UserDao;
import cn.wangye.pojo.Order;
import cn.wangye.pojo.Order_User;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order_UserDaoImpl implements Order_UserDao {

    /**
     * 向用户_订单表中添加信息;
     *
     * @param userId  用户的id
     * @param orderId 订单的id
     * @return 1成功 0失败
     */
    public int insertOrder_User(Integer userId, Integer orderId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into `order_user` (userId,orderId) VALUES (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, orderId);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 通过Id进行删除，删除一定不是平白无故的，而是先获取信息之后，才会进行删除。
     *
     * @param id 订单用户表中的id
     * @return 0成功 1失败
     */
    public int deleteOrder_UserById(int id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from `order_user` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


    /**
     * 通过id,对订单信息进行查询
     *
     * @param id order_user的id
     * @return Order_User 封装着订单和用户的关系,
     */
    public Order_User selectById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_User order_user = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order_user` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer userId = rs.getInt("userId");
                Integer orderId = rs.getInt("orderId");
                order_user = new Order_User();
                order_user.setUserId(userId);
                order_user.setOrderId(orderId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return order_user;
    }


    /**
     * 查找所有订单和用户的所有信息
     *
     * @return 返回一个封装了包含用户和订单的的List集合
     */
    public List<Order_User> selectAll() {
        List<Order_User> allOrder_User = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_User order_user = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order_user`";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer userId = rs.getInt("userId");
                Integer orderId = rs.getInt("orderId");
                order_user = new Order_User();
                order_user.setUserId(userId);
                order_user.setOrderId(orderId);
                allOrder_User.add(order_user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrder_User;
    }

}

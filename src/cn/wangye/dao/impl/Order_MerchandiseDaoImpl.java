package cn.wangye.dao.impl;

import cn.wangye.dao.Order_MerchandiseDao;
import cn.wangye.pojo.Order;
import cn.wangye.pojo.Order_Merchandise;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order_MerchandiseDaoImpl implements Order_MerchandiseDao {
    /**
     * 增
     *
     * @param 'purchaseId  购买者的id'
     * @param 'receiveName 收货人信息'
     * @param 'telephone   收货人信息'
     * @param 'address     收货人地址'
     * @param 'note        备注'
     * @return 1成功 0失败
     */
    public int insertOrder_Merchandise(Integer orderId, Integer merchandiseId, Integer amount, Double total,Integer merchantId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into `order_merchandise` (orderId, merchandiseId, amount, total,merchantId) VALUES (?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, merchandiseId);
            ps.setInt(3, amount);
            ps.setDouble(4, total);
            ps.setInt(5, merchantId);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /*    *//**
     * 通过Id进行删除，删除一定不是平白无故的，而是先获取信息之后，才会进行删除。
     *
     * @param id 订单的id
     * @return 0成功 1失败
     *//*
    public int deleteById(int id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from `order` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }*/

    /* *//**
     * 用户信息完善和更改时使用；
     *
     * @param user 直接通过传入用户信息对象User的方式，进行信息的完善；
     * @return 返回1代表成功，0则失败
     * 这里需要注意的是对于当前用户id的传入问题；
     *//*
     *//**
     * 这个是改，修改订单的信息
     *
     * @param order Order对象
     * @return 0成功 1失败
     *//*
    public int updateById(Order order) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            Integer id = order.getId();
            Integer purchaserId = order.getPurchaserId();
            String receiverName = order.getReceiverName();
            String address = order.getAddress();
            String telephone = order.getTelephone();
            String note = order.getNotes();
            Timestamp purchaseDate = order.getPurchaseDate();
            if (id == 0) {
                throw new RuntimeException("系统错误，请联系管理员（id未传入）");
            }
            String sql = "update `order`set purchaserId  = ?,receiverName = ?,telephone=?,address=?,notes=? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, purchaserId);
            ps.setString(2, receiverName);
            ps.setString(3, telephone);
            ps.setString(4, address);
            ps.setString(5, note);
            ps.setInt(6, id);//id,where后面的条件,注意这里如果没有id话就不能进行更改;
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }
*/

    /**
     *
     * @param id Order_Merchandise表中自增长的id
     * @return Order_Merchandise对象
     */
    public Order_Merchandise selectById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_Merchandise order_merchandise = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order_merchandise` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer orderId = rs.getInt("orderId");
                Integer merchandiseId = rs.getInt("merchandiseId");
                Integer amount = rs.getInt("amount");
                Double total = rs.getDouble("total");
                order_merchandise = new Order_Merchandise();
                order_merchandise.setId(id);
                order_merchandise.setOrderId(orderId);
                order_merchandise.setMerchandiseId(merchandiseId);
                order_merchandise.setAmount(amount);
                order_merchandise.setTotal(total);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return order_merchandise;
    }


    /**
     * 通过 orderId在Order_Merchandise这一个表中尽心数据的查询
     * @return list集合
     */
    public List<Order_Merchandise> selectAllByOrderId(int orderId) {
        List<Order_Merchandise> allOrder_Merchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_Merchandise order_merchandise = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order_merchandise` where orderId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer merchandiseId = rs.getInt("merchandiseId");
                Integer amount = rs.getInt("amount");
                Double total = rs.getDouble("total");
                order_merchandise = new Order_Merchandise();
                order_merchandise.setId(id);
                order_merchandise.setOrderId(orderId);
                order_merchandise.setMerchandiseId(merchandiseId);
                order_merchandise.setAmount(amount);
                order_merchandise.setTotal(total);
                allOrder_Merchandise.add(order_merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrder_Merchandise;
    }

    /**
     * 查询所有Order_Merchandise表中的数据;
     * @return
     */
    public List<Order_Merchandise> selectAll() {
        List<Order_Merchandise> allOrder_Merchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_Merchandise order_merchandise = new Order_Merchandise();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order_merchandise`";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer orderId = rs.getInt("orderId");
                Integer merchandiseId = rs.getInt("merchandiseId");
                Integer amount = rs.getInt("amount");
                Double total = rs.getDouble("total");
                order_merchandise = new Order_Merchandise();
                order_merchandise.setId(id);
                order_merchandise.setOrderId(orderId);
                order_merchandise.setMerchandiseId(merchandiseId);
                order_merchandise.setAmount(amount);
                order_merchandise.setTotal(total);
                allOrder_Merchandise.add(order_merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrder_Merchandise;
    }
}
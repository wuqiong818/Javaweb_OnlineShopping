package cn.wangye.dao.impl;

import cn.wangye.dao.OrderDao;
import cn.wangye.pojo.Order;
import cn.wangye.pojo.Order_Merchandise;
import cn.wangye.pojo.Order_OrderMerchandise;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {


    /**
     * 增
     *
     * @param purchaseId  购买者的id
     * @param receiveName 收货人信息
     * @param telephone   收货人信息
     * @param address     收货人地址
     * @param note        备注
     * @return 1成功 0失败
     */
    public int insertOrder(Integer purchaseId, String receiveName, String telephone, String address, String note, Double orderTotal) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into `order` (purchaserId, receiverName, telephone, address, notes, purchaseDate,orderTotal) VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, purchaseId);
            ps.setString(2, receiveName);
            ps.setString(3, telephone);
            ps.setString(4, address);
            ps.setString(5, note);
            ps.setTimestamp(6, timestamp);
            ps.setDouble(7, orderTotal);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    public int[] insertOrderArray(Integer purchaseId, String receiveName, String telephone, String address, String note, Double orderTotal) {
        int count = 0;
        int orderId = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into `order` (purchaserId, receiverName, telephone, address, notes, purchaseDate,orderTotal) VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, purchaseId);
            ps.setString(2, receiveName);
            ps.setString(3, telephone);
            ps.setString(4, address);
            ps.setString(5, note);
            ps.setTimestamp(6, timestamp);
            ps.setDouble(7, orderTotal);
            count = ps.executeUpdate();
            ResultSet generateKeys = ps.getGeneratedKeys();
            if (generateKeys.next()) {
                orderId = generateKeys.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return new int[]{count, orderId};
    }

    /**
     * 通过Id进行删除，删除一定不是平白无故的，而是先获取信息之后，才会进行删除。
     *
     * @param id 订单的id
     * @return 0成功 1失败
     */
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
    }

    /**
     * 用户信息完善和更改时使用；
     *
     * @param user 直接通过传入用户信息对象User的方式，进行信息的完善；
     * @return 返回1代表成功，0则失败
     * 这里需要注意的是对于当前用户id的传入问题；
     */
    /**
     * 这个是改，修改订单的信息
     *
     * @param order Order对象
     * @return 0成功 1失败
     */
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


    /**
     * 通过id,对订单信息进行查询
     *
     * @param id order的id
     * @return Order订单对象, 封装订单的信息
     */
    public Order selectById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order` where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer purchaserId = rs.getInt("purchaserId");
                String receiverName = rs.getString("receiverName");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String notes = rs.getString("notes");
                Timestamp purchaseDate = rs.getTimestamp("purchaseDate");
                order = new Order();
                order.setPurchaserId(purchaserId);
                order.setReceiverName(receiverName);
                order.setTelephone(telephone);
                order.setAddress(address);
                order.setNotes(notes);
                order.setPurchaseDate(purchaseDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return order;
    }

    public List<Order> selectByPurchaserId(int purchaserId) {
        List<Order> allOrders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order` where purchaserId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, purchaserId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String receiverName = rs.getString("receiverName");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String notes = rs.getString("notes");
                Double orderTotal = rs.getDouble("orderTotal");
                Timestamp purchaseDate = rs.getTimestamp("purchaseDate");
                order = new Order();
                order.setId(id);
                order.setOrderTotal(orderTotal);
                order.setPurchaserId(purchaserId);
                order.setReceiverName(receiverName);
                order.setTelephone(telephone);
                order.setAddress(address);
                order.setNotes(notes);
                order.setPurchaseDate(purchaseDate);
                allOrders.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrders;
    }


    /**
     * 查找所有用户的信息
     *
     * @return 返回一个封装了所有用户的List集合
     */
    public List<Order> selectAll() {
        List<Order> allOrders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = new Order();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from `order`";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer purchaserId = rs.getInt("purchaserId");
                String receiverName = rs.getString("receiverName");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String notes = rs.getString("notes");
                Timestamp purchaseDate = rs.getTimestamp("purchaseDate");
                order = new Order();
                order.setId(id);
                order.setPurchaserId(purchaserId);
                order.setReceiverName(receiverName);
                order.setTelephone(telephone);
                order.setAddress(address);
                order.setNotes(notes);
                order.setPurchaseDate(purchaseDate);
                allOrders.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrders;
    }

    /*
     * 其实这一个功能从本质上来说，还是订单和商品之间的关系
     * 和用户购买行为产生的订单是一样的,不过只是查询的sql语句中的条件不一样罢了，里面所封装的数据是相同的;
     * 我需要的数据有：订单：订单ID、收货人，地址，电话,备注/购买的时间
     * 商品的数量，商品的id(那一个商品)
     * */
    public List<Order_Merchandise> selectByMerchantId(int merchantId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_Merchandise order_merchandise = null;
        List<Order_Merchandise> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from order_merchandise where merchantId =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, merchantId);
            rs = ps.executeQuery();
            while(rs.next()) {
                Integer id = rs.getInt("id");
                Integer orderId = rs.getInt("orderId");
                Integer merchandiseId = rs.getInt("merchandiseId");
                Integer amount = rs.getInt("amount");
                Double total = rs.getDouble("total");
                order_merchandise = new Order_Merchandise();
                order_merchandise.setId(id);
                order_merchandise.setOrderId(orderId);
                order_merchandise.setMerchandiseId(merchandiseId);
                order_merchandise.setMerchantId(merchantId);
                order_merchandise.setAmount(amount);
                order_merchandise.setTotal(total);
                list.add(order_merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return list;
    }


    //最底层数据库表的设计失误，后端要想实现同样的效果，只能去用更加复杂的逻辑去弥补;比如以下这一个sql语句和方法;
    /*public List<Order_OrderMerchandise_Merchandise> selectByMerchantId(int merchantId) {
        List<Order_OrderMerchandise_Merchandise> allOrders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order_OrderMerchandise_Merchandise omo = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT *\n" +
                    "FROM merchandise\n" +
                    "         LEFT JOIN order_merchandise ON merchandise.id = order_merchandise.merchandiseId\n" +
                    "         LEFT JOIN `order` ON `order`.id = order_merchandise.orderId\n" +
                    "WHERE merchandise.merchantId = ? && order_merchandise.orderId is not null\n" +
                    "ORDER BY purchaseDate";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, merchantId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer orderId = rs.getInt("`order`.id");
                Integer amount = rs.getInt("order_merchandise.amount");
                Integer merchandiseId = rs.getInt("merchandise.id");

                omo = new Order_OrderMerchandise_Merchandise();
                Order order = new Order();
                Merchandise merchandise = new Merchandise();
                order.setId(orderId);

                omo.setOrder(order);
                omo.setPurchaseMerchandiseAmount(amount);

                merchandise.setId(merchandiseId);
                omo.setMerchandise(merchandise);

                allOrders.add(omo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allOrders;
    }*/
}

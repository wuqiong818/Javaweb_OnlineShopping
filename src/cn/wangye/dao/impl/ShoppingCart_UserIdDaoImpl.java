package cn.wangye.dao.impl;

import cn.wangye.dao.ShoppingCart_UserIdDao;
import cn.wangye.pojo.User;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShoppingCart_UserIdDaoImpl implements ShoppingCart_UserIdDao {
    /**
     * 通过插入用户的id,创建购物车的自增id
     * @param userId 用户id
     * @return 1成功 0失败
     */
    public int insert(Integer userId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into shoppingcart_user (userId)values (?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 通过用户id,删除购物车的id
     * @param userId 用户id
     * @return 1删除成功 0失败
     */
    public int deleteById(Integer userId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from shoppingcart_user where userId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 通过用户的id,去查找用户对应这购物车id
     *
     * @param userId 用户的id
     * @return 该用户对应这购物车的id
     */
    public int selectByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        Integer id = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select id from shoppingcart_user where userId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return id;
    }
}

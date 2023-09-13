package cn.wangye.dao.impl;

import cn.wangye.dao.ShoppingCart_MerchandiseDao;
import cn.wangye.pojo.Merchandise;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart_MerchandiseDaoImpl implements ShoppingCart_MerchandiseDao {

    /**
     * 这个方法是如果购物车中已经添加了该商品了，就直接修改数量，如果没有
     * 就插入一条重新插入一条数据
     *
     * @param merchandiseId  商品的id
     * @param shoppingCartId 购物车的id和用户id挂钩
     * @param amount         添加到购物车中商品的数量
     * @return 1成功 0失败
     */
    public int insert_update(Integer merchandiseId, Integer shoppingCartId, Integer amount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE merchandise_shoppingcart SET amount = amount + ? WHERE merchandiseId = ? && shoppingCartId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, merchandiseId);
            ps.setInt(3, shoppingCartId);
            count = ps.executeUpdate();
            if (count == 0) {
                String sql2 = "insert into merchandise_shoppingcart (merchandiseId, shoppingCartId, amount) values(?,?,?)";
                ps = conn.prepareStatement(sql2);
                ps.setInt(1, merchandiseId);
                ps.setInt(2, shoppingCartId);
                ps.setInt(3, amount);
                count = ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 通过购物车与商品关系表中的id删除购物车中添加的商品
     *
     * @param id 购物车与商品关系表中的id
     * @return 1成功 0失败
     */
    public int deleteById(Integer id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from merchandise_shoppingcart where id =?";
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
     * 通过购物车和商品中的id,进行购物车中某一个商品数量的修改
     *
     * @param id     商品表的id
     * @param amount 将购物车中某个商品更改成该数量
     * @return 1成功 0失败
     */
    public int updateById(int id, int amount) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update merchandise_shoppingcart set amount = ? where id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setInt(2, id);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


    /**
     * 通过购物车的id去查询用户在购物车中添加的所有商品
     *
     * @param shoppingCartId 购物车的id
     * @return LIST集合，里面封装了该购物车中的所有商品
     */
    public List<Merchandise> selectAll(Integer shoppingCartId) {
        List<Merchandise> allMerchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = new Merchandise();
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM merchandise_shoppingcart INNER JOIN merchandise ON merchandise_shoppingcart.merchandiseId = merchandise.id WHERE merchandise_shoppingcart.shoppingCartId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, shoppingCartId);
            rs = ps.executeQuery();
            while (rs.next()) {
                //这一个id是购物车商品表中的id
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String documentPath = rs.getString("documentPath");
                String photoPath = rs.getString("photoPath");
                String feature = rs.getString("feature");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("amount");
                String isChecked = rs.getString("isChecked");
                Timestamp issuedDate = rs.getTimestamp("issuedDate");
                int merchandiseAmount = rs.getInt("merchandise.amount");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                merchandise = new Merchandise();
                merchandise.setId(id);
                merchandise.setName(name);
                merchandise.setDocumentPath(documentPath);
                merchandise.setPhotoPathArray(photoPath.split(";"));
                merchandise.setPhotoPath(photoPath);
                merchandise.setFeature(feature);
                merchandise.setFeatureArray(feature.split(";"));
                merchandise.setDescription(description);
                merchandise.setCategory(category);
                merchandise.setPrice(price);
                merchandise.setAmount(amount);
                merchandise.setMerchandiseStatus(merchandiseStatus);//这个是商品的状态
                merchandise.setIsChecked(isChecked);//这一个状态是是否勾选了
                merchandise.setMerchandiseAmount(merchandiseAmount);
                merchandise.setIssuedDate(issuedDate);
                allMerchandise.add(merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allMerchandise;
    }


    /**
     * 更改购物车当中商品是否勾选的状态和添加的数量
     * @param id 购物车和商品表的ID
     * @param status 购物车中商品的数量
     * @return 1成功 0失败
     */
    public int updateStatus(int id, int status) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update merchandise_shoppingcart set isChecked = ? where id =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, id);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    public List<Merchandise> selectByIsChecked(int shoppingCartId) {
        List<Merchandise> allMerchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = new Merchandise();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise_shoppingcart inner Join merchandise on merchandise_shoppingcart.merchandiseId = merchandise.id where merchandise_shoppingcart.isChecked =1&&shoppingCartId = ?&&merchandise.merchandiseStatus =1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, shoppingCartId);
            rs = ps.executeQuery();
            while (rs.next()) {
                //这一个id是购物车商品表中的id
                Integer id = rs.getInt("id");
                Integer merchandiseId = rs.getInt("merchandiseId");
                String name = rs.getString("name");
                String documentPath = rs.getString("documentPath");
                String photoPath = rs.getString("photoPath");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("merchandise_shoppingcart.amount");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                merchandise = new Merchandise();
                merchandise.setId(id);
                merchandise.setMerchandiseId(merchandiseId);
                merchandise.setName(name);
                merchandise.setDocumentPath(documentPath);
                merchandise.setPhotoPathArray(photoPath.split(";"));
                merchandise.setPhotoPath(photoPath);
                merchandise.setPrice(price);
                merchandise.setAmount(amount);
                merchandise.setMerchandiseStatus(merchandiseStatus);
                allMerchandise.add(merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allMerchandise;
    }

    public int deleteByIsChecked() {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from merchandise_shoppingcart where isChecked =?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }
}
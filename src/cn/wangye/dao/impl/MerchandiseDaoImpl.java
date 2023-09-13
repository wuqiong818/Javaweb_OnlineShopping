package cn.wangye.dao.impl;

import cn.wangye.dao.MerchandiseDao;
import cn.wangye.pojo.Merchandise;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseDaoImpl implements MerchandiseDao {
    /*在这里完成对merchandise表增删改查的操作*/
    //增 用户添加商品（用户自行上架商品）ok
    //删 用户自己上架的相关商品
    //改 用户更改自己上架的商品信息
    //查 用户所有商品的信息 通过商品名获取商品的id

    /**
     * 这个方法是进行商品的添加操作，用户可以直接在前端页面填写表单完成商品的添加，这是二手商城的核心
     *
     * @param "name              商品名"
     * @param "photoPath         上传商品的图片路径"
     * @param "feature           商品的特征，字符串，多个的话，以;结尾"
     * @param "description       商品的详情描述"
     * @param "category          商品的所属门类"
     * @param "price             商品的价格"
     * @param "amount            商品的数量"
     * @param "merchandiseStatus 商品的状态"
     * @param "issuedDate        商品的上传时间，默认自动生成"
     * @return 返回一个数组，0：是否插入成功，1：插入商品的id 这里通过了一个特殊的方法，在插入商品的同时，获取了商品的id
     */

    @Override
    public int[] insert(String name, String documentPath, String photoPath, String feature, String description, String category,
                        Double price, Integer amount, Integer merchandiseStatus, Timestamp issuedDate) {
        int count = 0;
        int merchandiseId = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into merchandise (name,photoPath, feature, description, category, price, amount, merchandiseStatus,issuedDate,documentPath)values (?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, photoPath);
            ps.setString(3, feature);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setDouble(6, price);
            ps.setInt(7, amount);
            ps.setInt(8, merchandiseStatus);
            ps.setTimestamp(9, issuedDate);
            ps.setString(10, documentPath);
            count = ps.executeUpdate();
            //注意哦，商品的插入是给返回id的;
            ResultSet generateKeys = ps.getGeneratedKeys();
            if (generateKeys.next()) {
                merchandiseId = generateKeys.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return new int[]{count, merchandiseId};
    }

    /**
     * 通过Id进行商品的注销
     *
     * @param id 注意商品id的传入;
     * @return count 1删除成功，0失败
     */
    public int deleteById(int id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from javaweb.merchandise where id=?";
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
     * 这里是上传商家对商品进行修改的方法
     *
     * @param merchandise 传入商品对象，获取传入的值，在原值的基础上进行修改，具体的操作在servlet层实现
     * @return int 1成功，0失败；
     */
    public int updateById(Merchandise merchandise) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            Integer id = merchandise.getId();
            String name = merchandise.getName();
            String photoPath = merchandise.getPhotoPath();
            String feature = merchandise.getFeature();
            String description = merchandise.getDescription();
            String category = merchandise.getCategory();
            Double price = merchandise.getPrice();
            Integer amount = merchandise.getAmount();
            Integer merchandiseStatus = merchandise.getMerchandiseStatus();
            Timestamp issuedDate = merchandise.getIssuedDate();
            String documentPath = merchandise.getDocumentPath();
            if (id == null) {//代表默认值
                throw new RuntimeException("系统错误，请联系管理员（id未传入）");
            }
            String sql = "update merchandise set name =?,photoPath=?,feature=?,description=?,category=?,price=?,amount=?,merchandiseStatus=?,issuedDate=?,documentPath = ? where id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, photoPath);
            ps.setString(3, feature);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setDouble(6, price);
            ps.setInt(7, amount);
            ps.setInt(8, merchandiseStatus);
            ps.setTimestamp(9, issuedDate);
            ps.setString(10,documentPath);
            ps.setInt(11, id);//id,where后面的条件,注意这里如果没有id话就不能进行更改；
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


    /**
     * 通过id 对商品的信息进行检查，通过商品的id来进行商品详细信息的查询
     *
     * @param
     * @return
     */
    public Merchandise selectById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = new Merchandise();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String documentPath = rs.getString("documentPath");
                String photoPath = rs.getString("photoPath");
                String feature = rs.getString("feature");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("amount");
                Integer merchantId = rs.getInt("merchantId");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                Timestamp issuedDate = rs.getTimestamp("issuedDate");
                merchandise = new Merchandise();
                merchandise.setId(id);
                merchandise.setName(name);
                merchandise.setDocumentPath(documentPath);
                merchandise.setPhotoPath(photoPath);
                merchandise.setPhotoPathArray(photoPath.split(";"));
                merchandise.setFeature(feature);
                merchandise.setFeatureArray(feature.split(";"));
                merchandise.setDescription(description);
                merchandise.setCategory(category);
                merchandise.setPrice(price);
                merchandise.setAmount(amount);
                merchandise.setMerchantId(merchantId);
                merchandise.setMerchandiseStatus(merchandiseStatus);
                merchandise.setIssuedDate(issuedDate);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return merchandise;
    }

    /**
     * 模糊查询，通过关键词进行商品名的搜索
     *
     * @param "keyword 用户键入的名"
     * @return 返回List集合，里面封装了所有可查询到的数据
     */
    public List<Merchandise> selectByKeyword(String keyword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = null;
        List<Merchandise> merchandiseList = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise where name like ? && merchandiseStatus = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String photoPath = rs.getString("photoPath");
                String feature = rs.getString("feature");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("amount");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                Timestamp issuedDate = rs.getTimestamp("issuedDate");
                merchandise = new Merchandise();
                merchandise.setId(id);
                merchandise.setName(name);
                merchandise.setPhotoPath(photoPath);
                merchandise.setPhotoPathArray(photoPath.split(";"));
                merchandise.setFeature(feature);
                merchandise.setFeatureArray(feature.split(";"));
                merchandise.setDescription(description);
                merchandise.setCategory(category);
                merchandise.setPrice(price);
                merchandise.setAmount(amount);
                merchandise.setMerchandiseStatus(merchandiseStatus);
                merchandise.setIssuedDate(issuedDate);
                merchandiseList.add(merchandise);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return merchandiseList;
    }


    /**
     * 查找所有商品的信息（管理员和市场的展示界面）
     *
     * @return 返回一个封装了所有用户的List集合
     */
    public List<Merchandise> selectAll() {
        List<Merchandise> allMerchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = new Merchandise();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise where merchandiseStatus = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String documentPath = rs.getString("documentPath");
                String photoPath = rs.getString("photoPath");
                String feature = rs.getString("feature");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("amount");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                Timestamp issuedDate = rs.getTimestamp("issuedDate");
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
                merchandise.setMerchandiseStatus(merchandiseStatus);
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

    public List<Merchandise> selectAllByMerchantId(int merchantId) {
        List<Merchandise> allMerchandise = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Merchandise merchandise = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from merchandise where merchantId = ? && merchandiseStatus != 3";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,merchantId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String documentPath = rs.getString("documentPath");
                String photoPath = rs.getString("photoPath");
                String feature = rs.getString("feature");
                String description = rs.getString("description");
                String category = rs.getString("category");
                Double price = rs.getDouble("price");
                Integer amount = rs.getInt("amount");
                Integer merchandiseStatus = rs.getInt("merchandiseStatus");
                Timestamp issuedDate = rs.getTimestamp("issuedDate");
                merchandise = new Merchandise();
                merchandise.setId(id);
                merchandise.setName(name);
                merchandise.setMerchantId(merchantId);
                merchandise.setDocumentPath(documentPath);
                merchandise.setPhotoPathArray(photoPath.split(";"));
                merchandise.setPhotoPath(photoPath);
                merchandise.setFeature(feature);
                merchandise.setFeatureArray(feature.split(";"));
                merchandise.setDescription(description);
                merchandise.setCategory(category);
                merchandise.setPrice(price);
                merchandise.setAmount(amount);
                merchandise.setMerchandiseStatus(merchandiseStatus);
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


    public int updateMerchantById(Integer id, Integer merchantId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update merchandise set merchantId = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, merchantId);
            ps.setInt(2, id);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }


}

package cn.wangye.dao.impl;

import cn.wangye.dao.UserDao;
import cn.wangye.pojo.User;
import cn.wangye.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    /*在这里完成对user表增删改查的操作*/
    //增 用户注册+完善信息（由于用户的登陆和信息用的一张表，所以这里的完善信息，实际上是对默认信息的更改）
    //删 账户注销
    //查 用户查看个人的信息
    //改 更改密码和相关信息
    //查所有 管理员操作

    /**
     * 不用对象封装了，这里直接传参 使用登陆时使用
     *
     * @param "username 账户"
     * @param "password 密码"
     * @return 1成功 0失败
     */
    public int insert(String userName, String passWord,String telephone) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into user (username,password,telephone)values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, passWord);
            ps.setString(3, telephone);
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 通过Id进行用户账户的注销
     *
     * @param id 注意用户id的传入哦；
     * @return count 1删除成功，0失败
     */
    public int deleteById(int id) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from javaweb.user where id=?";
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
    public int updateById(User user) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String profilePath = user.getProfilePath();
            String username = user.getUsername();
            String password = user.getPassword();
            String telephone = user.getTelephone();
            String address = user.getAddress();
            Integer userStatus = user.getUserStatus();
            Integer id = user.getId();
            if (id == 0) {
                throw new RuntimeException("系统错误，请联系管理员（id未传入）");
            }
            String sql = "update user set username = ?,password = ?,profilePath=?, telephone=?, address=? ,userStatus=? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, profilePath);
            ps.setString(4, telephone);
            ps.setString(5, address);
            ps.setInt(6, userStatus);
            ps.setInt(7, id);//id,where后面的条件,注意这里如果没有id话就不能进行更改；
            count = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    public User selectByUsername_Password(String username, String password, Integer identity) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from javaweb.user where username=? && password =? && identity = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, identity);
            rs = ps.executeQuery();
            if (rs.next()) {
                String profilePath = rs.getString("profilePath");
                Integer userStatus = rs.getInt("userStatus");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                Integer id = rs.getInt("id");
                user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setProfilePath(profilePath);
                user.setUserStatus(userStatus);
                user.setTelephone(telephone);
                user.setAddress(address);
                user.setIdentity(identity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return user;
    }


    /**
     * 通过id 对用户的信息进行检查
     *
     * @param id
     * @return
     */
    public User selectById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String profilePath = rs.getString("profilePath");
                Integer userStatus = rs.getInt("userStatus");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setProfilePath(profilePath);
                user.setUserStatus(userStatus);
                user.setTelephone(telephone);
                user.setAddress(address);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return user;
    }

    public User selectByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where username=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("id");
                String password = rs.getString("password");
                String profilePath = rs.getString("profilePath");
                Integer userStatus = rs.getInt("userStatus");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setProfilePath(profilePath);
                user.setUserStatus(userStatus);
                user.setTelephone(telephone);
                user.setAddress(address);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return user;
    }


    /**
     * 查找所有用户的信息
     *
     * @return 返回一个封装了所有用户的List集合
     */
    public List<User> selectAll() {
        List<User> allUsers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = new User();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where identity = 0";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String profilePath = rs.getString("profilePath");
                Integer userStatus = rs.getInt("userStatus");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setProfilePath(profilePath);
                user.setUserStatus(userStatus);
                user.setTelephone(telephone);
                user.setAddress(address);
                allUsers.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return allUsers;
    }


}
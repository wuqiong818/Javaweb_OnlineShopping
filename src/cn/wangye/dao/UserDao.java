package cn.wangye.dao;

import cn.wangye.pojo.User;

import java.util.List;

public interface UserDao {
    public int insert(String userName, String passWord,String telephone);
    public int deleteById(int id);
    public int updateById(User user);
    public User selectByUsername_Password(String username, String password ,Integer identity);
    public User selectById(int id);
    public User selectByUsername(String username);
    public List<User> selectAll();
}

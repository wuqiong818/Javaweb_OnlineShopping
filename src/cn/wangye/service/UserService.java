package cn.wangye.service;

import cn.wangye.pojo.User;

import javax.servlet.http.HttpServlet;
import java.util.List;

public interface UserService {

    public User login(String username, String password,Integer identity);
//    public int signUp(String username,String password);
    public int signUp(String username,String password,String telephone);

    public User checkSignUp(String username);
    public int updateById(User user);
    public User selectById(int id);

    public User selectByUsername(String username);
    public List<User> getAllUserInfo();
}

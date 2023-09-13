package cn.wangye.service.impl;

import cn.wangye.dao.ShoppingCart_UserIdDao;
import cn.wangye.dao.impl.ShoppingCart_UserIdDaoImpl;
import cn.wangye.dao.impl.UserDaoImpl;
import cn.wangye.dao.UserDao;
import cn.wangye.pojo.User;
import cn.wangye.service.UserService;

import java.util.List;

//service并不处理请求，请求要在service中处理好，这里就是辅助方法的编写
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    /**
     * @param "username         用户名”
     * @param “password         密码”
     * @param "verificationCode 验证码“
     * @return true登陆成功，false登陆失败
     */
    public User login(String username, String password, Integer identity) {
        return userDao.selectByUsername_Password(username, password, identity);
    }

    /**
     * 用户注册服务
     *
     * @param "username 用户名”
     * @param “password 密码”
     */
    public int signUp(String username, String password,String telephone) {
        return userDao.insert(username, password,telephone);
    }

    public User checkSignUp(String username) {
        return userDao.selectByUsername(username);
    }

    public int updateById(User user) {
        return userDao.updateById(user);
    }

    public User selectById(int id) {
        return userDao.selectById(id);
    }

    public User selectByUsername(String username) {
        return userDao.selectByUsername(username);
    }


    public List<User> getAllUserInfo(){
        return userDao.selectAll();
    }
}

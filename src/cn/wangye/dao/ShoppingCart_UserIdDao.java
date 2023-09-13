package cn.wangye.dao;

public interface ShoppingCart_UserIdDao {
    public int insert(Integer userId);

    public int deleteById(Integer userId);

    public int selectByUserId(int userId);
}

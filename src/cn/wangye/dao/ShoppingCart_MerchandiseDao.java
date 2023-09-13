package cn.wangye.dao;

import cn.wangye.pojo.Merchandise;
import cn.wangye.pojo.ShoppingCart_Merchandise;

import java.util.List;

public interface ShoppingCart_MerchandiseDao {
    public int insert_update(Integer merchandiseId, Integer shoppingCartId, Integer amount);

    public int deleteById(Integer id);

    public List<Merchandise> selectAll(Integer shoppingCartId);

    public int updateById(int id, int amount);

    public int updateStatus(int id, int status);
    public List<Merchandise> selectByIsChecked(int shoppingCartId);
    public int deleteByIsChecked();
}

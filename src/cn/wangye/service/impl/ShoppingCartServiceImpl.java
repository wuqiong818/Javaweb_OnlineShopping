package cn.wangye.service.impl;

import cn.wangye.dao.MerchandiseDao;
import cn.wangye.dao.ShoppingCart_MerchandiseDao;
import cn.wangye.dao.ShoppingCart_UserIdDao;
import cn.wangye.dao.impl.MerchandiseDaoImpl;
import cn.wangye.dao.impl.ShoppingCart_MerchandiseDaoImpl;
import cn.wangye.dao.impl.ShoppingCart_UserIdDaoImpl;
import cn.wangye.pojo.Merchandise;
import cn.wangye.service.ShoppingCartService;

import java.util.List;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    ShoppingCart_MerchandiseDao shoppingCart_merchandiseDao = new ShoppingCart_MerchandiseDaoImpl();
    ShoppingCart_UserIdDao shoppingCart_userIdDao = new ShoppingCart_UserIdDaoImpl();

    /**
     * 向购物车中添加商品，先通过查询，看该用户的购物车是否已经存在，若没有存在，则创建购物车。
     *
     * @param merchandiseId 添加到购物车中的商品id
     * @param userId        用户id
     * @param amount        添加到购物车中该商品的数量
     */
    public boolean addToShoppingCart(int merchandiseId, int userId, int amount) {
        if (shoppingCart_userIdDao.selectByUserId(userId) == 0) {
            shoppingCart_userIdDao.insert(userId);
        }
        int shoppingCartId = shoppingCart_userIdDao.selectByUserId(userId);
        //在购物车中进行查询，如果没有找到该商品，则添加，找到了，就直接进行数量的更改
        shoppingCart_merchandiseDao.insert_update(merchandiseId, shoppingCartId, amount);
        return true;
    }

    public void changeAmount(int id, int amount) {
        shoppingCart_merchandiseDao.updateById(id, amount);
    }

    public List<Merchandise> displayShoppingCartByUserId(int userId) {
        int shoppingCartId = shoppingCart_userIdDao.selectByUserId(userId);
        return shoppingCart_merchandiseDao.selectAll(shoppingCartId);
    }

    public int deleteShoppingCartMerchandiseById(int id) {
        return shoppingCart_merchandiseDao.deleteById(id);
    }

    public int updateStatus(int id, int status) {
        return shoppingCart_merchandiseDao.updateStatus(id, status);
    }

}

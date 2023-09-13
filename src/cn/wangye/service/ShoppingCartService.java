package cn.wangye.service;

import cn.wangye.pojo.Merchandise;

import java.util.List;

public interface ShoppingCartService {

    public boolean addToShoppingCart(int merchandiseId,int userId,int amount);

    public void changeAmount(int id, int amount);
    public List<Merchandise> displayShoppingCartByUserId(int userId);
    public int deleteShoppingCartMerchandiseById(int id);
    public int updateStatus(int id, int status);
}

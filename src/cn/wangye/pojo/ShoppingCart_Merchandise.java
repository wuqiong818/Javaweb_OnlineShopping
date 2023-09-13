package cn.wangye.pojo;

import java.net.Inet4Address;

public class ShoppingCart_Merchandise {
    private Integer id;
    private Integer merchandiseId;
    private Integer shoppingCartId;
    private Integer amount;
    private Integer isChecked;

    public Integer getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
    }

    public ShoppingCart_Merchandise() {
    }

    public Integer getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(Integer merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShoppingCart_Merchandise{" +
                "id=" + id +
                ", merchandiseId=" + merchandiseId +
                ", shoppingCartId=" + shoppingCartId +
                ", amount=" + amount +
                ", isChecked=" + isChecked +
                '}';
    }
}

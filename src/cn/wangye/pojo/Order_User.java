package cn.wangye.pojo;

public class Order_User {
    private Integer id;
    private Integer userId;
    private Integer orderId;

    public Order_User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Order_User{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }
}

package cn.wangye.pojo;

public class ShoppingCart_UserId {
    private Integer id;
    private Integer userId;

    public ShoppingCart_UserId() {
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

    @Override
    public String toString() {
        return "ShoppingCart_UsernameDaoImpl{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}

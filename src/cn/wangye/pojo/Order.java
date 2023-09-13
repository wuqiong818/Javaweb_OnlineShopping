package cn.wangye.pojo;

import java.sql.Timestamp;

public class Order {
    private Integer id;//订单的id
    private String exchangeMeans;
    private Integer purchaserId;
    private String receiverName;
    private String address;
    private String telephone;
    private String notes;
    private String orderStatus;
    private Timestamp purchaseDate;
    private Integer merchantId;

    private Double orderTotal;

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExchangeMeans() {
        return exchangeMeans;
    }

    public void setExchangeMeans(String exchangeMeans) {
        this.exchangeMeans = exchangeMeans;
    }

    public Integer getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Integer purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", exchangeMeans='" + exchangeMeans + '\'' +
                ", purchaserId=" + purchaserId +
                ", receiverName='" + receiverName + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", notes='" + notes + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", merchantId=" + merchantId +
                ", orderTotal=" + orderTotal +
                '}';
    }
}

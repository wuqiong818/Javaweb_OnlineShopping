package cn.wangye.pojo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class Order_OrderMerchandise {
    private Integer id;//id是订单的ID
    private String exchangeMeans;
    private Integer purchaserId;
    private String receiverName;
    private String address;
    private String telephone;
    private String notes;
    private String orderStatus;
    private Timestamp purchaseDate;
    private Double orderTotal;

    private Integer merchandiseId;
    private Integer amount;

    private List<Integer> merchandiseIdArray;
    private List<Merchandise> merchandises;
    private List<Integer> merchandiseAmountArray;



    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }

    public Order_OrderMerchandise() {
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

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(Integer merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Integer> getMerchandiseIdArray() {
        return merchandiseIdArray;
    }

    public void setMerchandiseIdArray(List<Integer> merchandiseIdArray) {
        this.merchandiseIdArray = merchandiseIdArray;
    }

    public List<Integer> getMerchandiseAmountArray() {
        return merchandiseAmountArray;
    }

    public void setMerchandiseAmountArray(List<Integer> merchandiseAmountArray) {
        this.merchandiseAmountArray = merchandiseAmountArray;
    }

    @Override
    public String toString() {
        return "Order_OrderMerchandise{" +
                "id=" + id +
                ", exchangeMeans='" + exchangeMeans + '\'' +
                ", purchaserId=" + purchaserId +
                ", receiverName='" + receiverName + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", notes='" + notes + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", orderTotal=" + orderTotal +
                ", merchandiseId=" + merchandiseId +
                ", amount=" + amount +
                ", merchandiseIdArray=" + merchandiseIdArray +
                ", merchandiseAmountArray=" + merchandiseAmountArray +
                ", merchandises=" + merchandises +
                '}';
    }
}

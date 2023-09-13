package cn.wangye.pojo;

public class Order_Merchandise {
    private Integer id;
    private Integer orderId;
    private Integer merchandiseId;
    private Integer amount;
    private Double total;
    private Merchandise merchandise;
    private Integer merchantId;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
    }

    public Order_Merchandise() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Order_Merchandise{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", merchandiseId=" + merchandiseId +
                ", amount=" + amount +
                ", total=" + total +
                ", merchandise=" + merchandise +
                ", merchantId=" + merchantId +
                '}';
    }
}

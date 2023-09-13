package cn.wangye.pojo;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class Merchandise {
    private Integer id;
    private String name;
    private String photoPath;
    private String documentPath;
    private String feature;
    private String description;
    private String category;
    private Double price;
    private Integer amount;
    private Integer merchandiseStatus;
    private String isChecked;
    private Timestamp issuedDate;

    private Integer merchandiseId;
    private Integer merchantId;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(Integer merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    private String [] featureArray;

    private String [] photoArray;

    private int merchandiseAmount;//这个是商品数量，记住，是为了实现购物车添加功能验证而写的，在购物车这个功能里面，amount是购物车里面的数量;

    public int getMerchandiseAmount() {
        return merchandiseAmount;
    }

    public void setMerchandiseAmount(int merchandiseAmount) {
        this.merchandiseAmount = merchandiseAmount;
    }

    public String[] getPhotoArray() {
        return photoArray;
    }

    public void setPhotoArray(String[] photoArray) {
        this.photoArray = photoArray;
    }

    private String [] photoPathArray;

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
    public String[] getFeatureArray() {
        return featureArray;
    }

    public void setFeatureArray(String[] featureArray) {
        this.featureArray = featureArray;
    }

    public String[] getPhotoPathArray() {
        return photoArray;
    }

    public void setPhotoPathArray(String[] photoArray) {
        this.photoArray = photoArray;
    }

    public Merchandise() {
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Merchandise(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getMerchandiseStatus() {
        return merchandiseStatus;
    }

    public void setMerchandiseStatus(Integer merchandiseStatus) {
        this.merchandiseStatus = merchandiseStatus;
    }

    public Timestamp getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Timestamp issuedDate) {
        this.issuedDate = issuedDate;
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", documentPath='" + documentPath + '\'' +
                ", feature='" + feature + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", merchandiseStatus=" + merchandiseStatus +
                ", isChecked='" + isChecked + '\'' +
                ", issuedDate=" + issuedDate +
                ", merchandiseId=" + merchandiseId +
                ", merchantId=" + merchantId +
                ", featureArray=" + Arrays.toString(featureArray) +
                ", photoArray=" + Arrays.toString(photoArray) +
                ", merchandiseAmount=" + merchandiseAmount +
                ", photoPathArray=" + Arrays.toString(photoPathArray) +
                '}';
    }
}

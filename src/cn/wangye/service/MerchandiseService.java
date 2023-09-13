package cn.wangye.service;

import cn.wangye.pojo.Merchandise;

import java.sql.Timestamp;
import java.util.List;

public interface MerchandiseService {
    public int[] addMerchandise(String name, String documentPath, String photoPath, String feature, String description, String category,
                                Double price, Integer amount, Integer status, Timestamp issuedDate,int userId);

    public int deleteById(int id);

    public int updateById(Merchandise merchandise);

    public Merchandise selectById(int id);

    public List<Merchandise> searchAllMerchandise(String keyword);

    public List<Merchandise> selectAllMerchandise();

    public List<Merchandise> selectCookieById_Amount(List<Merchandise> parameterMerchandiseList);

    public List<Merchandise> selectMerchandiseByMerchantId(int merchantId);
}

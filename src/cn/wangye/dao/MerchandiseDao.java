package cn.wangye.dao;

import cn.wangye.pojo.Merchandise;

import java.sql.Timestamp;
import java.util.List;

public interface MerchandiseDao {
    public int[] insert(String name, String documentPath, String photoPath, String feature, String description, String category,
                        Double price, Integer amount, Integer merchandiseStatus, Timestamp issuedDate);
    public int deleteById(int id);

    public int updateById(Merchandise merchandise);

    public Merchandise selectById(int id);

    public List<Merchandise> selectAll();

    public List<Merchandise> selectByKeyword(String keyword);

    public int updateMerchantById(Integer id,Integer merchantId);

    public List<Merchandise> selectAllByMerchantId(int merchantId);
}

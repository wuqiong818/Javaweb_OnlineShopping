package cn.wangye.service.impl;

import cn.wangye.dao.MerchandiseDao;
import cn.wangye.dao.impl.MerchandiseDaoImpl;
import cn.wangye.pojo.Merchandise;
import cn.wangye.service.MerchandiseService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MerchandiseServiceImpl implements MerchandiseService {
    MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();

    //用户不需要知道商品的id,但是我们需要知道商品的id，我们需要通过id,来进行数据库中id的插入;
    public int[] addMerchandise(String name, String documentPath, String photoPath, String feature, String description, String category,
                                Double price, Integer amount, Integer status, Timestamp issuedDate, int userId) {
        int[] array = merchandiseDao.insert(name, documentPath, photoPath, feature, description, category, price, amount, status, issuedDate);
        int merchandiseId = array[1];//很奇怪，当时为什么没有直接将用户的id作为merchantId进行插入
        merchandiseDao.updateMerchantById(merchandiseId,userId);
        return array;
    }

    public int deleteById(int id) {
        return merchandiseDao.deleteById(id);
    }

    public int updateById(Merchandise merchandise) {
        return merchandiseDao.updateById(merchandise);
    }

    public Merchandise selectById(int id) {
        return merchandiseDao.selectById(id);
    }

    public List<Merchandise> searchAllMerchandise(String keyword) {
        return merchandiseDao.selectByKeyword(keyword);
    }

    public List<Merchandise> selectAllMerchandise() {
        return merchandiseDao.selectAll();
    }

    /**
     * 这个方法是通过一个只有商品id和数量的parameterMerchandiseList集合获取参数
     * 通过传入的id,进行商品相关信息的查询，并将结果返回，之后使用参数中的数量进行覆盖，得到一个新的List
     *
     * @param parameterMerchandiseList 一个只有商品id和amount的集合
     * @return 返回一个根据所传入的id, 查询到的商品信息和传入数量的一个集合
     */
    public List<Merchandise> selectCookieById_Amount(List<Merchandise> parameterMerchandiseList) {
        List<Merchandise> merchandiseList = new ArrayList<>();
        for (Merchandise merchandise : parameterMerchandiseList) {
            int id = merchandise.getId();
            int amount = merchandise.getAmount();
            Merchandise tempMerchandise = selectById(id);//这个用来临时存储，方法返回的Merchandise对象
            int merchandiseAmount = tempMerchandise.getAmount();
            tempMerchandise.setAmount(amount);
            tempMerchandise.setMerchandiseAmount(merchandiseAmount);
            merchandiseList.add(tempMerchandise);
        }
        return merchandiseList;
    }

    public List<Merchandise> selectMerchandiseByMerchantId(int merchantId){
        return merchandiseDao.selectAllByMerchantId(merchantId);
    }

}

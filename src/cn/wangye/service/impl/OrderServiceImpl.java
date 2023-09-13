package cn.wangye.service.impl;

import cn.wangye.dao.*;
import cn.wangye.dao.impl.*;
import cn.wangye.pojo.*;
import cn.wangye.service.MerchandiseService;
import cn.wangye.service.OrderService;
import cn.wangye.service.ShoppingCartService;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    ShoppingCart_MerchandiseDao shoppingCart_merchandiseDao = new ShoppingCart_MerchandiseDaoImpl();

    ShoppingCart_UserIdDao shoppingCart_userIdDao = new ShoppingCart_UserIdDaoImpl();

    Order_MerchandiseDao orderMerchandiseDao = new Order_MerchandiseDaoImpl();

    OrderDao orderDao = new OrderDaoImpl();

    ShoppingCart_MerchandiseDao shoppingCartMerchandiseDao = new ShoppingCart_MerchandiseDaoImpl();
    //通过checked，来查询商品信息和购买的数量;

    public List<Merchandise> selectByIsChecked(int userId) {
        int shoppingCartId = shoppingCart_userIdDao.selectByUserId(userId);
        return shoppingCart_merchandiseDao.selectByIsChecked(shoppingCartId);
    }

    public int insertOrder(Integer purchaseId, String receiveName, String address, String telephone, String note, Double orderTotal) {
        OrderDao orderDao = new OrderDaoImpl();
        return orderDao.insertOrder(purchaseId, receiveName, address, telephone, note, orderTotal);
    }


    /**
     * 这个方法实现了两个数据库表的插入，一个是订单表数据的操作，大部分数据来自于前端用户所输入的数据
     * 另一个是订单和商品表信息，这些数据是根据用户所勾选的商品进行的更改;
     *
     * @param userId      用户的id
     * @param receiveName 前端收货人姓名；
     * @param telephone   前端电话号码；
     * @param address     前端地址；
     * @param notes       前端个人备注;
     */
    public void insertOrder_OrderMerchandise(int userId, String receiveName, String telephone,
                                             String address, String notes, String purchaseMerchandiseId, String purchaseAmount) {
        //先通过查询进行获取数据库中isCheck=1的数据
        int shoppingCartId = shoppingCart_userIdDao.selectByUserId(userId);
        MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();
        List<Merchandise> merchandiseList = new ArrayList<>();
        //这一个加工的数据需要发生变化,分成两种情况，不再是直接在后端获取勾选的了;
        if ("null".equals(purchaseMerchandiseId)) {
            //用户通过购物车进行购买操作
            merchandiseList = shoppingCart_merchandiseDao.selectByIsChecked(shoppingCartId);
        } else {
            //用户购买单个商品了
            /*
             * 用户单个购买的话，我需要单独造出来一个 List<Merchandise> merchandiseList 此集合是在购物车中获取的，即在购物车中被勾选的商品西悉尼
             * merchandiseList要包含一下属性;
             *拿着商品的id查一下，商品的数量一更新即可;
             *setAmount setPrice setMerchandiseId;
             * */
            Merchandise merchandise = merchandiseDao.selectById(Integer.parseInt(purchaseMerchandiseId));
            merchandise.setAmount(Integer.valueOf(purchaseAmount));
            merchandise.setMerchandiseId(Integer.valueOf(purchaseMerchandiseId));
            merchandiseList.add(merchandise);
        }

        Double orderTotal = 0D;
        for (int i = 0; i < merchandiseList.size(); i++) {
            orderTotal += merchandiseList.get(i).getAmount() * merchandiseList.get(i).getPrice();
        }
        //向order表中插入数据;
        int[] array = orderDao.insertOrderArray(userId, receiveName, telephone, address, notes, orderTotal);//返回count是否成功的状态和orderId订单的ID;
        int count = array[0];
        int orderId = array[1];
        if (count == 1) {
            //商品与订单表的数据的插入;
            for (int i = 0; i < merchandiseList.size(); i++) {
                int merchandiseId = merchandiseList.get(i).getMerchandiseId();
                int amount = merchandiseList.get(i).getAmount();
                Double total = merchandiseList.get(i).getPrice() * amount;

                Merchandise merchandise = merchandiseDao.selectById(merchandiseId);
                int merchantId = merchandise.getMerchantId();
                orderMerchandiseDao.insertOrder_Merchandise(orderId, merchandiseId, amount, total, merchantId);
            }

            //对商品的数量进行更改，商品的状态也要发生更改://0为售空，1为正常 2为下架;
            //默认值为1,0是商品售空，通过这里更改为0,2是用户自行下架和商品违规管理员下架
            //从订单表中获取商品的id和数量和商品表中的id和商品的数量；更改的商品的数量和状态;
            MerchandiseService merchandiseService = new MerchandiseServiceImpl();
            for (int i = 0; i < merchandiseList.size(); i++) {
                int merchandiseId = merchandiseList.get(i).getMerchandiseId();
                Merchandise merchandise = merchandiseService.selectById(merchandiseId);
                int merchandiseAmount = merchandise.getAmount();
                int orderMerchandiseAmount = merchandiseList.get(i).getAmount();//获取订单表的商品的数量;
                int subtractNum = merchandiseAmount - orderMerchandiseAmount;
                int merchandiseStatus = 1;
                if (subtractNum == 0) {
                    merchandiseStatus = 0;
                } else if (subtractNum < 0) {
                    try {
                        throw new Exception("商品已售空");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                //进行商品表中商品状态和数值的插入;
                merchandise.setAmount(subtractNum);
                merchandise.setMerchandiseStatus(merchandiseStatus);
                merchandiseService.updateById(merchandise);//进行商品表中数量和商品状态的更改
            }
            //这个也需要分一下状态;用户购买单个商品的话，不需要进行购物车的清空操作;
            if ("null".equals(purchaseMerchandiseId)) {
                shoppingCartMerchandiseDao.deleteByIsChecked();//对用户已经勾选的商品在购物车_商品表中进行删除;
            } else {
                //购买成功,没有啥事了
            }
        } else {
            //订单插入错误，请联系管理员
        }
    }

    //在这里封装一个方法：
    /*什么方法？
     *这里要形成一个特别理想的对象，什么样的对象呢？
     *一个包含orderID和orderTotal和包含订单中其商品的所有信息名称，价格，订单中商品的购买数量
     *
     *
     * */

    public List<Order_OrderMerchandise> displayOrderByUserId(int userId) {//这个最后返回的一定是order_orderMerchangdise类的list集合
        //通过用户名，获取一个包含该用户所有订单信息的list集合
        List<Order_OrderMerchandise> orderOrderMerchandises = new ArrayList<>();
        List<Order> orders = orderDao.selectByPurchaserId(userId);//根据用户的ID去查询用户相关的订单信息
        Order_MerchandiseDao order_merchandiseDao = new Order_MerchandiseDaoImpl();
        MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();
        for (int i = 0; i < orders.size(); i++) {
            Order_OrderMerchandise order_orderMerchandise = new Order_OrderMerchandise();//订单_商品的关系类
            //获取order订单中的数据先订单_商品的关系类中进行数据的封装:
            Integer orderId = orders.get(i).getId();
            Double orderTotal = orders.get(i).getOrderTotal();
            Integer purchaserId = orders.get(i).getPurchaserId();

            order_orderMerchandise.setId(orderId);//封装订单的id
            order_orderMerchandise.setOrderTotal(orderTotal);//封装订单的总价
            order_orderMerchandise.setPurchaserId(purchaserId);
            order_orderMerchandise.setReceiverName(orders.get(i).getReceiverName());
            order_orderMerchandise.setTelephone(orders.get(i).getTelephone());
            order_orderMerchandise.setAddress(orders.get(i).getAddress());
            order_orderMerchandise.setNotes(orders.get(i).getNotes());
            order_orderMerchandise.setPurchaseDate(orders.get(i).getPurchaseDate());
            //通过订单ID在Order_Merchandise中去查询相关的商品信息，数量
            List<Order_Merchandise> orderMerchandises = order_merchandiseDao.selectAllByOrderId(orderId);
            //商品的id和商品的数量放到for循环外面，不能每次都给重新创造一个,我要的是持续的多个;
            List<Integer> merchandiseId = new ArrayList<>();
            List<Integer> merchandiseAmount = new ArrayList<>();
            List<Merchandise> merchandises = new ArrayList<>();
            for (int j = 0; j < orderMerchandises.size(); j++) {
                merchandiseId.add(orderMerchandises.get(j).getMerchandiseId());
                merchandiseAmount.add(orderMerchandises.get(j).getAmount());
                Merchandise merchandise = merchandiseDao.selectById(orderMerchandises.get(j).getMerchandiseId());
                merchandises.add(merchandise);
            }
            //这里在for循环之外在进行放置;
            order_orderMerchandise.setMerchandiseIdArray(merchandiseId);//这里不妨在大胆一点，直接将id转化成商品类
            order_orderMerchandise.setMerchandiseAmountArray(merchandiseAmount);
            order_orderMerchandise.setMerchandises(merchandises);
            orderOrderMerchandises.add(order_orderMerchandise);
        }
        return orderOrderMerchandises;
    }


    //查询我的订单
    /*这里如何进行封装呢？
     *我需要的数据是:merchandiseId、receiverName、telephone、address,orderAmount,total,merchandiseName
     *总之就是order、order_merchandiseOrder、merchandise三个表中的数据都有;
     * 要让计算机为人服务，而不是人为计算机服务;
     * 我需要什么样的数据:一个订单里面有什么商品，这些商品的数量是多少，给谁发送
     * 最理想的数据是，一个订单编号，里面有若干个商品和顾客的各种信息数据
     * 1.创建一个新的封装对象,里面直接包含三个类,这一个新的对象没有这么简单，里面包含着各种各样的关系
     * 比如:一对一，一对多，多对多;
     * 2.
     */
    //这一个和用户的购买记录不同，那一个是顺着查的，这一个是上下交叉查询的;
    /*获取第一个,向后查orderId，如果orderId相同，则将商品的信息添加到上一个list中。对象的提交为最后一个语句。*/
    public List<Order_OrderMerchandise> displayOrderByMerchant(int merchantId) {
        List<Order_OrderMerchandise> list = new ArrayList<>();//提交的list集合
        List<Order_Merchandise> orderMerchandises = orderDao.selectByMerchantId(merchantId);
        MerchandiseDao merchandiseDao = new MerchandiseDaoImpl();
        Order_OrderMerchandise oom;
        for (int i = 0; i < orderMerchandises.size(); i++) {
            oom = new Order_OrderMerchandise();//向返回值list中插入的对象;
            int orderId = orderMerchandises.get(i).getOrderId();
            List<Integer> merchandiseIdList = new ArrayList<>();
            List<Integer> merchandiseAmountList = new ArrayList<>();
            List<Merchandise> merchandiseList = new ArrayList<>();
            for (int j = i + 1; j < orderMerchandises.size(); j++) {//在这里进行orderId相同值的处理，循环之中只获取商品信息；那么循环结束之后，在获取订单中的信息;
                int nextOrderId = orderMerchandises.get(j).getOrderId();
                boolean flag = true;
                if (orderId == nextOrderId) {
                    //进一步获取merchandiseId,将商品的id插入到merchandiseList,
                    //在这里光进行商品id和商品amount的list添加，其他不进行处理，orderId中只添加一次;
                    if (flag) {
                        merchandiseIdList.add(orderMerchandises.get(i).getMerchandiseId());
                        merchandiseAmountList.add(orderMerchandises.get(i).getAmount());
                        Merchandise merchandise = merchandiseDao.selectById(orderMerchandises.get(i).getMerchandiseId());
                        merchandiseList.add(merchandise);
                        flag = false;
                    }
                    merchandiseIdList.add(orderMerchandises.get(j).getMerchandiseId());
                    merchandiseAmountList.add(orderMerchandises.get(j).getAmount());
                    Merchandise merchandise = merchandiseDao.selectById(orderMerchandises.get(j).getMerchandiseId());
                    merchandiseList.add(merchandise);
                    i++;
                } else {
                    break;
                }
            }
            Order order = orderDao.selectById(orderId);
            oom.setId(orderId);
            oom.setReceiverName(order.getReceiverName());
            oom.setTelephone(order.getTelephone());
            oom.setAddress(order.getAddress());
            oom.setNotes(order.getNotes());
            oom.setPurchaseDate(order.getPurchaseDate());
            if (merchandiseIdList.isEmpty()) {
                merchandiseIdList.add(orderMerchandises.get(i).getMerchandiseId());
            }
            oom.setMerchandiseIdArray(merchandiseIdList);

            if (merchandiseAmountList.isEmpty()) {
                merchandiseAmountList.add(orderMerchandises.get(i).getAmount());
            }
            oom.setMerchandiseAmountArray(merchandiseAmountList);

            if (merchandiseList.isEmpty()) {
                Merchandise merchandise = merchandiseDao.selectById(orderMerchandises.get(i).getMerchandiseId());
                merchandiseList.add(merchandise);
            }
            oom.setMerchandises(merchandiseList);
            list.add(oom);
        }
        return list;
    }
}

/*
            int merchandiseId = orderMerchandises.get(i).getMerchandiseId();
            int amount = orderMerchandises.get(i).getAmount();
            Order order = orderDao.selectById(orderId);
            oom.setId(order.getId());
            oom.setReceiverName(order.getReceiverName());
            oom.setTelephone(order.getTelephone());
            oom.setAddress(order.getAddress());
            oom.setNotes(order.getNotes());
            oom.setPurchaseDate(order.getPurchaseDate());
                        List<Merchandise> merchandisesList = new ArrayList<>();
            List<Integer> merchandiseAmount = new ArrayList<>();*/
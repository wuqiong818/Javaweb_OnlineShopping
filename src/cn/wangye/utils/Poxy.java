package cn.wangye.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/*
public class ConnectionProxy {
    public static accountService createProxy(accountService accountServiceInv) {
        accountService accountServiceProxy = (accountService) Proxy.newProxyInstance(ConnectionProxy.class.getClassLoader(),
                new Class[]{accountService.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("accountTransfer")) {
                            Connection conn = null;
                            try {
                                conn = DBUtil.getConnection();
                                conn.setAutoCommit(false);
                                method.invoke(accountServiceInv, args);
                                conn.commit();
                            }catch (SQLException e) {
                                e.printStackTrace();
                                throw new appException("对不起，转账失败。请联系我们的管理员");
                            } finally {
                                conn.close();
                            }
                        }else {
                            method.invoke(accountServiceInv, args);
                        }
                        return null;
                    }
                });
        return accountServiceProxy;
    }
}*/

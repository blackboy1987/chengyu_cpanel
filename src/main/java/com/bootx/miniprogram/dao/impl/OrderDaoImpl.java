
package com.bootx.miniprogram.dao.impl;

import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.miniprogram.dao.OrderDao;
import com.bootx.miniprogram.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * Dao - 插件配置
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

}
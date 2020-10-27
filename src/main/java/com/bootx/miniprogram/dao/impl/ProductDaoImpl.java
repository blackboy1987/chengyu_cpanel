
package com.bootx.miniprogram.dao.impl;

import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.miniprogram.dao.ProductDao;
import com.bootx.miniprogram.entity.Product;
import org.springframework.stereotype.Repository;

/**
 * Dao - 插件配置
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

}
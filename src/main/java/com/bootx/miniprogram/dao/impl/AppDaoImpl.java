
package com.bootx.miniprogram.dao.impl;

import com.bootx.dao.impl.BaseDaoImpl;
import com.bootx.miniprogram.dao.AppDao;
import com.bootx.miniprogram.entity.App;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

/**
 * Dao - 插件配置
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class AppDaoImpl extends BaseDaoImpl<App, Long> implements AppDao {

    @Override
    public App findByCodeAndSecret(String code, String secret) {

        String jpql = "select app from App app where app.code = :code and app.secret = :secret";
        TypedQuery<App> query = entityManager.createQuery(jpql, App.class).setParameter("code", code).setParameter("secret",secret);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
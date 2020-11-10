
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.Idiom1Dao;
import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.miniprogram.service.Idiom1Service;
import com.bootx.miniprogram.util.EhCacheUtils;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class Idiom1ServiceImpl extends BaseServiceImpl<Idiom1, Long> implements Idiom1Service {

    @Autowired
    private Idiom1Dao idiom1Dao;

    @Override
    public Idiom1 findByLeve(Integer level) {
        return idiom1Dao.find("level",level);
    }
    @Override
    public Idiom1 findByFullText(String fullText) {
        return idiom1Dao.find("fullText",fullText);
    }

    @Override
    public Idiom1 save(Idiom1 entity) {
        entity = super.save(entity);
        EhCacheUtils.setCacheIdiomList(entity);
        return entity;
    }

    @Override
    public Idiom1 update(Idiom1 entity) {
        entity = super.update(entity);
        EhCacheUtils.setCacheIdiomList(entity);
        return entity;
    }

    @Override
    public Idiom1 update(Idiom1 entity, String... ignoreProperties) {

        entity = super.update(entity, ignoreProperties);
        EhCacheUtils.setCacheIdiomList(entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        Idiom1 idiom1 = super.find(id);
        if(idiom1!=null){
            EhCacheUtils.setCacheIdiomList(idiom1);
        }
        super.delete(id);
    }

    @Override
    public void delete(Long... ids) {
        for (Long id:ids) {
            Idiom1 idiom1 = super.find(id);
            if(idiom1!=null){
                EhCacheUtils.setCacheIdiomList(idiom1);
            }
        }
        super.delete(ids);
    }

    @Override
    public void delete(Idiom1 entity) {
        if(entity!=null){
            EhCacheUtils.setCacheIdiomList(entity);
        }

        super.delete(entity);
    }
}
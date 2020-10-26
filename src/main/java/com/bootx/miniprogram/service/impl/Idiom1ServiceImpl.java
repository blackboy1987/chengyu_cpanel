
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.Idiom1Dao;
import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.miniprogram.service.Idiom1Service;
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
    public Idiom1 findByText(String text) {
        return idiom1Dao.find("text",text);
    }
}
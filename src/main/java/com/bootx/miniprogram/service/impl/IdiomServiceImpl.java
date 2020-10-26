
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.IdiomDao;
import com.bootx.miniprogram.entity.Idiom;
import com.bootx.miniprogram.service.IdiomService;
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
public class IdiomServiceImpl extends BaseServiceImpl<Idiom, Long> implements IdiomService {

    @Autowired
    private IdiomDao idiomDao;

    @Override
    public Idiom findByLeve(Integer level) {
        return idiomDao.find("level",level);
    }
}

package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.WordDao;
import com.bootx.miniprogram.entity.Word;
import com.bootx.miniprogram.service.WordService;
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
public class WordServiceImpl extends BaseServiceImpl<Word, Long> implements WordService {

    @Autowired
    private WordDao wordDao;

    @Override
    public Word findByText(String text) {
        return wordDao.find("text",text);
    }
}
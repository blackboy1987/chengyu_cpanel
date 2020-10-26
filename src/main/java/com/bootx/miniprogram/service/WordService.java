
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.Word;
import com.bootx.service.BaseService;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface WordService extends BaseService<Word,Long> {

    Word findByText(String text);
}
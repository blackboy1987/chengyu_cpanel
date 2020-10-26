
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.Idiom1;
import com.bootx.service.BaseService;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface Idiom1Service extends BaseService<Idiom1,Long> {

    Idiom1 findByLeve(Integer level);
    Idiom1 findByText(String text);
}
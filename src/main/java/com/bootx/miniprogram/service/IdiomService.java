
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.Idiom;
import com.bootx.service.BaseService;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface IdiomService extends BaseService<Idiom,Long> {

    Idiom findByLeve(Integer level);
}
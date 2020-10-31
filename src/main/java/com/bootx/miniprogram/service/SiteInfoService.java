
package com.bootx.miniprogram.service;

import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.SiteInfo;
import com.bootx.service.BaseService;

/**
 * Service - 插件
 * 
 * @author blackboy
 * @version 1.0
 */
public interface SiteInfoService extends BaseService<SiteInfo,Long> {

    SiteInfo find(App app);

    void addCache();
}
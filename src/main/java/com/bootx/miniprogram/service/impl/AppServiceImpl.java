
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.AppDao;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.service.AppService;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class AppServiceImpl extends BaseServiceImpl<App, Long> implements AppService {

	@Autowired
	private AppDao appDao;

	@Override
	@Cacheable(value = "app",key = "#code",condition = "#code != null")
	public App findByCodeAndSecret(String code,String secret) {
		return appDao.find("code",code);
	}
}
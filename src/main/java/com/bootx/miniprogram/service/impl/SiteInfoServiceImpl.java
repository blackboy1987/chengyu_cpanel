
package com.bootx.miniprogram.service.impl;

import com.bootx.miniprogram.dao.SiteInfoDao;
import com.bootx.miniprogram.entity.App;
import com.bootx.miniprogram.entity.SiteInfo;
import com.bootx.miniprogram.service.SiteInfoService;
import com.bootx.miniprogram.util.EhCacheUtils;
import com.bootx.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service - 素材目录
 * 
 * @author blackboy
 * @version 1.0
 */
@Service
public class SiteInfoServiceImpl extends BaseServiceImpl<SiteInfo, Long> implements SiteInfoService {

	@Autowired
	private SiteInfoDao siteInfoDao;

	@Override
	public SiteInfo find(App app) {
		return siteInfoDao.find("app",app);
	}

	@Override
	public void addCache() {
		List<SiteInfo> siteInfos = super.findAll();
		for (SiteInfo siteInfo:siteInfos) {
			EhCacheUtils.setCacheSiteInfo(siteInfo);
		}
	}

}
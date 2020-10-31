
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

	@Override
	public SiteInfo save(SiteInfo entity) {
		SiteInfo siteInfo = super.save(entity);
		EhCacheUtils.setCacheSiteInfo(siteInfo);
		return siteInfo;
	}

	@Override
	public SiteInfo update(SiteInfo entity) {
		SiteInfo siteInfo = super.update(entity);
		EhCacheUtils.setCacheSiteInfo(siteInfo);
		return siteInfo;
	}

	@Override
	public SiteInfo update(SiteInfo entity, String... ignoreProperties) {
		SiteInfo siteInfo = super.update(entity, ignoreProperties);
		EhCacheUtils.setCacheSiteInfo(siteInfo);
		return siteInfo;
	}

	@Override
	public void delete(Long id) {
		EhCacheUtils.removeCacheVideo(id);
		super.delete(id);
	}

	@Override
	public void delete(Long... ids) {
		for (Long id:ids) {
			EhCacheUtils.removeCacheVideo(id);
		}
		super.delete(ids);
	}

	@Override
	public void delete(SiteInfo siteInfo) {
		if(siteInfo!=null){
			EhCacheUtils.removeCacheVideo(siteInfo.getId());
		}
		super.delete(siteInfo);
	}
}
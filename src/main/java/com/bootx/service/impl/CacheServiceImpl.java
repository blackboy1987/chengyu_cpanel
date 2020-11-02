/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 1C3OaPprBQkrIDVVYaWGPwQ/kVmKHYgr
 */
package com.bootx.service.impl;

import com.bootx.service.CacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	@Override
	public String getDiskStorePath() {
		return cacheManager.getConfiguration().getDiskStoreConfiguration().getPath();
	}

	@Override
	public int getCacheSize() {
		int cacheSize = 0;
		String[] cacheNames = cacheManager.getCacheNames();
		if (cacheNames != null) {
			for (String cacheName : cacheNames) {
				Ehcache cache = cacheManager.getEhcache(cacheName);
				if (cache != null) {
					cacheSize += cache.getSize();
				}
			}
		}
		return cacheSize;
	}
	@Override
	public void clear() {
		List<Map<String,Object>> list = new ArrayList<>();
		String [] cacheNames = cacheManager.getCacheNames();
		for (String cacheName:cacheNames) {
			cacheManager.getCache(cacheName).removeAll();
		}
	}

	@Override
	public List<Map<String,Object>> info() {
		List<Map<String,Object>> list = new ArrayList<>();
		String [] cacheNames = cacheManager.getCacheNames();
		for (String cacheName:cacheNames) {
			Map<String,Object> map = new HashMap<>();
			map.put("cacheName",cacheName);
			Cache cache = cacheManager.getCache(cacheName);
			List keys = cache.getKeys();
			List<Map<Object, Object>> result = new ArrayList<>();
			for (Object key:keys) {
				Map<Object, Object> map1 = new HashMap<>();
				map1.put("key",key);
				map1.put("value",cache.get(key).getObjectValue());
				result.add(map1);
			}
			map.put("list",result);
			list.add(map);
		}

		return list;
	}

}
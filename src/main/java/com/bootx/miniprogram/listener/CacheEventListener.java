/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: B7/DqioNLPyLcApCaIWWUiEH+4awkVkY
 */
package com.bootx.miniprogram.listener;

import com.bootx.miniprogram.service.SiteInfoService;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class CacheEventListener extends CacheEventListenerAdapter {

	@Autowired
	private SiteInfoService siteInfoService;

	/**
	 * 元素过期调用
	 * 
	 * @param ehcache
	 *            缓存
	 * @param element
	 *            元素
	 */
	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {

	}

}
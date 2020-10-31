/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: jWgfYYmh/6LEC64TGHi9WwZbgwpa9bVF
 */
package com.bootx.service;

import java.util.List;
import java.util.Map;

/**
 * Service - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CacheService {

	/**
	 * 获取缓存存储路径
	 * 
	 * @return 缓存存储路径
	 */
	String getDiskStorePath();

	/**
	 * 获取缓存数
	 * 
	 * @return 缓存数
	 */
	int getCacheSize();

	/**
	 * 清除缓存
	 */
	void clear();

	/**
	 * 缓存详情
	 * @return
	 */
	List<Map<String,Object>> info();

}
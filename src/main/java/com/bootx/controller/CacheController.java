/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: bgt5awDludfsjBEW+J8LwWa9ldZcevdc
 */
package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminCacheController")
@RequestMapping("/admin/cache")
public class CacheController extends BaseController {

	@Autowired
	private CacheService cacheService;

	/**
	 * 清除缓存
	 */
	@GetMapping("/clear")
	public Result clear() {
		cacheService.clear();
		return Result.success("");
	}

	@GetMapping("/info")
	public Result info() {
		return Result.success(cacheService.info());
	}

}
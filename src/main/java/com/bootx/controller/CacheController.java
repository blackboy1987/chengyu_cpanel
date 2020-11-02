
package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 缓存
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController("adminCacheController")
@RequestMapping("/idiom/cache")
public class CacheController extends BaseController {

	@Autowired
	private CacheService cacheService;

	/**
	 * 清除缓存
	 */
	@PostMapping("/clear")
	public Result clear() {
		cacheService.clear();
		return Result.success("");
	}

	@PostMapping("/info")
	public Result info() {
		return Result.success(cacheService.info());
	}

}
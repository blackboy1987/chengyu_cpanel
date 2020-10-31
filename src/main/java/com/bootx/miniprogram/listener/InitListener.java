
package com.bootx.miniprogram.listener;

import com.bootx.miniprogram.service.SiteInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Listener - 初始化
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class InitListener {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());

	@Autowired
	private SiteInfoService siteInfoService;

	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
			return;
		}
		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |S|H|O|P|+|+| |B|2|B|2|C| |";
		LOGGER.info(info.replace("|", StringUtils.EMPTY));
		siteInfoService.addCache();
	}

}
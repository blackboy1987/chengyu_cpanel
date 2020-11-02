
package com.bootx.dao.impl;

import com.bootx.dao.SnDao;
import com.bootx.miniprogram.entity.Sn;
import com.bootx.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Dao - 序列号
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class SnDaoImpl implements SnDao, InitializingBean {


	/**
	 * 订单编号生成器
	 */
	private HiloOptimizer orderHiloOptimizer;


	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		orderHiloOptimizer = new HiloOptimizer(Sn.Type.ORDER, "yyyyMMddHHmmss", 100);
	}

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	@Override
	public String generate(Sn.Type type) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");

		if (type == Sn.Type.ORDER) {
			return orderHiloOptimizer.generate();
		}
		return null;
	}

	/**
	 * 获取末值
	 * 
	 * @param type
	 *            类型
	 * @return 末值
	 */
	private long getLastValue(Sn.Type type) {
		String jpql = "select sn from Sn sn where sn.type = :type";
		Sn sn = entityManager.createQuery(jpql, Sn.class).setLockMode(LockModeType.PESSIMISTIC_WRITE).setParameter("type", type).getSingleResult();
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		return lastValue;
	}

	/**
	 * 高低位算法生成器
	 */
	private class HiloOptimizer {

		/**
		 * 类型
		 */
		private Sn.Type type;

		/**
		 * 前缀
		 */
		private String prefix;

		/**
		 * 最大低位值
		 */
		private int maxLo;

		/**
		 * 低位值
		 */
		private int lo;

		/**
		 * 高位值
		 */
		private long hi;

		/**
		 * 末值
		 */
		private long lastValue;

		/**
		 * 构造方法
		 * 
		 * @param type
		 *            类型
		 * @param prefix
		 *            前缀
		 * @param maxLo
		 *            最大低位值
		 */
		HiloOptimizer(Sn.Type type, String prefix, int maxLo) {
			this.type = type;
			this.prefix = prefix != null ? prefix.replace("{", "${") : StringUtils.EMPTY;
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		/**
		 * 生成序列号
		 * 
		 * @return 序列号
		 */
		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			return DateUtils.formatDateToString(new Date(),prefix) + (hi + lo++);
		}
	}

}
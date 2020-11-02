/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: 69VCuKa/9qbrgal1V+cN8dg0QUOEOYzc
 */
package com.bootx.miniprogram.entity;

import com.bootx.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Entity - 序列号
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Entity
public class Sn extends BaseEntity<Long> {

	private static final long serialVersionUID = -2330598144835706164L;

	/**
	 * 类型
	 */
	public enum Type {

		/**
		 * 订单
		 */
		ORDER,
	}

	/**
	 * 类型
	 */
	@Column(nullable = false, updatable = false, unique = true)
	private Type type;

	/**
	 * 末值
	 */
	@Column(nullable = false)
	private Long lastValue;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取末值
	 * 
	 * @return 末值
	 */
	public Long getLastValue() {
		return lastValue;
	}

	/**
	 * 设置末值
	 * 
	 * @param lastValue
	 *            末值
	 */
	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}

}